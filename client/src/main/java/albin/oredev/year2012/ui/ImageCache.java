package albin.oredev.year2012.ui;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.util.Log;

import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EBean;

@EBean
public class ImageCache {

	@Bean
	protected ImageLoader imageLoader;

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
				Log.d("Oredev", "Submitting image loading job");
				memCache.put(url, new BitmapEntry());
				imageLoader.loadImage(url, new LoaderJob(callback));
				return null;
			}
		}
		return entry == null ? null : entry.bitmap;
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
			synchronized (memCache) {
				BitmapEntry entry = new BitmapEntry();
				entry.bitmap = bitmap;
				memCache.put(url, entry);
			}
			listener.onImageLoaded(url, bitmap);
		}

	}

}
