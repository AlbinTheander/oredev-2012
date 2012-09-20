package albin.oredev.year2012.ui;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.util.LruCache;

import com.googlecode.androidannotations.annotations.Background;
import com.googlecode.androidannotations.annotations.EBean;

@EBean
public class ImageCache {
	
	
	public interface OnImageLoadedListener {
		void onImageLoaded(String url, Bitmap bitmap);
	}
	
	private LruCache<String, Bitmap> memCache;
	
	public ImageCache() {
		memCache = new LruCache<String, Bitmap>(4*1024*1024) {
			@Override
	        protected int sizeOf(String key, Bitmap value) {
	               return value.getRowBytes() * value.getHeight();
	       }
	    };
	}
	
	public Bitmap getImage(String url, OnImageLoadedListener callback) {
		Bitmap bitmap;
		synchronized(memCache) {
		    bitmap = memCache.get(url);
		}
		 
		if(bitmap == null) {
		    getImageFromServer(url, callback);
		}
		return bitmap;
	}

	@Background
	protected void getImageFromServer(String url, OnImageLoadedListener callback) {
		try {
			URL imageUrl = new URL(url);
			InputStream in = (InputStream) imageUrl.getContent();
			Bitmap bitmap = BitmapFactory.decodeStream(in);
		    synchronized(memCache) {
		        memCache.put(url, bitmap);
		    }
		    callback.onImageLoaded(url, bitmap);

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
