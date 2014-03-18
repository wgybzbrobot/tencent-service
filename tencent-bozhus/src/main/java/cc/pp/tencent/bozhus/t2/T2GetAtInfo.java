package cc.pp.tencent.bozhus.t2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cc.pp.service.tencent.dao.info.TencentWeiboInfoDao;
import cc.pp.service.tencent.exception.TencentApiException;
import cc.pp.service.tencent.model.UserTimelineData;
import cc.pp.service.tencent.model.UserTimelineInfo;

public class T2GetAtInfo {

	private static Logger logger = LoggerFactory.getLogger(T2GetAtInfo.class);

	private final TencentWeiboInfoDao tencentWeiboInfoDao;

	public T2GetAtInfo(TencentWeiboInfoDao tencentWeiboInfoDao) {
		this.tencentWeiboInfoDao = tencentWeiboInfoDao;
	}

	/**
	 * 测试函数
	 */
	public static void main(String[] args) throws Exception {
		//
	}

	/**
	 * AT(@)交互信息分析
	 */
	public int[] atInfoAnalysis(String uid) {

		/* 初始化结果值
		 * result[0]----负面情感数
		 * result[1]----正面情感数
		 * result[2]----中性情感数
		 * result[3]----@数量
		 */
		int[] result = new int[4];
		UserTimelineData mentions = null;
		try {
			mentions = tencentWeiboInfoDao.getTencentUserMentions(uid);
		} catch (TencentApiException e) {
			mentions = null;
		}
		if (mentions == null) {
			logger.info("User '" + uid + "' has no mention.");
			return result;
		}
		long lasttime; // 第一页最后一条记录的时间和id
		String lastwid; // 第一页最后一条记录的
		boolean flag = true;
		long t = System.currentTimeMillis()/1000;

		while (flag && (mentions != null)) {
			for (UserTimelineInfo mention : mentions.getInfo()) {
				if (mention.getTimestamp() < t - 86400) {
					flag = false;
					break;
				} else {
					result[3]++;
					result[T2Utils.getEmotions(mention.getText())]++;
				}
			}
			lasttime = mentions.getInfo().get(mentions.getInfo().size() - 1).getTimestamp();
			lastwid = mentions.getInfo().get(mentions.getInfo().size() - 1).getId();
			try {
				mentions = tencentWeiboInfoDao.getTencentUserMentions(uid, lasttime, lastwid);
			} catch (TencentApiException e) {
				mentions = null;
			}
		}

		return result;
	}


}
