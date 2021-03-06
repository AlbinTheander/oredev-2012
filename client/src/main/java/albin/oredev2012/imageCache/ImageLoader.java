package albin.oredev2012.imageCache;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.Callable;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import albin.oredev2012.util.Logg;
import albin.oredev2012.util.StreamUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.googlecode.androidannotations.annotations.AfterInject;
import com.googlecode.androidannotations.annotations.EBean;

@EBean
public class ImageLoader {

	public interface LoadListener {
		void onLoadFinished(boolean success, String url, Bitmap bitmap);
	}

	private ThreadPoolExecutor loaderService;

	@AfterInject
	protected void init() {
		loaderService = new ThreadPoolExecutor(2, 2, 0L, TimeUnit.MILLISECONDS,
				new LinkedBlockingQueue<Runnable>());
		loaderService.setThreadFactory(new ThreadFactory() {

			@Override
			public Thread newThread(Runnable r) {
				Thread t = new Thread(r, "image-loader");
				t.setPriority(Thread.MIN_PRIORITY);
				return t;
			}
		});
	}

	public Bitmap loadImage(String url) {
		try {
			URL imageUrl = new URL(url);
			InputStream in = (InputStream) imageUrl.getContent();
			Bitmap bitmap = BitmapFactory.decodeStream(in);
			StreamUtil.closeSilently(in);
			return bitmap;
		} catch (MalformedURLException e) {
			Logg.e("Couldn't decode image from server", e);
		} catch (IOException e) {
			Logg.e("Problem reading image from server", e);
		}
		return null;
	}

	public void loadImageAsync(String url, LoadListener callback) {
		LoaderJob job = new LoaderJob(url, callback);
		loaderService.submit(job);
	}

	private static class LoaderJob implements Callable<Bitmap> {
		private final String url;
		private final LoadListener listener;

		private LoaderJob(String url, LoadListener listener) {
			this.url = url;
			this.listener = listener;
		}

		@Override
		public Bitmap call() {
			try {
				URL imageUrl = new URL(url);
				InputStream in = (InputStream) imageUrl.getContent();
				Bitmap bitmap = BitmapFactory.decodeStream(in);
				StreamUtil.closeSilently(in);
				if (listener != null) {
					listener.onLoadFinished(true, url, bitmap);
				}
				return bitmap;
			} catch (MalformedURLException e) {
				Logg.e("Invalid url to image", e);
			} catch (IOException e) {
				Logg.e("Problem reading file from server", e);
			}
			return null;
		}
	}

}
