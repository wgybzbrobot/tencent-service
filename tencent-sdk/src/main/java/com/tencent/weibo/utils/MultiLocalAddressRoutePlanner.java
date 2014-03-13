package com.tencent.weibo.utils;

import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.conn.params.ConnRouteParams;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.routing.HttpRoutePlanner;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.protocol.HttpContext;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 具有多个本地ip的RoutePlanner，会轮寻使用本地ip与远端建立链接，适合避开新浪或腾讯针在单位时间对ip调用的次数限制
 * Created by chenwei on 14-2-20.
 */
public class MultiLocalAddressRoutePlanner implements HttpRoutePlanner {

	/**
	 * The scheme registry.
	 */
	protected final SchemeRegistry schemeRegistry; // class is @ThreadSafe

	protected NetworkInterface networkInterface;
	private static List<InetAddress> inetAddresses;
	private static AtomicInteger inetAddressIndex = new AtomicInteger();

	/**
	 * Creates a new default route planner.
	 *
	 * @param schreg the scheme registry
	 */
	public MultiLocalAddressRoutePlanner(SchemeRegistry schreg, NetworkInterface networkInterface) {
		if (schreg == null) {
			throw new IllegalArgumentException
					("SchemeRegistry must not be null.");
		}
		if (networkInterface == null) {
			throw new IllegalArgumentException
					("NetworkInterface must not be null.");
		}
		schemeRegistry = schreg;
		inetAddresses = getInetAddresses(networkInterface);
	}

	public HttpRoute determineRoute(HttpHost target,
									HttpRequest request,
									HttpContext context)
			throws HttpException {

		if (request == null) {
			throw new IllegalStateException
					("Request must not be null.");
		}

		// If we have a forced route, we can do without a target.
		HttpRoute route =
				ConnRouteParams.getForcedRoute(request.getParams());
		if (route != null)
			return route;

		// If we get here, there is no forced route.
		// So we need a target to compute a route.

		if (target == null) {
			throw new IllegalStateException
					("Target host must not be null.");
		}

		final InetAddress local = determineLocalAddress();
		final HttpHost proxy =
				ConnRouteParams.getDefaultProxy(request.getParams());

		final Scheme schm;
		try {
			schm = schemeRegistry.getScheme(target.getSchemeName());
		} catch (IllegalStateException ex) {
			throw new HttpException(ex.getMessage());
		}
		// as it is typically used for TLS/SSL, we assume that
		// a layered scheme implies a secure connection
		final boolean secure = schm.isLayered();

		if (proxy == null) {
			route = new HttpRoute(target, local, secure);
		} else {
			route = new HttpRoute(target, local, proxy, secure);
		}
		return route;
	}

	private InetAddress determineLocalAddress() {
		return inetAddresses.get(inetAddressIndex.getAndAdd(1) % inetAddresses.size());
	}

	static List<InetAddress> getInetAddresses(NetworkInterface networkInterface) {
		Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();
		List<InetAddress> result = new ArrayList<InetAddress>();
		while (inetAddresses.hasMoreElements()) {
			InetAddress inetAddress = inetAddresses.nextElement();
			if (inetAddress instanceof Inet4Address) {
				result.add(inetAddress);
			}
		}
		return result;
	}

}
