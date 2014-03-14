package cc.pp.tencent.bozhus.t2;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cc.pp.service.tencent.dao.info.TencentUserInfoDao;
import cc.pp.service.tencent.dao.info.TencentWeiboInfoDao;
import cc.pp.service.tencent.model.Infos;
import cc.pp.service.tencent.model.ShowWeibo;
import cc.pp.service.tencent.model.SimpleUser;
import cc.pp.service.tencent.model.UserTimeline;
import cc.pp.service.tencent.model.UserTimelineInfo;
import cc.pp.tencent.algorithms.top.sort.InsertSort;
import cc.pp.tencent.bozhus.common.SourceType;
import cc.pp.tencent.bozhus.domain.SimpleWeiboResult;

public class T2SingleWeiboAnalysis {

	private static Logger logger = LoggerFactory.getLogger(T2SingleWeiboAnalysis.class);

	private final TencentUserInfoDao tencentUserInfoDao;
	private final TencentWeiboInfoDao tencentWeiboInfoDao;

	public T2SingleWeiboAnalysis(TencentUserInfoDao tencentUserInfoDao, TencentWeiboInfoDao tencentWeiboInfoDao) {
		this.tencentUserInfoDao = tencentUserInfoDao;
		this.tencentWeiboInfoDao = tencentWeiboInfoDao;
	}

	/**
	 * 主函数
	 */
	public static void main(String[] args) throws Exception {


	}

	/**
	 * 分析函数
	 */
	public SimpleWeiboResult singleWeiboAnalysis(String url) throws Exception {

		// 结果初始化
		SimpleWeiboResult result = new SimpleWeiboResult();

		// 变量
		int exposionsum = 0; // 1、总曝光量
		int[] emotions = new int[3]; // 4、情感值
		String[] keyusersbyreps = new String[50]; // 7、关键转发用户，按转发量
		SWeiboUtils.initStrArray(keyusersbyreps);
		String[] keyusersbycoms = new String[50]; // 7、关键用户，按评论量
		SWeiboUtils.initStrArray(keyusersbycoms);
		int[] reposttimelineby24H = new int[24]; // // 8、24小时内的转发分布
		HashMap<String, Integer> reposttimelinebyDay = new HashMap<>(); //9、转发时间线，按照天
		int[] gender = new int[3]; // // 10、性别分析
		int[] location = new int[101]; // // 11、区域分布
		int[] wbsource = new int[6]; // 12、终端分布
		int[] reposterquality = new int[2]; // 13、转发用户质量
		int[] verifiedtype = new int[2]; // 14、认证分布
		HashMap<String, Integer> suminclass = new HashMap<>(); // // 15、层级分析  || @个数确定
		String str = ""; // 16、关键词分析，其中str为待分词的所有微博内容
		int[] weibotype = new int[8]; //17、微博类型,1-原创发表，2-转载，3-私信，4-回复，5-空回，6-提及，7-评论
		HashMap<Integer, String> usernames = new HashMap<>(); //存放用户名
		// 18、总体评价
		// 中间变量初始化
		int existwb = 0, wbsum = 0,isvip; // isvip：VIP类型
		String verifiy;
		long reposttime;
		// 获取该微博数据
		String wid = SWeiboUtils.getWid(url);
		/*********单条微博信息*********/
		ShowWeibo weibo = tencentWeiboInfoDao.getSingleWeiboDetail(wid);
		if (weibo.getData() == null) {
			logger.info("Wid=" + wid + " does not existed.");
			return null;
		}

		// 1、原创用户信息
		SWeiboUtils.originalUserArrange(result, weibo);
		// 2、微博内容
		result.setWbcontent(weibo.getData().getText());
		// 4、总转发量
		result.setRepostcount(weibo.getData().getCount());
		// 5、总评论量
		result.setCommentcount(weibo.getData().getMcount());

		/*********转发数据*********/
		UserTimeline reposters = tencentWeiboInfoDao.getTencentSingleWeiboResposts(wid);
		if (reposters.getData() == null) {
			logger.info("Wid=" + wid + " has no reposters.");
			return null;
		}

		long lasttime;
		String lastwid;
		int cursor = 2;
		while ((cursor * 100 < weibo.getData().getCount()) && (cursor <= 50)) {

			logger.info("Cursor=" + cursor);

			for (UserTimelineInfo info : reposters.getData().getInfo()) {
				existwb++;
				usernames.put(existwb, info.getName());
				str += info.getText();
				isvip = info.getIsvip();
				verifiy = (isvip == 0) ? "no" : "vip";
				reposttime = info.getTimestamp();
				// 6、情感值
				emotions[SWeiboUtils.getEmotions(info.getText())]++;
				// 7、关键账号
				keyusersbyreps = InsertSort.toptable(keyusersbyreps,
						info.getName() + "," + info.getNick() + "," + info.getHead() + "," + info.getType() + ","
								+ verifiy + "," + reposttime + "=" + info.getCount());
				keyusersbycoms = InsertSort.toptable(keyusersbyreps,
						info.getName() + "," + info.getNick() + "," + info.getHead() + "," + info.getType() + ","
								+ verifiy + "," + reposttime + "=" + info.getMcount());
				// 8、转发时间线（24小时）
				reposttimelineby24H[Integer.parseInt(SWeiboUtils.getHour(reposttime))]++;
				// 9、转发时间线--按天
				SWeiboUtils.putRepostByDay(reposttimelinebyDay, SWeiboUtils.getDate(reposttime));
				// 11、区域分布
				location[SWeiboUtils.checkCity(Integer.parseInt(info.getProvince_code()))]++;
				// 12、终端设备
				wbsource[SourceType.getCategory(info.getFrom())]++;
				// 14、认证分布
				verifiedtype[isvip]++; // 是否为VIP用户
				// 15、层级分析
				SWeiboUtils.putSumInClass(suminclass, info.getText());
				// 17、微博类型
				weibotype[info.getType()]++; //1-原创发表，2-转载，3-私信，4-回复，5-空回，6-提及，7-评论
			}
			lasttime = reposters.getData().getInfo().get(reposters.getData().getInfo().size() - 1).getTimestamp();
			lastwid = reposters.getData().getInfo().get(reposters.getData().getInfo().size() - 1).getId();
			reposters = tencentWeiboInfoDao.getTencentSingleWeiboResposts(wid, lasttime, lastwid);
			if (reposters.getData() == null) {
				break;
			}
		}

		/******用户基础数据******/
		int[] t = userBaseInfo(usernames, gender, reposterquality);
		exposionsum = t[1];
		if (existwb * t[0] == 0) {
			return null;
		}
		/******************整理数据结果*******************/
		// 3、总曝光量
		result.setExposionsum((long) exposionsum * wbsum / t[0]);
		// 6、情感值
		SWeiboUtils.emotionArrange(result, emotions);
		// 7、关键账号
		SWeiboUtils.repskeyusersArrange(result, keyusersbyreps, getBasedinfo(SWeiboUtils.tran2Uids(keyusersbyreps)));
		SWeiboUtils.comskeyusersArrange(result, keyusersbycoms, getBasedinfo(SWeiboUtils.tran2Uids(keyusersbycoms)));
		// 8、转发时间线（24小时）
		SWeiboUtils.reposttime24HArrange(result, reposttimelineby24H, existwb);
		// 9、转发时间线（按天）
		result.setReposttimelinebyDay(reposttimelinebyDay);
		// 10、性别分布
		SWeiboUtils.genderArrange(result, gender);
		// 11、区域分布
		SWeiboUtils.locationArrange(result, location, existwb);
		// 12、终端设备
		SWeiboUtils.sourceArrange(result, wbsource, existwb);
		// 13、水军比例
		SWeiboUtils.qualityArrange(result, reposterquality);
		// 14、认证分布
		SWeiboUtils.verifiedTypeArrange(result, verifiedtype, existwb);
		// 15、层级分析
		result.setSuminclass(suminclass);
		// 16、关键词提取
		SWeiboUtils.keywordsArrange(result, str);
		// 17、微博类型
		SWeiboUtils.weibotypeArrange(result, weibotype, existwb);
		// 18、总体评价
		SWeiboUtils.lastCommentArrange(result, (long) exposionsum * wbsum / t[0], emotions, gender, location,
				reposterquality);

		return result;
	}

