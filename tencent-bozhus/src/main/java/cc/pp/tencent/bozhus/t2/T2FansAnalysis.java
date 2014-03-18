package cc.pp.tencent.bozhus.t2;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cc.pp.service.tencent.dao.info.TencentUserInfoDao;
import cc.pp.service.tencent.dao.info.TencentWeiboInfoDao;
import cc.pp.service.tencent.exception.TencentApiException;
import cc.pp.service.tencent.model.FansInfo;
import cc.pp.service.tencent.model.OtherInfoData;
import cc.pp.service.tencent.model.UserFansListData;
import cc.pp.service.token.TencentTokenServiceImpl;
import cc.pp.service.token.tencent.TencentTokenService;
import cc.pp.sina.dao.common.MybatisConfig;
import cc.pp.sina.dao.t2.T2Interactions;
import cc.pp.sina.dao.t2.T2UserFans;
import cc.pp.sina.domain.t2.T2TencentInteractionsInfo;
import cc.pp.sina.utils.json.JsonUtils;
import cc.pp.tencent.algorithms.top.sort.InsertSort;
import cc.pp.tencent.bozhus.common.SourceType;
import cc.pp.tencent.bozhus.common.TencentDaoUtils;
import cc.pp.tencent.bozhus.domain.FansAnalysisResult;

public class T2FansAnalysis {

	private static Logger logger = LoggerFactory.getLogger(T2FansAnalysis.class);

	private final TencentUserInfoDao tencentUserInfoDao;
	private final SWeiboAnalysis sWeiboAnalysis;
	private final T2Interactions interactions;

	private static T2UserFans userFans = new T2UserFans(MybatisConfig.ServerEnum.fenxi);

	public T2FansAnalysis(TencentUserInfoDao tencentUserInfoDao, TencentWeiboInfoDao tencentWeiboInfoDao) {
		this.tencentUserInfoDao = tencentUserInfoDao;
		this.sWeiboAnalysis = new SWeiboAnalysis(tencentWeiboInfoDao);
		interactions = new T2Interactions(MybatisConfig.ServerEnum.fenxi);
	}

	private final int FANS_LIMIT = 2000;

	/**
	 * 测试函数
	 */
	public static void main(String[] args) {

		TencentTokenService tokenService = new TencentTokenServiceImpl();
		T2FansAnalysis fansAnalysis = new T2FansAnalysis(TencentDaoUtils.getTencentUserInfoDao(tokenService),
				TencentDaoUtils.getTencentWeiboInfoDao(tokenService));
		fansAnalysis.insertUserFansResult("new");
	}

	/**
	 * 存储粉丝结果数据
	 */
	public void insertUserFansResult(String type) {
		List<String> uids;
		if ("all".equalsIgnoreCase(type)) {
			uids = userFans.getTencentAllUids();
		} else if ("new".equalsIgnoreCase(type)) {
			uids = userFans.getTencentNewUids();
		} else {
			logger.error("The 'type' param is error.");
			return;
		}
		logger.info("Uids' size=" + uids.size());
		for (String uid : uids) {
			logger.info("Tackle uid=" + uid);
			FansAnalysisResult fansAnalysisResult = fansAnalysis(uid);
			if (fansAnalysisResult != null) {
				userFans.updateT2TencentUser(uid, JsonUtils.toJsonWithoutPretty(fansAnalysisResult));
			}
		}
	}

