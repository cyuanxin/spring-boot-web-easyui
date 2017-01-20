package com.zyx.admin.system.utils;

import com.zyx.admin.domain.User;
import com.zyx.admin.service.system.UserRealm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;


public class UserUtil {
	/**
	 * 获取当前用户对象shiro
	 * @return shirouser
	 */
	public static UserRealm.ShiroUser getCurrentShiroUser(){
		UserRealm.ShiroUser user=(UserRealm.ShiroUser) SecurityUtils.getSubject().getPrincipal();
		return user;
	}
	
	/**
	 * 获取当前用户session
	 * @return session
	 */
	public static Session getSession(){
		Session session =SecurityUtils.getSubject().getSession();
		return session;
	}
	
	/**
	 * 获取当前用户httpsession
	 * @return httpsession
	 */
	public static Session getHttpSession(){
		Session session =SecurityUtils.getSubject().getSession();
		return session;
	}
	
	/**
	 * 获取当前用户对象
	 * @return user
	 */
	public static User getCurrentUser(){
		Session session =SecurityUtils.getSubject().getSession();
		if(null!=session){
			return (User) session.getAttribute("user");
		}else{
			return null;
		}
		
	}
}
 