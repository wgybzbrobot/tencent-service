package cc.pp.tencent.bozhus.t2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cc.pp.service.tencent.dao.info.TencentWeiboInfoDao;
import cc.pp.service.tencent.exception.TencentApiException;
import cc.pp.service.tencent.model.UserTimelineData;
import cc.pp.service.tencent.model.UserTimelineInfo;

public class SWeiboAnalysis {

	private static Logger logger = LoggerFactory.getLogger(SWeiboAnalysis.class);

	private final TencentWeiboInfoDao tencentWeiboInfoDao;

	public SWeiboAnalysis(TencentWeiboInfoDao tencentWeiboInfoDao) {
		this.tencentWeiboInfoDao = tencentWeiboInfoDao;
	}

	/**
	 * 测试函数
	 */
	public static void main(String[] args) {
		//
	}

	/**
	 * 分析函数
	 */
	public int[] sWeiboAnalysis(String[] uids) {

		// 变量初始化
		int[] wbfenbubyhour = new int[24]; // 24小时内的微博发布分布
		try {
			for (int i = 0; i < uids.length; i++) {
				if (uids[i] == null || uids[i].length() < 5) {
					break;
				}
				UserTimelineData weibos = null;
				try {
					weibos = tencentWeiboInfoDao.getTencentUserOriRepWeibos(uids[i], 0, "0");
				} catch (TencentApiException e) {
					weibos = null;
				}
				if (weibos == null) {
					continue;
				}
				for (UserTimelineInfo weibo : weibos.getInfo()) {
					// 24小时内的微博发布分布
					wbfenbubyhour[Integer.parseInt(T2Utils.getHour(weibo.getTimestamp()))]++;
				}
			}
		} catch (RuntimeException e) {
			logger.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}

		return wbfenbubyhour;
	}

}
