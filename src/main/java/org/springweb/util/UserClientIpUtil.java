package org.springweb.util;

import javax.servlet.http.HttpServletRequest;

/**
 * ��ȡ�ͻ���ip������
 *
 */
public class UserClientIpUtil {

    /**
     * ��ȡ��������IP��ַ,���ͨ�������������͸������ǽ��ȡ��ʵIP��ַ
     * 
     * @param data ������httpͷ��Ϣ
     * @return String
     */
    public static String getClientIp(HttpServletRequest request) {
        return getClientIpFromRequest(request);
    }

    /**
     * ��ȡ��������IP��ַ,���ͨ�������������͸������ǽ��ȡ��ʵIP��ַ
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
