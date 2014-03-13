package cc.pp.service.tencent.api;

import java.lang.reflect.Proxy;

import org.restlet.Application;
import org.restlet.Component;
import org.restlet.Restlet;
import org.restlet.data.Protocol;
import org.restlet.routing.Router;

import cc.pp.service.tencent.dao.RetryHandler;
import cc.pp.service.tencent.dao.StatusDao;
import cc.pp.service.tencent.dao.StatusDaoImpl;
import cc.pp.service.tencent.exception.TencentApiException;

/**
 * Created by chenwei on 14-2-19.
 */
public class TencentApiApplication extends Application {

	public static void main(String... args) throws Exception {
		System.setProperty("org.restlet.engine.loggerFacadeClass", "org.restlet.ext.slf4j.Slf4jLoggerFacade");

		// Create a component with an HTTP server connector
		Component comp = new Component();
		comp.getServers().add(Protocol.HTTP, Integer.valueOf(Config.get("tencent.api.port", "8080")));

		// Attach the application to the default host and start it
		comp.getDefaultHost().attach("/tencent", new TencentApiApplication());
		comp.start();
	}

	private StatusDao statusDao;

	public TencentApiApplication() {
		statusDao = (StatusDao) Proxy.newProxyInstance(StatusDaoImpl.class.getClassLoader(),
				new Class[] { StatusDao.class }, new RetryHandler(new StatusDaoImpl(), 100, 3) {
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
	}

	@Override
	public Restlet createInboundRoot() {
		Router router = new Router(getContext());
		router.attach("/users/{uid}/t/{id}", StatusResource.class);

		return router;
	}

	public StatusDao getStatusDao() {
		return statusDao;
	}
}