	/**
	 * 获取用户的信息
	 */
	private HashMap<String, String> getBasedinfo(HashMap<Integer, String> usernames) throws Exception {

		HashMap<String, String> result = new HashMap<>();
		String uids = "";
		/*****************获取所有pp用户****************/
		for (int i = 0; i < usernames.size();) {

			uids = getUids(usernames, i);
			/*****************采集用户信息******************/
			Infos userinfos = tencentUserInfoDao.getTencentUserBaseInfos(uids);
			if (userinfos.getData() == null) {
				i++;
				continue;
			}
			for (SimpleUser userinfo : userinfos.getData().getInfo()) {
				result.put(userinfo.getName(), userinfo.getFansnum() + "");
			}
			if (usernames.size() - 30 > i) {
				i = i + 30;
			} else {
				i = usernames.size();
			}
		}

		return result;
	}

	/**
	 * 获取uid串
	 */
	private String getUids(HashMap<Integer, String> usernames, int i) {

		String uids = null;
		if (usernames.size() - 30 > i) {
			for (int k = 0; k < 30; k++) {
				uids += usernames.get(i + k) + ",";
			}
		} else {
			for (int k = i; k < usernames.size(); k++) {
				uids += usernames.get(k) + ",";
			}
		}
		uids = uids.substring(0, uids.length() - 1);

		return uids;
	}

	/**
	 * 用户基础数据处理
	 */
	private int[] userBaseInfo(HashMap<Integer, String> usernames, int[] gender, int[] reposterquality)
			throws Exception {

		Infos userinfos;
		int exposionsum = 0, usersum = 0, count = 1;
		if (usernames.size() > 30) {
			count = usernames.size() / 30;
		}
		count = (count < 10) ? count : 10;
		String uids = "";
		for (int i = 0; i < count; i++) {
			for (int j = 0; j < 30; j++) {
				uids += usernames.get(i * 30 + j) + ",";
			}
			uids = uids.substring(0, uids.length() - 1);
			userinfos = tencentUserInfoDao.getTencentUserBaseInfos(uids);
			if (userinfos.getData() == null) {
				continue;
			}
			for (SimpleUser userinfo : userinfos.getData().getInfo()) {
				usersum++;
				// 10、性别分布
				gender[userinfo.getSex()]++; //1-男，2-女，0-未填写
				// 13、转发用户质量（水军比例）
				reposterquality[SWeiboUtils.checkfans(userinfo.getFansnum())]++;
				exposionsum += userinfo.getFansnum();
			}
			uids = "";
		}
		int[] result = new int[2];
		result[0] = usersum;
		result[1] = exposionsum;

		return result;
	}

}
