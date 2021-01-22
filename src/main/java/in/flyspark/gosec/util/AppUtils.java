package in.flyspark.gosec.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AppUtils {

	private AppUtils() {
		super();
	}

	public static final String SUMMARY = "Summary";
	public static final String REPORT = "Report";

	public static final String ANALYSIS_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ";
	public static final String REPORT_DATE_FORMAT = "dd-MMM-yyyy hh:mm:ss a";
	public static final String FILE_FORMAT = "dd_MMM_yyyy_HH_mm_ss";

	public static final String getFileName(String projectName, String suffix) {
		return projectName + "_gosec_report_" + suffix;
	}

	public static String getCurrentDateTime(String format) {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, Locale.getDefault());
		return simpleDateFormat.format(calendar.getTime());
	}

	public static boolean isBlank(String input) {
		return input == null || input.trim().isEmpty() ? true : false;
	}

	public static String trim(String input) {
		return input == null || input.trim().isEmpty() ? "" : input.trim();
	}
}
