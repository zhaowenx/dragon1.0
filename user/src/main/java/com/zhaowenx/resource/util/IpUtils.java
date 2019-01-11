package com.zhaowenx.resource.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * Class Name : IpUtils<br>
 * 
 * Description : <br>
 * 
 * @author wumingchao
 * @see
 *
 */
public class IpUtils {

	private static final Logger logger = LoggerFactory.getLogger(IpUtils.class);

	/**
	 * 获取服务器ip
	 * 
	 * @param isNetIp
	 *            是否优先取外网ip
	 * @return
	 * @throws SocketException
	 */
	public static String getRealIp(boolean isNetIp) {
		String localip = null;// 本地IP，如果没有配置外网IP则返回它
		String netip = null;// 外网IP
		Enumeration<NetworkInterface> netInterfaces;
		try {
			netInterfaces = NetworkInterface.getNetworkInterfaces();

			InetAddress ip = null;
			boolean finded = false;// 是否找到外网IP
			while (netInterfaces.hasMoreElements() && !finded) {
				NetworkInterface ni = netInterfaces.nextElement();
				Enumeration<InetAddress> address = ni.getInetAddresses();
				while (address.hasMoreElements()) {
					ip = address.nextElement();
					if (!ip.isSiteLocalAddress() && !ip.isLoopbackAddress()
							&& ip.getHostAddress().indexOf(":") == -1) {// 外网IP
						netip = ip.getHostAddress();
						finded = true;
						break;
					} else if (ip.isSiteLocalAddress()
							&& !ip.isLoopbackAddress()
							&& ip.getHostAddress().indexOf(":") == -1) {// 内网IP
						localip = ip.getHostAddress();
					}
				}
			}
			if (netip != null && !"".equals(netip) && isNetIp) {
				return netip;
			} else {
				return localip;
			}
		} catch (SocketException e) {
			logger.error(e.getMessage(), e);
		}
		return "";
	}

	/**
	 * 获取服务器ip
	 * 
	 * @param isNetIp
	 *            是否优先取外网ip
	 * @return
	 * @throws SocketException
	 */
	public static Map<String, String> getMuiltIp() {
		Map<String, String> map = new HashMap<String, String>();
		Enumeration<NetworkInterface> netInterfaces;
		try {
			netInterfaces = NetworkInterface.getNetworkInterfaces();
			InetAddress ip = null;
			boolean finded = false;// 是否找到外网IP
			while (netInterfaces.hasMoreElements() && !finded) {
				NetworkInterface ni = netInterfaces.nextElement();
				Enumeration<InetAddress> address = ni.getInetAddresses();
				while (address.hasMoreElements()) {
					ip = address.nextElement();
//					System.out.println(ip.getHostAddress() + "   "
//							+ "\nisAnyLocalAddress:" + ip.isAnyLocalAddress()
//							+ "\nisLinkLocalAddress:" + ip.isLinkLocalAddress()
//							+ "\nisLoopbackAddress:" + ip.isLoopbackAddress()
//							+ "\nisMCGlobal:" + ip.isMCGlobal()
//							+ "\nisMCLinkLocal:" + ip.isMCLinkLocal()
//							+ "\nisMCNodeLocal:" + ip.isMCNodeLocal()
//							+ "\nisMCOrgLocal:" + ip.isMCOrgLocal()
//							+ "\nisMCSiteLocal:" + ip.isMCSiteLocal()
//							+ "\nisMulticastAddress:" + ip.isMulticastAddress()
//							+ "\nisSiteLocalAddress:" + ip.isSiteLocalAddress()
//							+ "\n\n"
//					);
					if (!ip.isSiteLocalAddress() && !ip.isLoopbackAddress()
							&& ip.getHostAddress().indexOf(":") == -1) {// 外网IP
						map.put(ip.getHostAddress(), ip.getHostAddress());
						finded = true;
						break;
					} else if (ip.isSiteLocalAddress()
							&& !ip.isLoopbackAddress()
							&& ip.getHostAddress().indexOf(":") == -1) {// 内网IP
						map.put(ip.getHostAddress(), ip.getHostAddress());
					}
				}
			}

		} catch (SocketException e) {
			logger.error(e.getMessage(), e);
		}
		return map;
	}
	
    /**
     * 
     * <pre>
     * 获取真实IP
     * </pre>
     *
     * @param request
     * @return
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ipAddress = null;
        // ipAddress = request.getRemoteAddr();
        ipAddress = request.getHeader("x-forwarded-for");
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
            if (ipAddress.equals("127.0.0.1")) {
                // 根据网卡取本机配置的IP
                InetAddress inet = null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                ipAddress = inet.getHostAddress();
            }

        }

        // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if (ipAddress != null && ipAddress.length() > 15) { // "***.***.***.***".length()
                                                            // = 15
            if (ipAddress.indexOf(",") > 0) {
                ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
            }
        }
        return ipAddress;
    }


	public static void main(String[] args) {
		System.out.println(IpUtils.getMuiltIp().toString());
	}
}