package cc.pp.tencent.utils.map;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

/**
 * Map处理工具类
 * @author wgybzb
 *
 */
public class MapUtils {

	/**
	 * 将HashMap中的数据归一化成百分比
	 */
	public static HashMap<String, String> tranToRatio(HashMap<Integer, Integer> table) {

		HashMap<String, String> result = new HashMap<String, String>();
		int sum = 0;
		Iterator<Integer> iterator = table.keySet().iterator();
		while (iterator.hasNext()) {
			sum += table.get(iterator.next());
		}
		iterator = table.keySet().iterator();
		while (iterator.hasNext()) {
			Integer temp = iterator.next();
			result.put(Integer.toString(temp),
					Float.toString((float) (Math.round(((float) table.get(temp) / sum) * 10000)) / 100) + "%");
		}

		return result;
	}

	/**
	 * 合并多个Map
	 */
	public static HashMap<Long, Integer> unionMaps(List<HashMap<Long, Integer>> table) {

		HashMap<Long, Integer> result = new HashMap<Long, Integer>();
		for (HashMap<Long, Integer> hs : table) {
			for (Entry<Long, Integer> temp : hs.entrySet()) {
				if (result.get(temp.getKey()) == null) {
					result.put(temp.getKey(), temp.getValue());
				} else {
					result.put(temp.getKey(), result.get(temp.getKey()) + temp.getValue());
				}
			}
		}

		return result;
	}

	/**
	 * 获取HashMap值的总和
	 */
	public static int getSum(HashMap<Long, Integer> table) {

		int sum = 0;
		for (Entry<Long, Integer> temp : table.entrySet()) {
			sum += temp.getValue();
		}

		return sum;
	}

	/**
	 * 获取真实用户比例及数量
	 */
	public static int[] getRealUses(HashMap<Long, Integer> table, int num) {

		int[] result = new int[2];
		for (Entry<Long, Integer> temp : table.entrySet()) {
			if (temp.getValue() >= num) {
				result[0]++;
				result[1] += temp.getValue();
			}
		}

		return result;
	}

	/**
	 * 每小时真实用户比例，真实用户使用的内容比例
	 */
	public static String[] getRealRatio(HashMap<Long, Integer> table, int num) {

		String[] result = new String[2];
		int[] hour = new int[2];
		int sum = 0;
		for (Entry<Long, Integer> temp : table.entrySet()) {
			sum += temp.getValue();
			// 使用次数少于num的为真实用户
			if (temp.getValue() <= num) {
				hour[0]++;
				hour[1] += temp.getValue();
			}
		}
		result[0] = Float.toString((float) (Math.round(((float) hour[0] / table.size()) * 10000)) / 100) + "%";
		result[1] = Float.toString((float) (Math.round(((float) hour[1] / sum) * 10000)) / 100) + "%";

		return result;
	}

	/**
	 * 每小时真实用户数，真实用户使用的内容数
	 */
	public static int[] getRealCount(HashMap<Long, Integer> table, int num) {

		int[] result = new int[2];
		for (Entry<Long, Integer> temp : table.entrySet()) {
			// 使用次数少于num的为真实用户
			if (temp.getValue() <= num) {
				result[0]++;
				result[1] += temp.getValue();
			}
		}

		return result;
	}

	/**
	 *  获取HashMap的最大key值
	 */
	public static int getMaxKey(HashMap<Integer, String> map) {

		Set<Integer> keys = map.keySet();
		Iterator<Integer> iterator = keys.iterator();
		int max = 0;
		while (iterator.hasNext()) {
			max = Math.max(max, iterator.next());
		}

		return max;
	}

}
