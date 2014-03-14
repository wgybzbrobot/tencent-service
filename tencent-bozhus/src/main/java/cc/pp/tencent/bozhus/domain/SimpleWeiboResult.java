package cc.pp.tencent.bozhus.domain;

import java.util.HashMap;

public class SimpleWeiboResult {

	// 1、用户信息
	private HashMap<String, String> originaluserinfos = new HashMap<>();
	// 2、微博内容
	private String wbcontent = "";
	// 3、总曝光量
	private long exposionsum;
	// 4、总转发量
	private int repostcount;
	// 5、总评论量
	private int commentcount;
	// 6、情感值
	private HashMap<String, String> emotion = new HashMap<>();
	// 7、关键转发用户
	private HashMap<String, HashMap<String, String>> keyusersbyreps = new HashMap<>(); // 按转发量
	private HashMap<String, HashMap<String, String>> keyusersbyfans = new HashMap<>(); // 按照粉丝量
	// 8、24小时内的转发分布
	private HashMap<String, String> reposttimelineby24H = new HashMap<>();
	// 9、转发时间线，按照天
	private HashMap<String, Integer> reposttimelinebyDay = new HashMap<>();
	// 10、性别分析
	private HashMap<String, String> gender = new HashMap<>();
	// 11、区域分布
	private HashMap<String, String> location = new HashMap<>();
	// 12、终端分布
	private HashMap<String, String> source = new HashMap<>();
	// 13、转发用户质量
	private HashMap<String, String> reposterquality = new HashMap<>();
	// 14、认证分布
	private HashMap<String, String> verifiedtype = new HashMap<>();
	// 15、层级分析
	private HashMap<String, Integer> suminclass = new HashMap<>();
	// 16、关键字提取
	private HashMap<String, String> keywords = new HashMap<>();
	// 17、微博类型
	private HashMap<String, String> weibotype = new HashMap<>();
	// 18、总体评价
	private String lastcomment = "";

	public HashMap<String, String> getOriginaluserinfos() {
		return originaluserinfos;
	}

	public void setOriginaluserinfos(HashMap<String, String> originaluserinfos) {
		this.originaluserinfos = originaluserinfos;
	}
	public String getWbcontent() {
		return wbcontent;
	}
	public void setWbcontent(String wbcontent) {
		this.wbcontent = wbcontent;
	}

	public long getExposionsum() {
		return exposionsum;
	}

	public void setExposionsum(long exposionsum) {
		this.exposionsum = exposionsum;
	}

	public int getRepostcount() {
		return repostcount;
	}

	public void setRepostcount(int repostcount) {
		this.repostcount = repostcount;
	}

	public int getCommentcount() {
		return commentcount;
	}

	public void setCommentcount(int commentcount) {
		this.commentcount = commentcount;
	}
	public HashMap<String, String> getEmotion() {
		return emotion;
	}
	public void setEmotion(HashMap<String, String> emotion) {
		this.emotion = emotion;
	}
	public HashMap<String, HashMap<String, String>> getKeyusersbyreps() {
		return keyusersbyreps;
	}
	public void setKeyusersbyreps(HashMap<String, HashMap<String, String>> keyusersbyreps) {
		this.keyusersbyreps = keyusersbyreps;
	}
	public HashMap<String, HashMap<String, String>> getKeyusersbyfans() {
		return keyusersbyfans;
	}
	public void setKeyusersbyfans(HashMap<String, HashMap<String, String>> keyusersbyfans) {
		this.keyusersbyfans = keyusersbyfans;
	}
	public HashMap<String, String> getReposttimelineby24H() {
		return reposttimelineby24H;
	}
	public void setReposttimelineby24H(HashMap<String, String> reposttimelineby24h) {
		reposttimelineby24H = reposttimelineby24h;
	}

	public HashMap<String, Integer> getReposttimelinebyDay() {
		return reposttimelinebyDay;
	}

	public void setReposttimelinebyDay(HashMap<String, Integer> reposttimelinebyDay) {
		this.reposttimelinebyDay = reposttimelinebyDay;
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
	public HashMap<String, String> getSource() {
		return source;
	}
	public void setSource(HashMap<String, String> source) {
		this.source = source;
	}
	public HashMap<String, String> getReposterquality() {
		return reposterquality;
	}
	public void setReposterquality(HashMap<String, String> reposterquality) {
		this.reposterquality = reposterquality;
	}

	public HashMap<String, String> getVerifiedtype() {
		return verifiedtype;
	}

	public void setVerifiedtype(HashMap<String, String> verifiedtype) {
		this.verifiedtype = verifiedtype;
	}
	public HashMap<String, Integer> getSuminclass() {
		return suminclass;
	}
	public void setSuminclass(HashMap<String, Integer> suminclass) {
		this.suminclass = suminclass;
	}
	public HashMap<String, String> getKeywords() {
		return keywords;
	}
	public void setKeywords(HashMap<String, String> keywords) {
		this.keywords = keywords;
	}

	public HashMap<String, String> getWeibotype() {
		return weibotype;
	}

	public void setWeibotype(HashMap<String, String> weibotype) {
		this.weibotype = weibotype;
	}
	public String getLastcomment() {
		return lastcomment;
	}
	public void setLastcomment(String lastcomment) {
		this.lastcomment = lastcomment;
	}

}
