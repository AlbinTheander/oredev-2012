package albin.oredev2012.util;

import java.io.Closeable;
import java.io.IOException;

public class StreamUtil {
	
	public static void closeSilently(Closeable stream) {
		if (stream != null) {
			try {
				stream.close();
			} catch (IOException e) {
				// Silently ignore
			}
		}
	}

}
