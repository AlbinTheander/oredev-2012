package albin.oredev2012.util;

import java.io.IOException;

import org.joda.time.Interval;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class FormatUtil {

	private static final DateTimeFormatter HOUR_MINUTE_FORMAT = DateTimeFormat
			.forPattern("HH:mm");

	public static CharSequence format(Interval interval) {
		StringBuilder sb = new StringBuilder();
		try {
			HOUR_MINUTE_FORMAT.printTo(sb, interval.getStart());
			sb.append(" - ");
			HOUR_MINUTE_FORMAT.printTo(sb, interval.getEnd());
		} catch (IOException e) {
			// Will never happen for StringBuilder
		}
		return sb;
	}

}
