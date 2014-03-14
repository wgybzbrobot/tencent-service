package cc.pp.service.tencent.dao;

import java.lang.reflect.Proxy;

import cc.pp.service.tencent.dao.info.TencentWeiboInfoDao;
import cc.pp.service.tencent.dao.info.TencentWeiboInfoDaoImpl;
import cc.pp.service.tencent.exception.TencentApiException;
import cc.pp.service.tencent.model.UserTimeline;
import cc.pp.service.token.TencentTokenServiceImpl;
import cc.pp.service.token.tencent.TencentTokenService;
import cc.pp.tencent.utils.json.JsonUtils;

public class TencentWeiboInfoImplDemo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		TencentTokenService tokenService = new TencentTokenServiceImpl();
		TencentWeiboInfoDao tencentWeiboInfoDao = (TencentWeiboInfoDao) Proxy.newProxyInstance(
				TencentWeiboInfoDao.class.getClassLoader(), new Class[] { TencentWeiboInfoDao.class },
				new RetryHandler(new TencentWeiboInfoDaoImpl(tokenService), 50, 5) {
			@Override
			protected boolean isRetry(Throwable e) {
				if (e instanceof TencentApiException) {
					TencentApiException exception = (TencentApiException) e;
					if ((exception.getRet() == 3 && exception.getErrCode() == 8) // msg=check sign error，应该是token无效，重试
							|| (exception.getRet() == 4 && exception.getErrCode() == 20) // msg=tweet has  been deleted，应该是误判，重试
					) {
						return true;
					}
				}
				return false;
			}
		});

		//		String uid = "langdao2010";
		//		UserTimeline userTimeline = tencentWeiboInfoDao.getTencentUserWeibos(uid);
		//		System.out.println(JsonUtils.toJson(userTimeline));
		//		long lasttime = userTimeline.getData().getInfo().get(userTimeline.getData().getInfo().size() - 1)
		//				.getTimestamp();
		//		String lastwid = userTimeline.getData().getInfo().get(userTimeline.getData().getInfo().size() - 1).getId();
		//		UserTimeline userTimelines = tencentWeiboInfoDao.getTencentUserWeibos(uid, lasttime, lastwid);
		//		System.out.println(JsonUtils.toJson(userTimelines));

		String wid = "373718090218003";
		//		ShowWeibo weibo = tencentWeiboInfoDao.getSingleWeiboDetail(wid);
		//		System.out.println(JsonUtils.toJson(weibo));
		//		UserTimeline reposters = tencentWeiboInfoDao.getTencentSingleWeiboResposts(wid);
		//		System.out.println(JsonUtils.toJson(reposters));
		//		long lasttime = reposters.getData().getInfo().get(reposters.getData().getInfo().size() - 1).getTimestamp();
		//		String lastwid = reposters.getData().getInfo().get(reposters.getData().getInfo().size() - 1).getId();
		//		reposters = tencentWeiboInfoDao.getTencentSingleWeiboResposts(wid, lasttime, lastwid);
		//		System.out.println(JsonUtils.toJson(reposters));

		UserTimeline comments = tencentWeiboInfoDao.getTencentSingleWeiboComments(wid);
		//		System.out.println(JsonUtils.toJson(comments));
		long lasttime = comments.getData().getInfo().get(comments.getData().getInfo().size() - 1).getTimestamp();
		String lastwid = comments.getData().getInfo().get(comments.getData().getInfo().size() - 1).getId();
		comments = tencentWeiboInfoDao.getTencentSingleWeiboComments(wid, lasttime, lastwid);
		System.out.println(JsonUtils.toJson(comments));

	}

}
