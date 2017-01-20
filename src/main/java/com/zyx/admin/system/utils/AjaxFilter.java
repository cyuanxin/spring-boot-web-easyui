package com.zyx.admin.system.utils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AjaxFilter implements Filter{

	public void init(FilterConfig filterConfig) throws ServletException {
		
	}

	public void doFilter(ServletRequest servletRequestt, ServletResponse servletResponse,
			FilterChain chain) throws IOException, ServletException {
		 HttpServletRequest request=(HttpServletRequest) servletRequestt;
		 HttpServletResponse response=(HttpServletResponse) servletResponse;
		 
		 //String currentURL = request.getRequestURI();//取得根目录所对应的绝对路径:   
		 //String targetURL = currentURL.substring(currentURL.indexOf("/", 1), currentURL.length());  //截取到当前文件名用于比较
		 
		 String ajaxSubmit = request.getHeader("X-Requested-With");
		 if(ajaxSubmit != null && ajaxSubmit.equals("XMLHttpRequest")){
			 if (request.getSession(false) == null) {
				 response.setHeader("sessionstatus", "timeout");
				 response.getWriter().print("sessionstatus");
				 return;
			 }
		 }
		 chain.doFilter(servletRequestt, servletResponse);
	}

	public void destroy() {
		
	}
}
