package cc.pp.tencent.bozhus.domain;

import java.util.HashMap;

public class FansAnalysisResult {

	// 1、粉丝总数
	private int fansSum;
	// 2、活跃粉丝数
	private int activeFansSum;
	// 3、粉丝使用的终端设备来源分布
	private HashMap<String, String> source = new HashMap<>();
	// 4、认证分布
	private HashMap<String, String> verifiedType = new HashMap<>();
	// 5、粉丝的热门标签
	private HashMap<String, HashMap<String, String>> hotTags = new HashMap<>();
	// 6、粉丝等级分布（按粉丝量）
	private HashMap<String, String> gradeByFans = new HashMap<>();
	// 7、年龄分析
	private HashMap<String, String> age = new HashMap<>();
	// 8、性别分析
	private HashMap<String, String> gender = new HashMap<>();
	// 9、区域分析
	private HashMap<String, String> location = new HashMap<>();
	// 10、水军分析
	private HashMap<String, String> fansQuality = new HashMap<>();
	// 11、粉丝top分析（按粉丝量）
	private HashMap<String, String> topNByFans = new HashMap<>();
	// 12、认证比例
	private String addVRatio;
	// 13、粉丝活跃时间分析
	private HashMap<String, String> activeTimeline = new HashMap<>();
	// 14、增长趋势分析
	private HashMap<String, Integer> fansAddTimeline = new HashMap<>();

	public int getFansSum() {
		return fansSum;
	}

	public void setFansSum(int fansSum) {
		this.fansSum = fansSum;
	}

	public int getActiveFansSum() {
		return activeFansSum;
	}

	public void setActiveFansSum(int activeFansSum) {
		this.activeFansSum = activeFansSum;
	}
	public HashMap<String, String> getSource() {
		return source;
	}
	public void setSource(HashMap<String, String> source) {
		this.source = source;
	}

	public HashMap<String, String> getVerifiedType() {
		return verifiedType;
	}

	public void setVerifiedType(HashMap<String, String> verifiedType) {
		this.verifiedType = verifiedType;
	}

	public HashMap<String, HashMap<String, String>> getHotTags() {
		return hotTags;
	}

	public void setHotTags(HashMap<String, HashMap<String, String>> hotTags) {
		this.hotTags = hotTags;
	}

	public HashMap<String, String> getGradeByFans() {
		return gradeByFans;
	}

	public void setGradeByFans(HashMap<String, String> gradeByFans) {
		this.gradeByFans = gradeByFans;
	}
	public HashMap<String, String> getAge() {
		return age;
	}
	public void setAge(HashMap<String, String> age) {
		this.age = age;
	}
	public HashMap<String, String> getGender() {
		return gender;
	}
	public void setGender(HashMap<String, String> gender) {
		this.gender = gender;
	}
	public HashMap<String, String> getLocation() {
		return location;
	}
	public void setLocation(HashMap<String, String> location) {
		this.location = location;
	}
	public HashMap<String, String> getFansQuality() {
		return fansQuality;
	}
	public void setFansQuality(HashMap<String, String> fansQuality) {
		this.fansQuality = fansQuality;
	}

	public HashMap<String, String> getTopNByFans() {
		return topNByFans;
	}

	public void setTopNByFans(HashMap<String, String> topNByFans) {
		this.topNByFans = topNByFans;
	}
	public String getAddVRatio() {
		return addVRatio;
	}
	public void setAddVRatio(String addVRatio) {
		this.addVRatio = addVRatio;
	}

	public HashMap<String, String> getActiveTimeline() {
		return activeTimeline;
	}

	public void setActiveTimeline(HashMap<String, String> activeTimeline) {
		this.activeTimeline = activeTimeline;
	}

	public HashMap<String, Integer> getFansAddTimeline() {
		return fansAddTimeline;
	}

	public void setFansAddTimeline(HashMap<String, Integer> fansAddTimeline) {
		this.fansAddTimeline = fansAddTimeline;
	}

}
