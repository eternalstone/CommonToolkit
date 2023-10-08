package io.github.eternalstone.common.toolkit.web.util;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author c5game
 */
public class IpUtil {

	private static final String UNKNOWN = "unknown";
	private static final String COMMA = ",";
	private static final String IP_EMPTY = "";
	private static final Integer THRESHOLD = 15;
	private static final String LOCAL_IP_DEFAULT = "127.0.0.1";

	public static String getIpAddr(HttpServletRequest request) {
		String ipAddress;
		try {
			ipAddress = request.getHeader("X-Original-Forwarded-For");
			if (!existIpAddress(ipAddress)) {
				ipAddress = request.getHeader("Proxy-Client-IP");
			}
			if (!existIpAddress(ipAddress)) {
				ipAddress = request.getHeader("WL-Proxy-Client-IP");
			}
			if (!existIpAddress(ipAddress)) {
				ipAddress = request.getHeader("X-Client-IP");
			}
			if (!existIpAddress(ipAddress)) {
				ipAddress = request.getHeader("X-True-IP");
			}
			if (!existIpAddress(ipAddress)) {
				ipAddress = request.getHeader("X-Forwarded-For");
			}
			if (!existIpAddress(ipAddress)) {
				ipAddress = request.getRemoteAddr();
				if (LOCAL_IP_DEFAULT.equals(ipAddress)) {
					// 根据网卡取本机配置的IP
					InetAddress inet = null;
					try {
						inet = InetAddress.getLocalHost();
					} catch (UnknownHostException e) {
						e.printStackTrace();
					}
					ipAddress = inet.getHostAddress();
				}
			}
			// 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
			if (ipAddress != null && ipAddress.length() > THRESHOLD) {
				if (ipAddress.indexOf(COMMA) > 0) {
					ipAddress = ipAddress.substring(0, ipAddress.indexOf(COMMA));
				}
			}
		} catch (Exception e) {
			ipAddress = IP_EMPTY;
		}
		return ipAddress;
	}

	private static boolean existIpAddress(String ipAddress) {
		if (ipAddress == null || ipAddress.length() == 0 || UNKNOWN.equalsIgnoreCase(ipAddress)) {
			return false;
		}
		return true;
	}
}
