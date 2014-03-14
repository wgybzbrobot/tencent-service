package cc.pp.service.tencent.model;

import java.util.Map;

public class Weibo {

	public String text; // 微博内容,
	public String origtext; // 原始内容,
	public int count; // 微博被转次数,
	public int mcount; // 点评次数,
	public int readcount; // 阅读数
	public String from; // 来源,
	public String fromurl; // 来源url,
	public String id; // 微博唯一id,
	public String[] image; // 图片url列表,
	public Video video; // 视频信息
	public String music; // 音频信息
	public String name; // 发表人帐户名,
	public String openid; // 用户唯一id，与name相对应,
	public String nick; // 发表人昵称,
	public int self; // 是否自已发的的微博，0-不是，1-是,
	public long timestamp; // 发表时间,
	public int type; // 微博类型，1-原创发表，2-转载，3-私信，4-回复，5-空回，6-提及，7-评论,
	public String head; // 发表者头像url,
	public String https_head;
	public int likecount; // 微博赞数量,
	public String location; // 发表者所在地,
	public String country_code; // 国家码（与地区发表时间线一样）,
	public String province_code; // 省份码（与地区发表时间线一样）,
	public String city_code; // 城市码（与地区发表时间线一样）,
	public int isvip; // 是否微博认证用户，0-不是，1-是,
	public String geo; // 发表者地理信息,
	public int status; // 微博状态，0-正常，1-系统删除，2-审核中，3-用户删除，4-根删除,
	public String emotionurl; // 心情图片url,
	public int emotiontype; // 心情类型,
	public int isrealname; // 是否实名认证，0-老用户，1-已实名认证，2-未实名认证,
	public String longitude; // 经度,
	public String latitude; // 纬度,
	public Weibo source; // 当type=2时，source即为源tweet,
	public Map<String, String> user; // key=name, value=nick

	public String jing;
	public String wei;

	@Override
	public String toString() {
		return "Weibo{" +
				"text='" + text + '\'' +
				", origtext='" + origtext + '\'' +
				", count=" + count +
				", mcount=" + mcount +
				", readcount=" + readcount +
				", from='" + from + '\'' +
				", fromurl='" + fromurl + '\'' +
				", id='" + id + '\'' +
				", image='" + image + '\'' +
				", video=" + video +
				", music='" + music + '\'' +
				", name='" + name + '\'' +
				", openid='" + openid + '\'' +
				", nick='" + nick + '\'' +
				", self=" + self +
				", timestamp=" + timestamp +
				", type=" + type +
				", head='" + head + '\'' +
				", https_head='" + https_head + '\'' +
				", likecount=" + likecount +
				", location='" + location + '\'' +
				", country_code='" + country_code + '\'' +
				", province_code='" + province_code + '\'' +
				", city_code='" + city_code + '\'' +
				", isvip=" + isvip +
				", geo='" + geo + '\'' +
				", status=" + status +
				", emotionurl='" + emotionurl + '\'' +
				", emotiontype=" + emotiontype +
				", isrealname=" + isrealname +
				", longitude='" + longitude + '\'' +
				", latitude='" + latitude + '\'' +
				", source=" + source +
				", user=" + user +
				", jing='" + jing + '\'' +
				", wei='" + wei + '\'' +
				'}';
	}

	public static class Video {

		public String picurl; // 缩略图,
		public String player; // 播放器地址,
		public String realurl; // 视频原地址,
		public String shorturl; // 视频的短url,
		public String title; // 视频标题

		@Override
		public String toString() {
			return "Video{" + "picurl='" + picurl + '\'' + ", player='" + player + '\'' + ", realurl='" + realurl
					+ '\'' + ", shorturl='" + shorturl + '\'' + ", title='" + title + '\'' + '}';
		}
	}

	public static class Music {

		public String author; // 演唱者,
		public String url; // 音频地址,
		public String title; // 音频名字，歌名
		public long id;

		@Override
		public String toString() {
			return "Music{" + "id='" + id + "\'" + ",author='" + author + '\'' + ", url='" + url + '\'' + ", title='"
					+ title + '\'' + '}';
		}
	}

}