package cc.pp.tencent.algorithms.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cc.pp.tencent.algorithms.top.sort.InsertSort;

/**
 * 用于内容库数据统计的排序工具
 * @author wgybzb
 *
 */
public class SortUtils {

	/**
	 * HashMap排序成String
	 */
	public static String sortedToString(HashMap<String,Integer> temp) {

		List<Map.Entry<String,Integer>> resulttemp = new ArrayList<Map.Entry<String,Integer>>(temp.entrySet());
		Collections.sort(resulttemp, new Comparator<Map.Entry<String,Integer>>(){
			@Override
			public int compare(Map.Entry<String,Integer> o1, Map.Entry<String,Integer> o2) {
				return (o2.getValue() - o1.getValue());
			}
		});
		StringBuffer sortedtemp = new StringBuffer();
		for (int k = 0; k < temp.size(); k++) {
			sortedtemp.append(resulttemp.get(k).toString()).append(",");
		}

		return sortedtemp.substring(0, sortedtemp.length() - 1).toString();
	}

	/**
	 * HashMap排序成String[]
	 */
	public static String[] sortedToStrings(HashMap<String,Integer> temp) {

		List<Map.Entry<String,Integer>> resulttemp = new ArrayList<Map.Entry<String,Integer>>(temp.entrySet());
		Collections.sort(resulttemp, new Comparator<Map.Entry<String,Integer>>(){
			@Override
			public int compare(Map.Entry<String,Integer> o1, Map.Entry<String,Integer> o2) {
				return (o2.getValue() - o1.getValue());
			}
		});
		String[] sortedtemp = new String[temp.size()];
		for (int k = 0; k < temp.size(); k++) {
			sortedtemp[k] = resulttemp.get(k).toString();
		}

		return sortedtemp;
	}

	/**
	 * HashMap排序成List
	 */
	public static List<String> sortedToHashMap(HashMap<String, Integer> temp) {

		List<Map.Entry<String,Integer>> resulttemp = new ArrayList<Map.Entry<String,Integer>>(temp.entrySet());
		Collections.sort(resulttemp, new Comparator<Map.Entry<String,Integer>>(){
			@Override
			public int compare(Map.Entry<String,Integer> o1, Map.Entry<String,Integer> o2) {
				return (o2.getValue() - o1.getValue());
			}
		});
		List<String> sortedtemp = new ArrayList<String>();
		for (int k = 0; k < temp.size(); k++) {
			sortedtemp.add(resulttemp.get(k).toString());
		}

		return sortedtemp;
	}

	public static List<String> sortedToMap(HashMap<Long, Integer> temp, int N) {

		List<Map.Entry<Long, Integer>> resulttemp = new ArrayList<>(temp.entrySet());
		Collections.sort(resulttemp, new Comparator<Map.Entry<Long, Integer>>() {
			@Override
			public int compare(Map.Entry<Long, Integer> o1, Map.Entry<Long, Integer> o2) {
				return (o2.getValue() - o1.getValue());
			}
		});
		List<String> sortedtemp = new ArrayList<String>();
		for (int k = 0; k < Math.min(temp.size(), N); k++) {
			sortedtemp.add(resulttemp.get(k).toString());
		}

		return sortedtemp;
	}

	public static HashMap<Long, Integer> sortedToList(HashMap<Long, Integer> temp, int N) {

		List<Map.Entry<Long, Integer>> resulttemp = new ArrayList<>(temp.entrySet());
		Collections.sort(resulttemp, new Comparator<Map.Entry<Long, Integer>>() {
			@Override
			public int compare(Map.Entry<Long, Integer> o1, Map.Entry<Long, Integer> o2) {
				return (o2.getValue() - o1.getValue());
			}
		});
		HashMap<Long, Integer> result = new HashMap<>();
		String[] info;
		for (int k = 0; k < Math.min(temp.size(), N); k++) {
			info = resulttemp.get(k).toString().split("=");
			result.put(Long.parseLong(info[0]), Integer.parseInt(info[1]));
		}

		return result;
	}

	/**
	 * 排序成List<HashMap>
	 */
	public static List<HashMap<String, Integer>> sortedToDoubleMap(HashMap<Integer, Integer> temp, String keyname1,
			String keyname2, int num) {

		List<Map.Entry<Integer, Integer>> resulttemp = new ArrayList<>(temp.entrySet());
		Collections.sort(resulttemp, new Comparator<Map.Entry<Integer, Integer>>() {
			@Override
			public int compare(Map.Entry<Integer, Integer> o1, Map.Entry<Integer, Integer> o2) {
				return (o2.getValue() - o1.getValue());
			}
		});
		List<HashMap<String, Integer>> sortedtemp = new ArrayList<>();
		String info, key, value;
		for (int k = 0; k < Math.min(num, temp.size()); k++) {
			HashMap<String, Integer> t = new HashMap<>();
			info = resulttemp.get(k).toString();
			key = info.substring(0, info.indexOf("="));
			value = info.substring(info.indexOf("=") + 1);
			t.put(keyname1, Integer.parseInt(key));
			t.put(keyname2, Integer.parseInt(value));
			sortedtemp.add(t);
		}

		return sortedtemp;
	}

	/**
	 * 获取每小时的前10排序
	 */
	public static List<List<HashMap<String, String>>> getTop10PeerHour(int[][] temp24h,
			HashMap<Integer, String> contentTypes) {

		List<List<HashMap<String, String>>> result = new ArrayList<>();
		for (int i1 = 0; i1 < 24; i1++) {
			String[] table = new String[temp24h[0].length];
			for (int i2 = 0; i2 < table.length; i2++) {
				table[i2] = "0=0";
			}
			for (int i2 = 0; i2 < table.length; i2++) {
				if (temp24h[i1][i2] != 0) {
					table = InsertSort.toptable(table, contentTypes.get(i2) + "=" + temp24h[i1][i2]);
				}
			}
			List<HashMap<String, String>> list = new ArrayList<>();
			int temp1 = 0;
			for (int i2 = 0; i2 < table.length; i2++) {
				HashMap<String, String> top10 = new HashMap<>();
				if (table[i2].equals("0=0") || (temp1 > 10)) {
					break;
				} else {
					String[] t = table[i2].split("=");
					top10.put("colum", t[0]);
					top10.put("count", t[1]);
				}
				temp1++;
				list.add(top10);
			}
			result.add(list);
		}

		return result;
	}

}
