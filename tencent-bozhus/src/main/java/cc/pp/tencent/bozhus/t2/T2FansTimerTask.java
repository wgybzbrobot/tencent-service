package cc.pp.tencent.bozhus.t2;

import java.util.Timer;
import java.util.TimerTask;

import cc.pp.service.token.TencentTokenServiceImpl;
import cc.pp.service.token.tencent.TencentTokenService;
import cc.pp.tencent.bozhus.common.TencentDaoUtils;

public class T2FansTimerTask {

	/**
	 * 主函数
	 */
	public static void main(String[] args) {

		Timer timer = new Timer();
		timer.schedule(new T2FAnalysisTimerTask(), 0, 86400 * 1000 * 7);
	}

	public static class T2FAnalysisTimerTask extends TimerTask {

		private static T2FansAnalysis fansAnalysis;

		public T2FAnalysisTimerTask() {
			TencentTokenService tokenService = new TencentTokenServiceImpl();
			fansAnalysis = new T2FansAnalysis(TencentDaoUtils.getTencentUserInfoDao(tokenService),
					TencentDaoUtils.getTencentWeiboInfoDao(tokenService));
		}

		@Override
		public void run() {
			try {
				fansAnalysis.insertUserFansResult("all");
			} catch (RuntimeException e) {
				throw new RuntimeException(e);
			}
		}
	}

}
