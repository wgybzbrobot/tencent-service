package cc.pp.tencent.utils.time;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DailyUtils {

	/**
	 * 获取某天日期
	 */
	public static String getSomeDayDate(int N) {
		Date now = new Date();
		long time = now.getTime() / 1000 + N * 86400;
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String date = format.format(new Date(time * 1000l));
		return date.replaceAll("-", "");
	}

	/**
	 * 获取小时
	 */
	public static int getHour(long time) {
		DateFormat format = new SimpleDateFormat("HH");
		String hour = format.format(new Date(time * 1000l));
		if (hour.substring(0, 1).equals("0")) {
			hour = hour.substring(1);
		}
		return Integer.parseInt(hour);
	}

}
