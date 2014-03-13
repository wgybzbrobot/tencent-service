package cc.pp.tencent.utils.net;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.Enumeration;

public class Nettool {
	
	/**
	 * 获取服务器局域网ip
	 * @return
	 */
	public static String getServerLocalIp() {

		Enumeration<NetworkInterface> netInterfaces = null;
		try {
			String ip = "";
			netInterfaces = NetworkInterface.getNetworkInterfaces();
			while (netInterfaces.hasMoreElements()) {
				NetworkInterface ni = netInterfaces.nextElement();
				Enumeration<InetAddress> ips = ni.getInetAddresses();
				while (ips.hasMoreElements()) {
					ip = ips.nextElement().getHostAddress();
					if (ip.contains("192.168")) {
						return ip;
					}
				}
			}
			return null;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 获取我的本机IP
	 * @return
	 */
	public static String getMyip() {

		try {
			return InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			return null;
		}
	}

	/**
	 * 获取其它服务器IP
	 * @param url
	 * @return
	 */
	public static String getMyServer(String url) {

		try {
			return InetAddress.getByName(url).getHostAddress();
		} catch (UnknownHostException e) {
			return null;
		}
	}

}

