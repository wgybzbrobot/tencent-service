package cc.pp.tencent.bozhus.t2;

import java.util.HashMap;
import java.util.List;

import cc.pp.service.tencent.model.Tag;
import cc.pp.tencent.algorithms.top.sort.PPSort;
import cc.pp.tencent.bozhus.domain.FansAnalysisResult;

public class FansUtils {

	/**
	 * 初始化top排序数组
	 */
	public static void initTopArray(String[] array) {
		for (int i = 0; i < array.length; i++) {
			array[i] = "0=0";
		}
	}

	/**
	 * 判断年龄
	s	 */
	public static int checkAge(int fanssum) {
		if (fanssum < 30) {
			return 0; //0----"others"
		} else if (fanssum < 5000) {
			return 1; //1----"80s"
		} else if (fanssum < 10000) {
			return 2; //2----"90s"
		} else if (fanssum < 100000) {
			return 3; //3----"70s"
		} else {
			return 4; //4----"60s"
		}
	}

	/**
	 * 判断城市编码
	 */
	public static int checkLocation(int city) {
		if (city == 400) {
			return 0;
		} else {
			return city;
		}
	}

	/**
	 * 判断性别
	 */
	public static int checkGender(String sex) {
		if (sex.equals("m")) {
			return 0; //0----m 男
		} else {
			return 1; //1----f 女
		}
	}

	/**
	 * 判断是否认证
	 */
	public static int checkVerified(int addv) {
		if (addv == 0) {
			return 0;
		} else {
			return 1;
		}
	}

	/**
	 * 判断粉丝质量
	 */
	public static int checkQuality(int fanssum) {
		if (fanssum < 50) {
			return 0;
		} else {
			return 1;
		}
	}

	/**
	 * 判断粉丝等级
	 */
	public static int checkFansGrade(int fanssum) {
		if (fanssum < 100) {
			return 0; //0-----"X<100"
		} else if (fanssum < 1000) {
			return 1; //1-----"100<X<1000"
		} else if (fanssum < 10000) {
			return 2; //2-----"1000<X<1w"
		} else if (fanssum < 100000) {
			return 3; //3-----"1w<X<10w"
		} else {
			return 4; //4-----"X>10w"
		}
	}

	/**
	 * 判断认证类型
	 */
	public static int checkVerifiedType(int verifytype) {
		if ((verifytype < 9) && (verifytype >= 0)) {
			return verifytype;
		} else if (verifytype == 200) {
			return 9;
		} else if (verifytype == 220) {
			return 10;
		} else if (verifytype == 400) {
			return 11;
		} else if (verifytype == -1) {
			return 12;
		} else {
			return 13;
		}
	}

	/**
	 * 处理标签信息
	 */
	public static void tackleTags(HashMap<String, Integer> hottags, List<Tag> tags) {
		if (tags == null) {
			return;
		}
		for (Tag tag : tags) {
			if (hottags.get(tag.getName()) == null) {
				hottags.put(tag.getName(), 1);
			} else {
				hottags.put(tag.getName(), hottags.get(tag.getName()) + 1);
			}
		}
	}
	/**
	 * 终端设备分布（或微博来源）结果整理
	 */
	public static void sourceArrange(FansAnalysisResult result, int[] wbsource, int existuids) {
		result.getSource().put("tencent",
				Float.toString((float) Math.round(((float) wbsource[0] / existuids) * 10000) / 100) + "%");
		result.getSource().put("pc",
				Float.toString((float) Math.round(((float) wbsource[1] / existuids) * 10000) / 100) + "%");
		result.getSource().put("android",
				Float.toString((float) Math.round(((float) wbsource[2] / existuids) * 10000) / 100) + "%");
		result.getSource().put("iphone",
				Float.toString((float) Math.round(((float) wbsource[3] / existuids) * 10000) / 100) + "%");
		result.getSource().put("ipad",
				Float.toString((float) Math.round(((float) wbsource[4] / existuids) * 10000) / 100) + "%");
		result.getSource().put("others",
				Float.toString((float) Math.round(((float) wbsource[5] / existuids) * 10000) / 100) + "%");
	}

	/**
	 * 年龄分析结果整理
	 */
	public static void ageArrange(FansAnalysisResult result, int[] age, int existuids) {
		result.getAge().put("others",
				Float.toString((float) Math.round(((float) age[0] / existuids) * 10000) / 100) + "%");
		result.getAge()
				.put("80s", Float.toString((float) Math.round(((float) age[1] / existuids) * 10000) / 100) + "%");
		result.getAge()
				.put("90s", Float.toString((float) Math.round(((float) age[2] / existuids) * 10000) / 100) + "%");
		result.getAge()
				.put("70s", Float.toString((float) Math.round(((float) age[3] / existuids) * 10000) / 100) + "%");
		result.getAge()
				.put("60s", Float.toString((float) Math.round(((float) age[4] / existuids) * 10000) / 100) + "%");
	}

