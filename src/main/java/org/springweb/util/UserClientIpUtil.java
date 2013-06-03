package org.springweb.util;

import javax.servlet.http.HttpServletRequest;

/**
 * 获取客户端ip工具类
 *
 */
public class UserClientIpUtil {

    /**
     * 获取请求主机IP地址,如果通过代理进来，则透过防火墙获取真实IP地址
     * 
     * @param data 包含了http头信息
     * @return String
     */
    public static String getClientIp(HttpServletRequest request) {
        return getClientIpFromRequest(request);
    }

    /**
     * 获取请求主机IP地址,如果通过代理进来，则透过防火墙获取真实IP地址
     * 
     * @param
     * @return String
     */
    public static String getClientIpFromRequest(HttpServletRequest request) {
        String clientIp = null;
        String strClientIp = request.getHeader("x-forwarded-for");
        if (strClientIp == null || strClientIp.equalsIgnoreCase("unknown")) {
            clientIp = request.getRemoteAddr();
        } else {
            String[] strIps = strClientIp.split(",");
            String strIp = null;
            for (int i = 0; i < strIps.length; i++) {
                strIp = strIps[i];
                if (!strIp.equalsIgnoreCase("unknown") && !strIp.equalsIgnoreCase("127.0.0.1")) {
                    clientIp = strIp;
                    break;
                }
            }
        }
        return clientIp;
    }
}
