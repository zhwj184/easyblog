package org.springweb.filter;

import java.io.IOException;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * 
 <filter> <filter-name> ResponseHeaderFilter</filter-name> <filter-class>
 * com.jspbook.ResponseHeaderFilter</filter-class> <init-param> <param-name>
 * Cache-Control</param-name> <param-value> max-age=3600</param-value>
 * </init-param> </filter>
 * 
 * <filter-mapping> <filter-name> ResponseHeaderFilter</filter-name>
 * <url-pattern>/logo.png</url-pattern> </filter-mapping>
 * 
 * 1. //本页面允许在浏览器端或缓存服务器中缓存，时限为10秒。 2. java.util.Date date = new
 * java.util.Date(); 3. response.setDateHeader("Last-Modified",date.getTime());
 * 4. response.setDateHeader("Expires",date.getTime()+10000); 5. 6.
 * response.setHeader("Cache-Control", "public"); 7.
 * response.setHeader("Pragma", "Pragma");
 * 
 * http://www.onjava.com/pub/a/onjava/2004/03/03/filters.html
 * 
 * @author weijian.zhongwj
 * 
 */
public class ResponseHeaderFilter implements Filter {

	private static Pattern pattern = Pattern.compile("/jiagoushi/(\\d*).htm");

	FilterConfig fc;

	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;

		chain.doFilter(req, response);
		
		//detail页面缓存或者style
		if (pattern.matcher(httpRequest.getRequestURI()).find() || httpRequest.getRequestURI().endsWith("js") || httpRequest.getRequestURI().endsWith("css")) {
			// set the provided HTTP response parameters
			java.util.Date date = new java.util.Date();
			response.setDateHeader("Last-Modified", date.getTime());
			response.setDateHeader("Expires", date.getTime() + 3600000);
			response.setHeader("Cache-Control", "max-age=" + 36000);
			response.setHeader("Pragma", "Pragma");
			// pass the request/response on
		}


	}

	public void init(FilterConfig filterConfig) {
		this.fc = filterConfig;
	}

	public void destroy() {
		this.fc = null;
	}
}