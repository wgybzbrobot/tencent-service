package cc.pp.tencent.bozhus.driver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cc.pp.tencent.bozhus.t2.T2FansAnalysis;
import cc.pp.tencent.bozhus.t2.T2FansTimerTask;
import cc.pp.tencent.bozhus.t2.T2InteractionsTimerTask;
import cc.pp.tencent.bozhus.t2.T2SingleWeiboAnalysis;
import cc.pp.tencent.bozhus.t2.T2SingleWeiboTimerTask;

/**
 * 驱动类
 * @author wgybzb
 *
 */
public class TencentBozhusDriver {

	private static Logger logger = LoggerFactory.getLogger(TencentBozhusDriver.class);

	public static void main(String[] args) {

		if (args.length == 0) {
			System.err.println("Usage: Driver <class-name>");
			System.exit(-1);
		}

		String[] restArgs = new String[args.length - 1];
		System.arraycopy(args, 1, restArgs, 0, restArgs.length);

		switch (args[0]) {
		case "t2FansTimerTask":
			logger.info("粉丝数据定时分析：");
			T2FansTimerTask.main(restArgs);
			break;
		case "t2FansAnalysis":
			logger.info("粉丝数据即时分析：");
			T2FansAnalysis.main(restArgs);
			break;
		case "t2InteractionsTimerTask":
			logger.info("用户与粉丝交互数据定时分析");
			T2InteractionsTimerTask.main(restArgs);
			break;
		case "t2SingleWeiboTimerTask":
			logger.info("单条微博定时分析：");
			T2SingleWeiboTimerTask.main(restArgs);
			break;
		case "t2SingleWeiboAnalysis":
			logger.info("单条微博临时分析：");
			T2SingleWeiboAnalysis.main(restArgs);
			break;
		default:
			return;
		}
	}

}
