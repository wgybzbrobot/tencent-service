package cc.pp.tencent.bozhus.t2;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cc.pp.service.tencent.dao.info.TencentUserInfoDao;
import cc.pp.service.tencent.dao.info.TencentWeiboInfoDao;
import cc.pp.service.tencent.exception.TencentApiException;
import cc.pp.service.tencent.model.OtherInfoData;
import cc.pp.service.tencent.model.UserTimelineData;
import cc.pp.service.tencent.model.UserTimelineInfo;
import cc.pp.service.token.TencentTokenServiceImpl;
import cc.pp.service.token.tencent.TencentTokenService;
import cc.pp.sina.dao.common.MybatisConfig;
import cc.pp.sina.dao.t2.T2Interactions;
import cc.pp.sina.dao.t2.T2UserFans;
import cc.pp.sina.domain.t2.T2TencentInteractionsInfo;
import cc.pp.sina.utils.json.JsonUtils;
import cc.pp.sina.utils.time.TimeUtils;
import cc.pp.tencent.bozhus.common.TencentDaoUtils;

/**
 * Title: 用户与粉丝交互数据分析
 * @author wanggang
 * @version 1.1
 * @since 2013-05-27
 */
public class T2InteractionsAnalysis {

	private static Logger logger = LoggerFactory.getLogger(T2InteractionsAnalysis.class);

	private final TencentUserInfoDao tencentUserInfoDao;
	private final TencentWeiboInfoDao tencentWeiboInfoDao;
	private final T2GetAtInfo getAtInfo;

	private static T2Interactions interactions = new T2Interactions(MybatisConfig.ServerEnum.fenxi);
	private static T2UserFans userFans = new T2UserFans(MybatisConfig.ServerEnum.fenxi);

	public T2InteractionsAnalysis(TencentUserInfoDao tencentUserInfoDao,TencentWeiboInfoDao tencentWeiboInfoDao) {
		this.tencentUserInfoDao = tencentUserInfoDao;
		this.tencentWeiboInfoDao = tencentWeiboInfoDao;
		this.getAtInfo = new T2GetAtInfo(tencentWeiboInfoDao);
	}

	/**
	 * 测试函数
	 */
	public static void main(String[] args) {

		TencentTokenService tokenService = new TencentTokenServiceImpl();
		T2InteractionsAnalysis interactionsAnalysis = new T2InteractionsAnalysis(
				TencentDaoUtils.getTencentUserInfoDao(tokenService),
				TencentDaoUtils.getTencentWeiboInfoDao(tokenService));
		interactionsAnalysis.insertUserInteractionResult();

	}

	/**
	 * 存储交互数据
	 */
	public void insertUserInteractionResult() {
		List<String> uids = userFans.getTencentAllUids();
		for (String uid : uids) {
			logger.info("Tackle uid=" + uid);
			T2TencentInteractionsInfo interactionsInfo = userInteractionAnalysis(uid);
			if (interactionsInfo != null) {
				interactions.insertT2TencentInteractions(interactionsInfo.getUsername(), interactionsInfo.getDate(),
						interactionsInfo.getFanscount(), interactionsInfo.getAllcount(),
						interactionsInfo.getEmotionratio());
			} else {
				HashMap<String, String> emotionratio = new HashMap<>();
				emotionratio.put("negative", "0.0%");
				emotionratio.put("positive", "0.0%");
				emotionratio.put("neutral", "0.0%");
				interactions.insertT2TencentInteractions(uid, Integer.parseInt(TimeUtils.getTodayDaily()), 0, 0,
						JsonUtils.toJsonWithoutPretty(emotionratio));
			}
		}
	}

	/**
	 * 分析函数
	 */
	public T2TencentInteractionsInfo userInteractionAnalysis(String uid) {

		T2TencentInteractionsInfo result = new T2TencentInteractionsInfo();
		result.setUsername(uid);
		result.setDate(Integer.parseInt(T2Utils.getDate(System.currentTimeMillis() / 1000)));
		// 定义需要获取的变量
		int allcount = 0;
		int[] emotions = new int[3];
		HashMap<String,String> emotionratio = new HashMap<>();
		/******采集用户基础信息******/
		OtherInfoData userInfo = null;
		try {
			userInfo = tencentUserInfoDao.getTencentUserBaseInfo(uid);
		} catch (TencentApiException e) {
			userInfo = null;
		}
		if (userInfo == null) {
			logger.info("User '" + uid + "' does not existed.");
			return null;
		}
		result.setFanscount(userInfo.getFansnum());
		/******获取用户的@数据值******/
		int[] atInfo = getAtInfo.atInfoAnalysis(uid);
		allcount += atInfo[3];
		emotions[0] += atInfo[0];
		emotions[1] += atInfo[1];
		emotions[2] += atInfo[2];
		/******采集用户微博信息******/
		UserTimelineData userWeibos = null;
		try {
			userWeibos = tencentWeiboInfoDao.getTencentUserWeibos(uid);
		} catch (TencentApiException e) {
			userWeibos = null;
		}
		if (userWeibos == null) {
			logger.info("User '"+ uid + "' has no weibos.");
			return null;
		}
		long t = System.currentTimeMillis()/1000;
		int count = 0;
		UserTimelineData reposters;
		for (UserTimelineInfo weibo : userWeibos.getInfo()) {
			if (weibo.getTimestamp() < (t - 86400)) {
				break;
			}
			count = weibo.getCount() + weibo.getMcount();
			if (count == 0) {
				continue;
			}
			allcount += count;
			try {
				reposters = tencentWeiboInfoDao.getTencentSingleWeiboResposts(weibo.getId());
			} catch (TencentApiException e) {
				reposters = null;
			}
			if (reposters == null) { //返回错误信息处理
				continue;
			}
			try {
				for (UserTimelineInfo reposter : reposters.getInfo()) {
					emotions[T2Utils.getEmotions(reposter.getText())]++;
				}
			} catch (RuntimeException e) {
				continue;
			}
		}
		int[] remotions = T2Utils.transData(emotions);
		int sum = remotions[0] + remotions[1] + remotions[2];
		if ((allcount != 0) && (sum == 0)) {
			remotions[1] = 1;
		}
		if (sum == 0) {
			sum = 1;
		}
		emotionratio.put("negative", Float.toString((float) Math.round(((float) remotions[0] / sum) * 10000) / 100)
				+ "%");
		emotionratio.put("positive", Float.toString((float) Math.round(((float) remotions[1] / sum) * 10000) / 100)
				+ "%");
		emotionratio.put("neutral", Float.toString((float) Math.round(((float) remotions[2] / sum) * 10000) / 100)
				+ "%");
		result.setAllcount(allcount);
		result.setEmotionratio(JsonUtils.toJsonWithoutPretty(emotionratio));

		return result;
	}
}
