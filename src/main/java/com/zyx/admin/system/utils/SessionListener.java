package com.zyx.admin.system.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.HashSet;


/**
 * session监听器
 * @author ty
 * @date 2015年1月15日
 */
@WebListener
public class SessionListener implements HttpSessionListener {
	
	private Logger log=LoggerFactory.getLogger(SessionListener.class);

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void sessionCreated(HttpSessionEvent event) {
		HttpSession session = event.getSession();
		ServletContext application = session.getServletContext();

		// 在application范围由一个HashSet集保存所有的session
		HashSet sessions = (HashSet) application.getAttribute("sessions");
		if (sessions == null) {
			sessions = new HashSet();
			application.setAttribute("sessions", sessions);
		}

		// 新创建的session均添加到HashSet集中
		sessions.add(session);
		log.info("session："+session.getId()+" add");
		// 可以在别处从application范围中取出sessions集合
		// 然后使用sessions.size()获取当前活动的session数，即为“在线人数”
	}

	@SuppressWarnings("rawtypes")
	public void sessionDestroyed(HttpSessionEvent event) {
		HttpSession session = event.getSession();
		ServletContext application = session.getServletContext();
		HashSet sessions = (HashSet) application.getAttribute("sessions");

		// 销毁的session均从HashSet集中移除
		sessions.remove(session);
		log.info("session："+session.getId()+" remove");
	}
  
}
