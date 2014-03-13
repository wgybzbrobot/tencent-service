package cc.pp.service.tencent.model;

import java.util.List;

public class FansInfo {

	private String city_code;
	private String country_code;
	private int fansnum;
	private String head;
	private String https_head;
	private int idolnum;
	private boolean isfans;
	private boolean isidol;
	private int isrealname;
	private int isvip;
	private String location;
	private String name;
	private String nick;
	private String openid;
	private String province_code;
	private int sex;
	private List<Tag> tag;
	private List<SimpleTweet> tweet;

	public String getCity_code() {
		return city_code;
	}

	public void setCity_code(String city_code) {
		this.city_code = city_code;
	}

	public String getCountry_code() {
		return country_code;
	}

	public void setCountry_code(String country_code) {
		this.country_code = country_code;
	}

	public int getFansnum() {
		return fansnum;
	}

	public void setFansnum(int fansnum) {
		this.fansnum = fansnum;
	}

	public String getHead() {
		return head;
	}

	public void setHead(String head) {
		this.head = head;
	}

	public String getHttps_head() {
		return https_head;
	}

	public void setHttps_head(String https_head) {
		this.https_head = https_head;
	}

	public int getIdolnum() {
		return idolnum;
	}

	public void setIdolnum(int idolnum) {
		this.idolnum = idolnum;
	}

	public boolean isIsfans() {
		return isfans;
	}

	public void setIsfans(boolean isfans) {
		this.isfans = isfans;
	}

	public boolean isIsidol() {
		return isidol;
	}

	public void setIsidol(boolean isidol) {
		this.isidol = isidol;
	}

	public int getIsrealname() {
		return isrealname;
	}

	public void setIsrealname(int isrealname) {
		this.isrealname = isrealname;
	}

	public int getIsvip() {
		return isvip;
	}

	public void setIsvip(int isvip) {
		this.isvip = isvip;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getProvince_code() {
		return province_code;
	}

	public void setProvince_code(String province_code) {
		this.province_code = province_code;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public List<Tag> getTag() {
		return tag;
	}

	public void setTag(List<Tag> tag) {
		this.tag = tag;
	}

	public List<SimpleTweet> getTweet() {
		return tweet;
	}

	public void setTweet(List<SimpleTweet> tweet) {
		this.tweet = tweet;
	}

}
