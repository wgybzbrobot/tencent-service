package cc.pp.service.tencent.dao;

import java.lang.reflect.Proxy;

import cc.pp.service.tencent.dao.info.TencentUserInfoDao;
import cc.pp.service.tencent.dao.info.TencentUserInfoDaoImpl;
import cc.pp.service.tencent.exception.TencentApiException;
import cc.pp.service.tencent.model.Infos;
import cc.pp.service.token.TencentTokenServiceImpl;
import cc.pp.service.token.tencent.TencentTokenService;
import cc.pp.tencent.utils.json.JsonUtils;

public class TencentUserInfoImplDemo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		TencentTokenService tokenService = new TencentTokenServiceImpl();
		TencentUserInfoDao tencentUserInfoDao = (TencentUserInfoDao) Proxy.newProxyInstance(TencentUserInfoDao.class
				.getClassLoader(), new Class[] { TencentUserInfoDao.class }, new RetryHandler(
				new TencentUserInfoDaoImpl(tokenService), 50, 5) {
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
		//		OtherInfo otherInfo = tencentUserInfoDao.getTencentUserBaseInfo(uid);
		//		System.out.println(JsonUtils.toJson(otherInfo));
		//		UserFansList userFansList = tencentUserInfoDao.getTencentUserFans(uid, 1);
		//		System.out.println(JsonUtils.toJson(userFansList));
		//		UserIdolList userIdolList = tencentUserInfoDao.getTencentUserFriends(uid, 1);
		//		System.out.println(JsonUtils.toJson(userIdolList));

		String uids = "langdao2010,wghwd_9059";
		Infos infos = tencentUserInfoDao.getTencentUserBaseInfos(uids);
		System.out.println(JsonUtils.toJson(infos));

	}

}
