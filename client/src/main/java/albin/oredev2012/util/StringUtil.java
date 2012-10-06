package albin.oredev2012.util;

import java.util.Collection;

import org.springframework.util.StringUtils;

public class StringUtil {

	public static final String replace(String source, CharSequence target,
			CharSequence replacement) {
		if (source != null) {
			return source.replace(target, replacement);
		}
		return null;
	}
	
	public static final String collectionToCommaDelimitedString(Collection<?> coll) {
		return StringUtils.collectionToCommaDelimitedString(coll);
	}

}
