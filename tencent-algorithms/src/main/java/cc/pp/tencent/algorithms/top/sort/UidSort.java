package cc.pp.tencent.algorithms.top.sort;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UidSort {

	private static Logger logger = LoggerFactory.getLogger(UidSort.class);

	/**
	 * 设置最大队长
	 */
	private int queue;

	/**
	 * 取出前top的用户
	 */
	private int top;

	private HashMap<String, Integer> uids;

	public UidSort(int queue, int top) {
		this.queue = queue;
		this.top = top;
		uids = new HashMap<String, Integer>();
	}

	/**
	 * 设置队长
	 */
	public void setQueue(int queue) {
		this.queue = queue;
	}

	/**
	 * 设置top数
	 */
	public void setTop(int top) {
		this.top = top;
	}

	public int getTop() {
		return top;
	}

	public int getQueue() {
		return queue;
	}

	/**
	 * 压入数据
	 */
	public void popUid(String uid) {
		if (uids.get(uid) == null) {
			uids.put(uid, 1);
		} else {
			uids.put(uid, uids.get(uid) + 1);
		}
	}

	/**
	 * 获取top数据
	 */
	public List<String> sort() {

		if (uids.size() < top) {
			logger.info("The size of uids less than top.");
			return null;
		}
		int sum = 0;
		String[] result = new String[top];
		initResult(result);
		for (Entry<String, Integer> temp : uids.entrySet()) {
			sum = sum + temp.getValue();
			result = InsertSort.toptable(result, temp.getKey() + "=" + temp.getValue());
		}
		if (queue != sum) {
			logger.info("The size of uids less than queue.");
			return null;
		}

		return Arrays.asList(result);
	}

	/**
	 * 重置计数器
	 */
	public void reset() {
		queue = 0;
		top = 0;
		uids = new HashMap<String, Integer>();
	}

	/**
	 * 重置计数器
	 */
	public void reset(int queue, int top) {
		this.queue = queue;
		this.top = top;
		uids = new HashMap<String, Integer>();
	}

	private void initResult(String[] result) {
		for (int i = 0; i < result.length; i++) {
			result[i] = "0=0";
		}
	}

}
