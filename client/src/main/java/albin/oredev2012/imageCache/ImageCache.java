package albin.oredev2012.imageCache;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.googlecode.androidannotations.annotations.Background;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EBean;
import com.googlecode.androidannotations.api.Scope;

@EBean(scope=Scope.Singleton)
public class ImageCache {

	@Bean
	protected ImageLoader imageLoader;

	@Bean
	protected ImageFileCache fileCache;

	public interface OnImageLoadedListener {
		void onImageLoaded(String url, Bitmap bitmap);
	}

	private LruCache<String, BitmapEntry> memCache;

	public ImageCache() {
		memCache = new LruCache<String, BitmapEntry>(4 * 1024 * 1024) {
			@Override
			protected int sizeOf(String key, BitmapEntry value) {
				if (value.bitmap == null)
					return 0;
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
				memCache.put(url, new BitmapEntry());
				loadInBackground(url, callback);
				return null;
			}
		}
		return entry == null ? null : entry.bitmap;
	}

	public void cache(String url, OnImageLoadedListener callback) {
		BitmapEntry entry;
		synchronized (memCache) {
			entry = memCache.get(url);

			if (entry == null) {
				memCache.put(url, new BitmapEntry());
			}
		}
		if (entry == null) {
			Bitmap bitmap = fileCache.get(url);
			if (bitmap == null) {
				bitmap = imageLoader.loadImage(url);
			}
			if (bitmap != null) {
				synchronized (memCache) {
					entry = new BitmapEntry();
					entry.bitmap = bitmap;
					memCache.put(url, entry);
				}
				callback.onImageLoaded(url, bitmap);
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
			imageLoader.loadImageAsync(url, new LoaderJob(callback));
		} else if (bitmap != null) {
			synchronized (memCache) {
				BitmapEntry entry = new BitmapEntry();
				entry.bitmap = bitmap;
				memCache.put(url, entry);
			}
			if (callback != null)
				callback.onImageLoaded(url, bitmap);
		}
	}

	private class BitmapEntry {
		Bitmap bitmap;
	}

	private class LoaderJob implements ImageLoader.LoadListener {

		private final OnImageLoadedListener listener;

		public LoaderJob(OnImageLoadedListener listener) {
			this.listener = listener;
		}

		@Override
		public void onLoadFinished(boolean success, String url, Bitmap bitmap) {
			if (!success)
				return;
			fileCache.put(url, bitmap);
			synchronized (memCache) {
				BitmapEntry entry = new BitmapEntry();
				entry.bitmap = bitmap;
				memCache.put(url, entry);
			}
			if (listener != null)
				listener.onImageLoaded(url, bitmap);
		}

	}

}
