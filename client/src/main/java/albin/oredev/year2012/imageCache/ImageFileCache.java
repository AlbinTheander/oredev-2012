package albin.oredev.year2012.imageCache;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import albin.oredev.year2012.util.Logg;
import albin.oredev.year2012.util.StreamUtil;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;

import com.googlecode.androidannotations.annotations.EBean;
import com.googlecode.androidannotations.annotations.RootContext;

@EBean
public class ImageFileCache {

	private static final char[] HEX_DIGITS = "0123456789ABCDEF".toCharArray();

	@RootContext
	protected Context context;

	public Bitmap get(String url) {
		File imageFile = new File(context.getCacheDir(), getFileName(url));
		Bitmap bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
		return bitmap;
	}

	public void put(String url, Bitmap bitmap) {
		File imageFile = new File(context.getCacheDir(), getFileName(url));
		try {
			OutputStream out = new FileOutputStream(imageFile);
			CompressFormat format = guessFormatFromUrl(url);
			bitmap.compress(format, 100, out);
			StreamUtil.closeSilently(out);
		} catch (FileNotFoundException e) {
			Logg.e("Couldn't save image cache file for " + url);
		}
	}

	private static CompressFormat guessFormatFromUrl(String url) {
		int suffixPos = url.lastIndexOf(".");
		String suffix = "jpg";
		if (suffixPos > 0) {
			suffix = url.substring(suffixPos + 1).toLowerCase();
		}
		if ("png".equals(suffix))
			return CompressFormat.PNG;
		return CompressFormat.JPEG;
	}

	private String getFileName(String url) {
		byte[] urlBytes = url.getBytes();

		MessageDigest md;
		try {
			md = MessageDigest.getInstance("MD5");
			byte[] thedigest = md.digest(urlBytes);
			return encodeHex(thedigest) + "." + url.substring(url.lastIndexOf(".")+1);
		} catch (NoSuchAlgorithmException e) {
			Logg.e("Fatal error. MD5 is not here!!!");
		}
		return "dummy.txt";
	}

	/**
	 * Converts an array of bytes into an array of characters representing the
	 * hexidecimal values of each byte in order. The returned array will be
	 * double the length of the passed array, as it takes two characters to
	 * represent any given byte.
	 * 
	 * @param data
	 *            a byte[] to convert to Hex characters
	 * @return A String containing hexidecimal characters
	 */
	public static String encodeHex(byte[] data) {
		int l = data.length;
		StringBuilder sb = new StringBuilder(l << 1);

		// two characters form the hex value.
		for (int i = 0; i < l; i++) {
			sb.append(HEX_DIGITS[(0xF0 & data[i]) >>> 4]);
			sb.append(HEX_DIGITS[0x0F & data[i]]);
		}

		return sb.toString();
	}

}
