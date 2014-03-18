package cc.pp.tencent.bozhus.t2;

import java.util.Timer;
import java.util.TimerTask;

import cc.pp.service.token.TencentTokenServiceImpl;
import cc.pp.service.token.tencent.TencentTokenService;
import cc.pp.tencent.bozhus.common.TencentDaoUtils;

public class T2InteractionsTimerTask {

	/**
	 * 主函数
	 */
	public static void main(String[] args) {

		Timer timer = new Timer();
		timer.schedule(new T2IAnalysisTimerTask(), 0, 86400 * 1000);
	}

	public static class T2IAnalysisTimerTask extends TimerTask {

		private static T2InteractionsAnalysis interactionsAnalysis;
		private static T2FansAnalysis fansAnalysis;

		public T2IAnalysisTimerTask() {
			TencentTokenService tokenService = new TencentTokenServiceImpl();
			interactionsAnalysis = new T2InteractionsAnalysis(TencentDaoUtils.getTencentUserInfoDao(tokenService),
					TencentDaoUtils.getTencentWeiboInfoDao(tokenService));
			fansAnalysis = new T2FansAnalysis(TencentDaoUtils.getTencentUserInfoDao(tokenService),
					TencentDaoUtils.getTencentWeiboInfoDao(tokenService));
		}

		@Override
		public void run() {
			try {
				interactionsAnalysis.insertUserInteractionResult();
				fansAnalysis.insertUserFansResult("new");
			} catch (RuntimeException e) {
				throw new RuntimeException(e);
			}
		}
	}

}
