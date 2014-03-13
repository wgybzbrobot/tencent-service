package cc.pp.tencent.algorithms.uids.repeat;

import java.util.HashSet;
import java.util.Iterator;

/**
 * 合并每天新浪用户的总粉丝，并找出用户每天新增粉丝uid
 * @author wgybzb
 *
 */
public class RetriveUnrepeats {

	/**
	 * 从oldUids中找出newUids中没有的uid,oldUids大于newUids
	 */
	public static String unRepeatUids(String oldUids, String[] newUids) {

		StringBuffer result = new StringBuffer();
		HashSet<String> oldSet = new HashSet<>();
		for (String uid : oldUids.split(",")) {
			oldSet.add(uid);
		}
		for (String uid : newUids) {
			if (!oldSet.contains(uid)) {
				result.append(uid).append(",");
			}
		}
		if (result.length() > 1) {
			return result.toString().substring(0, result.length() - 1);
		} else {
			return "";
		}
	}

	/**
	 * 合并oldUids和newUids
	 */
	public static String contactUids(String oldUids, String[] newUids) {

		StringBuffer result = new StringBuffer();
		HashSet<String> hs = new HashSet<>();
		for (String uid : oldUids.split(",")) {
			hs.add(uid);
		}
		for (String uid : newUids) {
			hs.add(uid);
		}
		Iterator<String> iterator = hs.iterator();
		while (iterator.hasNext()) {
			result.append(iterator.next()).append(",");
		}
		if (result.length() > 1) {
			return result.toString().substring(0, result.length() - 1);
		} else {
			return "";
		}
	}

}
