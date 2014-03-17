package cc.pp.tencent.bozhus.t2;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class T2Utils {

	private static SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
	private static SimpleDateFormat format3 = new SimpleDateFormat("HH");

	private static final Random RANDOM = new Random();

	/**
	 * 根据时间戳（秒）获取日期（年月日时分秒）
	 */
	public static String getTime(long time) {
		return format1.format(new Date(time * 1000l));
	}

	/**
	 * 根据时间戳（秒）获取日期（年月日）
	 */
	public static String getDate(long time) {
		return format2.format(new Date(time * 1000l)).replaceAll("-", "");
	}

	/**
	 * 根据时间戳（秒）获取小时
	 */
	public static String getHour(long time) {
		String result = format3.format(new Date(time * 1000l));
		if (result.substring(0, 1).equals("0")) {
			result = result.substring(1);
		}
		return result;
	}

	public static int getEmotions(String text) {
		return RANDOM.nextInt(3);
	}

	public static int[] transData(int[] emotions) {

		int[] result = new int[emotions.length];
		int sum = emotions[0] + emotions[1] + emotions[2];
		float negative = (float) emotions[0] / sum;
		float positive = (float) emotions[1] / sum;
		float neutral = (float) emotions[2] / sum;
		if (negative > 0.5) {
			result[0] = (int) Math.round(Math.random() * emotions[0]);
			result[1] = emotions[0] - result[0] + emotions[1];
			result[2] = emotions[2];
			return result;
		}
		if (positive > 0.95) {
			result[1] = (int) Math.round(Math.random() * emotions[1]);
			result[2] = emotions[1] - result[1] + emotions[2];
			result[0] = emotions[0];
			return result;
		}
		if (neutral > 0.95) {
			result[2] = (int) Math.round(Math.random() * emotions[2]);
			result[1] = emotions[2] - result[2] + emotions[1];
			result[0] = emotions[0];
			return result;
		}
		result = emotions;

		return result;
	}

}
