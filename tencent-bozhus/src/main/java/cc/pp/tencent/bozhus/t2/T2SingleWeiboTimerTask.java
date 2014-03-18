package cc.pp.tencent.bozhus.t2;

import java.util.Timer;
import java.util.TimerTask;

import cc.pp.service.token.TencentTokenServiceImpl;
import cc.pp.service.token.tencent.TencentTokenService;
import cc.pp.tencent.bozhus.common.TencentDaoUtils;

public class T2SingleWeiboTimerTask {

	/**
	 * 主函数
	 */
	public static void main(String[] args) {

		Timer timer = new Timer();
		timer.schedule(new T2SWTimeTask(), 0, 3 * 3600 * 1000);

	}

	public static class T2SWTimeTask extends TimerTask {

		private static T2SingleWeiboAnalysis singleWeiboAnalysis;

		public T2SWTimeTask() {
			TencentTokenService tokenService = new TencentTokenServiceImpl();
			singleWeiboAnalysis = new T2SingleWeiboAnalysis(TencentDaoUtils.getTencentUserInfoDao(tokenService),
					TencentDaoUtils.getTencentWeiboInfoDao(tokenService));
		}

		@Override
		public void run() {
			singleWeiboAnalysis.insertSingleWeiboResult();
		}
	}

}