	/**
	 * 分析函数
	 */
	public FansAnalysisResult fansAnalysis(String uid) {

		FansAnalysisResult result = new FansAnalysisResult();
		/*****用户信息******/
		OtherInfoData userInfo = null;
		try {
			userInfo = tencentUserInfoDao.getTencentUserBaseInfo(uid);
		} catch (TencentApiException e) {
			userInfo = null;
		}
		if (userInfo == null) {
			logger.error("User '" + uid + "' does not existed.");
			return null;
		}

		// 1、总粉丝数
		result.setFanssum(userInfo.getFansnum());
		int maxcount = Math.min(userInfo.getFansnum(), FANS_LIMIT);
		/*****粉丝信息*****/
		int existuids = 0;
		int[] wbsource = new int[6]; // 终端设备分布，或者微博来源分布
		int[] province = new int[101]; // 9、区域分析
		int[] ishead = new int[2];
		int[] gender = new int[3]; // 8、性别分析
		int[] gradebyfans = new int[5]; // 6、粉丝等级分布（按粉丝量）
		int[] age = new int[5]; // 7、年龄分析
		int[] quality = new int[2]; // 10、水军分析
		int[] addvratio = new int[2]; // 12、认证比例
		int[] verifiedtype = new int[2]; // 认证分布，或者是否为VIP
		HashMap<String, Integer> hottags = new HashMap<String, Integer>(); // 5、粉丝的热门标签
		String[] top40fans = new String[40]; // top40粉丝，按照粉丝量
		FansUtils.initTopArray(top40fans);
		String[] top10uids = new String[10]; // 存放待分析的用户名，用户分析这些用户的发博时间线
		FansUtils.initTopArray(top10uids);
		// 13、粉丝活跃时间分析
		int city, cursor = 1; // isvip是否为名人用户,cursor页码
		String citys;
		UserFansListData fansLists;
		while (cursor * 30 < maxcount) {
			try {
				fansLists = tencentUserInfoDao.getTencentUserFans(uid, cursor++);
			} catch (TencentApiException e) {
				fansLists = null;
			}
			if (fansLists == null) {
				break;
			}
			for (FansInfo fan : fansLists.getInfo()) {
				// 粉丝存在数 /
				if (fan != null) {
					existuids++;
				} else {
					continue;
				}
				//exp：经验值，level：微博等级
				citys = fan.getProvince_code();
				if (citys.length() == 0) {
					city = 0;
				} else {
					city = Integer.parseInt(citys);
				}
				/******************3、微博来源******************/
				wbsource[SourceType.getCategory(fan.getTweet().get(0).getFrom())]++;
				/******************4、认证分布******************/
				verifiedtype[fan.getIsvip()]++; //0-不是，1-是
				/******************5、热门标签******************/
				FansUtils.tackleTags(hottags, fan.getTag());
				/************6、用户等级分析（按粉丝量）***********/
				gradebyfans[FansUtils.checkFansGrade(fan.getFansnum())]++;
				/******************7、年龄分析******************/
				age[FansUtils.checkAge(fan.getFansnum())]++;
				/******************8、性别分析******************/
				if (fan.getSex() < 3)
					gender[fan.getSex()]++; //1-男，2-女，0-未填写
				/******************9、区域分析******************/
				province[FansUtils.checkLocation(city)]++;
				/******************10、水军分析*****************/
				quality[FansUtils.checkQuality(fan.getFansnum())]++;
				/****************存放待分析的用户名**************/
				top10uids = InsertSort.toptable(top10uids, fan.getName() + "=" + fan.getFansnum());
				/***********11、Top40粉丝UID（按粉丝量）**********/
				top40fans = InsertSort.toptable(top40fans, fan.getName() + "=" + fan.getFansnum());
				/******************12、认证比例*****************/
				addvratio[FansUtils.checkVerified(fan.getIsrealname())]++;
				/******************2、头像分析******************/
				if (fan.getHead() == null) {
					ishead[0]++;
				} else {
					ishead[1]++;
				}
			}
		}
		for (int i = 0; i < 10; i++) {
			top10uids[i] = top40fans[i];
		}
		/***********数据整理***********/
		/************2、活跃粉丝数*****************/
		FansUtils.activeCountArrange(result, quality, userInfo.getFansnum(), existuids);
		/************3、微博来源******************/
		FansUtils.sourceArrange(result, wbsource, existuids);
		/************4、认证分布******************/
		FansUtils.verifiedTypeArrange(result, verifiedtype, existuids);
		/************5、热门标签******************/
		FansUtils.tagsArrange(result, hottags);
		/************6、用户等级分析（按粉丝量）***********/
		FansUtils.fansGradeArrange(result, gradebyfans, existuids);
		/************7、年龄分析****************/
		FansUtils.ageArrange(result, age, existuids);
		/************8、性别分析****************/
		FansUtils.genderArrange(result, gender);
		/************9、区域分析******************/
		FansUtils.locationArrange(result, province, existuids);
		/************10、水军分析******************/
		FansUtils.qualityArrange(result, quality, existuids);
		/************11、Top40粉丝UID（按粉丝量）************/
		FansUtils.topFansArrange(result, top40fans);
		/************12、认证比例******************/
		result.setAddVRatio(Float.toString((float) Math.round(((float) addvratio[1] / existuids) * 10000) / 100) + "%");
		/************13、粉丝活跃时间线分析**************/
		result.setActivetimeline(getSwbAnalysis(top10uids));
		/************14、粉丝增长趋势*****************/
		result.setFansaddtimeline(getFansCountTimeline(uid, 7));

		return result;
	}

	/**
	 * 获取最近N天的粉丝量数据
	 */
	private HashMap<String, Integer> getFansCountTimeline(String uid, int NDaysFromNow) {
		HashMap<String, Integer> result = new HashMap<>();
		List<T2TencentInteractionsInfo> fansLists = interactions.selectT2TencentInteractionsList(uid, NDaysFromNow);
		for (T2TencentInteractionsInfo fan : fansLists) {
			result.put(fan.getDate() + "", fan.getFanscount());
		}
		return result;
	}

	/**
	 * 获取粉丝的24小时的活跃时间线
	 */
	public HashMap<String, String> getSwbAnalysis(String[] top10uids) {

		HashMap<String, String> result = new HashMap<>();
		String[] uids = InsertSort.trans(top10uids);
		int[] wbfenbubyhour = sWeiboAnalysis.sWeiboAnalysis(uids); // 24小时内的微博发布分布
		int sum = 0;
		for (int i = 0; i < wbfenbubyhour.length; i++) {
			sum += wbfenbubyhour[i];
		}
		for (int i = 0; i < wbfenbubyhour.length; i++) {
			result.put(Integer.toString(i),
					Float.toString((float) Math.round(((float) wbfenbubyhour[i] / sum) * 10000) / 100) + "%");
		}

		return result;
	}

}
