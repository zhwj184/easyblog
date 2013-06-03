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

import org.springweb.cache.CommentCountManager;
import org.springweb.util.UserClientIpUtil;


public class CommentCountLimitFilter implements Filter{
	
	private static final int DAY_MAX_COMMENT_COUNT = 5;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {	
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		if(httpRequest.getRequestURI().indexOf("/jiagoushi/addComment") >= 0){
			if(CommentCountManager.add(UserClientIpUtil.getClientIp(httpRequest)) > DAY_MAX_COMMENT_COUNT){
				httpResponse.sendRedirect("/jiagoushi/" + httpRequest.getParameter("postId") + ".htm");
				return ;
			}
		}
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {	
	}

}
