package cc.pp.tencent.algorithms.top.sort;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Title: 对Map进行排序工具
 * @author wanggang
 * @version 1.1
 * @since 2013-05-27
 */
public class PPSort {

	/**
	 * 将HashMap排序成字符串数组
	 */
	public static String[] sortedToStrings(HashMap<String, Integer> temp) {

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
	 * 将HashMap排序成HashMap
	 */
	public static HashMap<String, Float> sortedToHashMap(HashMap<String, Float> temp, int N) {

		List<Map.Entry<String, Float>> resulttemp = new ArrayList<>(temp.entrySet());
		Collections.sort(resulttemp, new Comparator<Map.Entry<String, Float>>() {
			@Override
			public int compare(Map.Entry<String, Float> o1, Map.Entry<String, Float> o2) {
				return (int) (o2.getValue() - o1.getValue());
			}
		});

		HashMap<String, Float> sortedtemp = new HashMap<>();
		for (int k = 0; k < Math.min(temp.size(), N); k++) {
			String[] t = resulttemp.get(k).toString().split("=");
			sortedtemp.put(t[0], Float.parseFloat(t[1]));
		}

		return sortedtemp;
	}

	public static HashMap<String, Integer> sortedToHashMapInteger(HashMap<String, Integer> temp, int N) {

		List<Map.Entry<String, Integer>> resulttemp = new ArrayList<>(temp.entrySet());
		Collections.sort(resulttemp, new Comparator<Map.Entry<String, Integer>>() {
			@Override
			public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
				return o2.getValue() - o1.getValue();
			}
		});

		HashMap<String, Integer> sortedtemp = new HashMap<>();
		for (int k = 0; k < Math.min(temp.size(), N); k++) {
			String[] t = resulttemp.get(k).toString().split("=");
			sortedtemp.put(t[0], Integer.parseInt(t[1]));
		}

		return sortedtemp;
	}

	/**
	 * 找出一个数组中的最大值对应的下标
	 */
	public static int getMaxId(int[] array) {

		int max = 0, index = 0;
		for (int i = 0; i < array.length; i++) {
			if (array[i] > max) {
				max = array[i];
				index = i;
			}
		}

		return index;
	}

	/**
	 * 将HashMap排序，并提取前num个元素
	 * @param temp：待排序的Map
	 * @param keyname1：键名1
	 * @param keyname2：键名2
	 * @param num：提取的排序数
	 */
	public static List<HashMap<String, String>> sortedToDoubleMap(HashMap<String, Long> temp,
			String keyname1, String keyname2, int num) {

		List<Map.Entry<String, Long>> resulttemp = new ArrayList<>(temp.entrySet());
		Collections.sort(resulttemp, new Comparator<Map.Entry<String, Long>>() {
			@Override
			public int compare(Map.Entry<String, Long> o1, Map.Entry<String, Long> o2) {
				return (int) (o2.getValue() - o1.getValue());
			}
		});

		List<HashMap<String, String>> sortedtemp = new ArrayList<>();
		String[] info = null;
		for (int k = 0; k < Math.min(num, temp.size()); k++) {
			HashMap<String, String> t = new HashMap<>();
			info = resulttemp.get(k).toString().split("=");
			t.put(keyname1, info[0]);
			t.put(keyname2, info[1]);
			sortedtemp.add(t);
		}

		return sortedtemp;
	}

}