package org.springweb.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SecurityFilter implements Filter{

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest)request;
		if((Integer)httpRequest.getSession().getAttribute("login") != null && (Integer)httpRequest.getSession().getAttribute("login") == 1){
			chain.doFilter(request, response);
			return;
		}
		if(httpRequest.getRequestURI().contains("jiagoushi/rootadmin/mustlogin.htm") || httpRequest.getRequestURI().contains("jiagoushi/rootadmin/login.htm")){
			chain.doFilter(request, response);
			return;
		}
		HttpServletResponse httpResponse = (HttpServletResponse)response;
		httpResponse.sendRedirect("mustlogin.htm");
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

}
