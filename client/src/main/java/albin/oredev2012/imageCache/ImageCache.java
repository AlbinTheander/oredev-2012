package albin.oredev2012.imageCache;

import java.util.HashSet;
import java.util.Set;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.googlecode.androidannotations.annotations.Background;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EBean;
import com.googlecode.androidannotations.api.Scope;

@EBean(scope = Scope.Singleton)
public class ImageCache {

	private static final int MEM_CACHE_SIZE = 4 * 1024 * 1024;

	@Bean
	protected ImageLoader imageLoader;

	@Bean
	protected ImageFileCache fileCache;

	public interface OnImageLoadedListener {
		void onImageLoaded(String url, Bitmap bitmap);
	}

	private final LruCache<String, BitmapEntry> memCache;

	public ImageCache() {
		memCache = new LruCache<String, BitmapEntry>(MEM_CACHE_SIZE) {
			@Override
			protected int sizeOf(String key, BitmapEntry value) {
				if (value.bitmap == null) {
					return 0;
				}
				return value.bitmap.getRowBytes() * value.bitmap.getHeight();
			}
		};
	}

	public Bitmap getImage(String url, boolean useNonMemoryCaches,
			OnImageLoadedListener callback) {
		BitmapEntry entry;
		synchronized (memCache) {
			entry = memCache.get(url);

			if (entry == null && useNonMemoryCaches) {
				entry = new BitmapEntry();
				entry.listeners.add(callback);
				memCache.put(url, entry);
				loadInBackground(url, callback);
				return null;
			}
		}
		return (entry == null) ? null : entry.bitmap;
	}

	public void cache(String url, OnImageLoadedListener callback) {
		BitmapEntry entry;
		synchronized (memCache) {
			entry = memCache.get(url);

			if (entry == null) {
				entry = new BitmapEntry();
				entry.listeners.add(callback);
				memCache.put(url, entry);
			} else {
				return;
			}
		}
		Bitmap bitmap = fileCache.get(url);
		if (bitmap == null) {
			bitmap = imageLoader.loadImage(url);
		}
		if (bitmap != null) {
			synchronized (memCache) {
				entry.bitmap = bitmap;
			}
			for (OnImageLoadedListener listener : entry.listeners) {
				listener.onImageLoaded(url, bitmap);
			}
		}
	}

	@Background
	protected void loadInBackground(String url, OnImageLoadedListener callback) {
		loadImage(url, callback);
	}

	private void loadImage(String url, OnImageLoadedListener callback) {
		Bitmap bitmap = fileCache.get(url);
		if (bitmap == null) {
			LoaderJob loaderJob = new LoaderJob();
			imageLoader.loadImageAsync(url, loaderJob);
		} else if (bitmap != null) {
			synchronized (memCache) {
				BitmapEntry entry = memCache.get(url);
				entry.bitmap = bitmap;
			}
			if (callback != null) {
				callback.onImageLoaded(url, bitmap);
			}
		}
	}

	private static class BitmapEntry {

		final Set<OnImageLoadedListener> listeners = new HashSet<ImageCache.OnImageLoadedListener>();

		Bitmap bitmap;
	}

	private class LoaderJob implements ImageLoader.LoadListener {

		@Override
		public void onLoadFinished(boolean success, String url, Bitmap bitmap) {
			if (!success) {
				return;
			}
			fileCache.put(url, bitmap);
			BitmapEntry entry;
			synchronized (memCache) {
				entry = memCache.get(url);
				if (entry == null) {
					entry = new BitmapEntry();
					memCache.put(url, entry);
				}
				entry.bitmap = bitmap;
			}
			for (OnImageLoadedListener listener : entry.listeners) {
				listener.onImageLoaded(url, bitmap);
			}
		}

	}

}
