package cc.pp.tencent.utils.time;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtils {

	/**
	 * 获取今天的日期
	 */
	public static String getTodayDaily() {
		long time = System.currentTimeMillis() / 1000;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String result = format.format(new Date(time * 1000l));
		return result.replaceAll("-", "");
	}

	public static String getTodayDaily(int N) {
		long time = System.currentTimeMillis() / 1000 + N * 86400;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String result = format.format(new Date(time * 1000l));
		return result.replaceAll("-", "");
	}

	/**
	 * 获取当前时间戳的日期
	 */
	public static String getDailyByTime(long time) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String result = format.format(new Date(time * 1000l));
		return result.replaceAll("-", "");
	}

	/**
	 * 获取某天的起始时间
	 */
	public static long getSomeDayTime(int N) {
		Date now = new Date();
		long time = now.getTime() / 1000 + N * 86400;
		time = time - time % 86400;
		return time;
	}

}