	/**
	 * 区域分析结果整理
	 */
	public static void locationArrange(FansAnalysisResult result, int[] province, int existuids) {
		if (province[0] != 0) {
			result.getLocation().put("400",
					Float.toString((float) Math.round(((float) province[0] / existuids) * 10000) / 100) + "%");
		}
		for (int j = 1; j < province.length; j++) {
			if (province[j] != 0) {
				result.getLocation().put(Integer.toString(j),
						Float.toString((float) Math.round(((float) province[j] / existuids) * 10000) / 100) + "%");
			}
		}
	}

	/**
	 * 性别分析结果整理
	 */
	public static void genderArrange(FansAnalysisResult result, int[] gender) {
		result.getGender().put("m",
				Float.toString((float) Math.round(((float) gender[1] / (gender[1] + gender[2])) * 10000) / 100) + "%");
		result.getGender().put("f",
				Float.toString((float) Math.round(((float) gender[2] / (gender[1] + gender[2])) * 10000) / 100) + "%");
	}

	/**
	 * Top40粉丝结果整理
	 */
	public static void topFansArrange(FansAnalysisResult result, String[] top40fans) {
		for (int n = 0; n < 40; n++) {
			if (top40fans[n].length() > 5) {
				result.getTop40byfans().put(Integer.toString(n), top40fans[n].substring(0, top40fans[n].indexOf("=")));
				top40fans[n] = top40fans[n].substring(0, top40fans[n].indexOf("="));
			} else {
				top40fans[n] = null;
			}
		}
	}

	/**
	 * 粉丝质量结果整理
	 */
	public static void qualityArrange(FansAnalysisResult result, int[] quality, int existuids) {
		result.getFansQuality().put("mask",
				Float.toString((float) Math.round(((float) quality[0] / existuids) * 10000) / 100) + "%");
		result.getFansQuality().put("real",
				Float.toString((float) Math.round(((float) quality[1] / existuids) * 10000) / 100) + "%");
	}

	/**
	 * 粉丝等级结果整理
	 */
	public static void fansGradeArrange(FansAnalysisResult result, int[] gradebyfans, int existuids) {
		result.getGradebyfans().put("<100",
				Float.toString((float) Math.round(((float) gradebyfans[0] / existuids) * 10000) / 100) + "%");
		result.getGradebyfans().put("100~1000",
				Float.toString((float) Math.round(((float) gradebyfans[1] / existuids) * 10000) / 100) + "%");
		result.getGradebyfans().put("1000~1w",
				Float.toString((float) Math.round(((float) gradebyfans[2] / existuids) * 10000) / 100) + "%");
		result.getGradebyfans().put("1w~10w",
				Float.toString((float) Math.round(((float) gradebyfans[3] / existuids) * 10000) / 100) + "%");
		result.getGradebyfans().put(">10w",
				Float.toString((float) Math.round(((float) gradebyfans[4] / existuids) * 10000) / 100) + "%");
	}

	/**
	 * 认证分布结果整理
	 */
	public static void verifiedTypeArrange(FansAnalysisResult result, int[] verifiedtype, int existuids) {
		result.getVerifiedtype().put("no",
				Float.toString((float) Math.round(((float) verifiedtype[0] / existuids) * 10000) / 100) + "%");
		result.getVerifiedtype().put("vip",
				Float.toString((float) Math.round(((float) verifiedtype[1] / existuids) * 10000) / 100) + "%");
	}

	/**
	 * 计算活跃粉丝数
	 */
	public static void activeCountArrange(FansAnalysisResult result, int[] quality, int userfanssum, int existuids) {
		float r = 0.0f;
		if (userfanssum > 3000) {
			r = (float) existuids / 3000;
		} else {
			r = (float) existuids / userfanssum;
		}
		int activefanssum = Math.round(((float) quality[1] / existuids) * userfanssum * r);
		result.setActivefanssum(activefanssum);
	}

	/**
	 * 热门标签结果整理
	 */
	public static void tagsArrange(FansAnalysisResult result, HashMap<String, Integer> hottags) {
		String[] sortedtags = PPSort.sortedToStrings(hottags);
		int max = (hottags.size() > 20) ? 20 : hottags.size();
		String tag, weight;
		for (int i = 0; i < max; i++) {
			tag = sortedtags[i].substring(0, sortedtags[i].indexOf("="));
			weight = sortedtags[i].substring(sortedtags[i].indexOf("=") + 1);
			HashMap<String, String> temp = new HashMap<String, String>();
			temp.put("tag", tag);
			temp.put("weight", weight);
			result.getHottags().put(Integer.toString(i), temp);
		}
	}
}
