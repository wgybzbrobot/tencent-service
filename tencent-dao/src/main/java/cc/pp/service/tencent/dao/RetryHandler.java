package cc.pp.service.tencent.dao;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class RetryHandler implements InvocationHandler {

	private static Logger logger = LoggerFactory.getLogger(RetryHandler.class);

	private final Object instance;

	private final int interval;

	private final int count;

	/**
	 * 自动重试的动态代理
	 * @param instance
	 * @param interval 重试间隔时间（毫秒）
	 * @param count 重试次数
	 */
	public RetryHandler(Object instance, int interval, int count) {
		this.instance = instance;
		this.interval = interval;
		this.count = count;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		return retry(method, args, 0);
	}

	protected abstract boolean isRetry(Throwable e);

	private Object retry(Method method, Object[] args, int retryCount) throws Throwable {
		logger.info("RetryCount=" + retryCount);
		try {
			return method.invoke(instance, args);
		} catch (InvocationTargetException e) {
			Throwable targetException = e.getTargetException();
			if (++retryCount > count) {
				throw targetException;
			}
			if (isRetry(targetException)) {
				Thread.sleep(interval);
				return retry(method, args, retryCount);
			}
			throw targetException;
		}
	}

}
