package cc.pp.tencent.bozhus.common;

import java.lang.reflect.Proxy;

import cc.pp.service.tencent.dao.RetryHandler;
import cc.pp.service.tencent.dao.info.TencentUserInfoDao;
import cc.pp.service.tencent.dao.info.TencentUserInfoDaoImpl;
import cc.pp.service.tencent.dao.info.TencentWeiboInfoDao;
import cc.pp.service.tencent.dao.info.TencentWeiboInfoDaoImpl;
import cc.pp.service.tencent.exception.TencentApiException;
import cc.pp.service.token.tencent.TencentTokenService;

public class TencentDaoUtils {

	public static TencentUserInfoDao getTencentUserInfoDao(TencentTokenService tokenService) {

		TencentUserInfoDao tencentUserInfoDao = (TencentUserInfoDao) Proxy.newProxyInstance(TencentUserInfoDao.class
				.getClassLoader(), new Class[] { TencentUserInfoDao.class }, new RetryHandler(
				new TencentUserInfoDaoImpl(tokenService), 100, 5) {
			@Override
			protected boolean isRetry(Throwable e) {
				if (e instanceof TencentApiException) {
					TencentApiException exception = (TencentApiException) e;
					if ((exception.getRet() == 3 && exception.getErrCode() == 8) // msg=check sign error，应该是token无效，重试
							|| (exception.getRet() == 4 && exception.getErrCode() == 20) // msg=tweet has  been deleted，应该是误判，重试
							|| (exception.getRet() == 4 && exception.getErrCode() == 111) // server error
					) {
						return true;
					}
				}
				return false;
			}
		});

		return tencentUserInfoDao;
	}

	public static TencentWeiboInfoDao getTencentWeiboInfoDao(TencentTokenService tokenService) {

		TencentWeiboInfoDao tencentWeiboInfoDao = (TencentWeiboInfoDao) Proxy.newProxyInstance(
				TencentWeiboInfoDao.class.getClassLoader(), new Class[] { TencentWeiboInfoDao.class },
				new RetryHandler(new TencentWeiboInfoDaoImpl(tokenService), 100, 5) {
			@Override
			protected boolean isRetry(Throwable e) {
				if (e instanceof TencentApiException) {
					TencentApiException exception = (TencentApiException) e;
					if ((exception.getRet() == 3 && exception.getErrCode() == 8) // msg=check sign error，应该是token无效，重试
							|| (exception.getRet() == 4 && exception.getErrCode() == 20) // msg=tweet has  been deleted，应该是误判，重试
									|| (exception.getRet() == 4 && exception.getErrCode() == 111) // server error
					) {
						return true;
					}
				}
				return false;
			}
		});

		return tencentWeiboInfoDao;
	}

}
