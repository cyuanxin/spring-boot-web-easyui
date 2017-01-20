package com.zyx.admin.service.system;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zyx.admin.domain.RolePermission;
import com.zyx.admin.mapper.RolePermissionMapper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.RealmSecurityManager;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 角色权限service
 * @author ty
 * @date 2015年1月13日
 */
@Service
public class RolePermissionService{

	@Autowired
	private RolePermissionMapper rolePermissionDao;
	

	
	/**
	 * 获取角色权限id集合
	 * @param roleId
	 * @return List
	 */
	public List<Integer> getPermissionIds(Integer roleId){
		Map<String, Object> map = Maps.newLinkedHashMap();
		map.put("roleId", roleId);
		List<RolePermission> rolePermissions = rolePermissionDao.findAll(map);
		List<Integer> list = rolePermissions.stream().map(RolePermission::getPermissionId).collect(Collectors.toList());
		return list;
	}
	
	/**
	 * 修改角色权限
	 * @param roleId
	 * @param oldList
	 * @param newList
	 */
	@Transactional
	public void updateRolePermission(Integer roleId,List<Integer> oldList,List<Integer> newList){
		//是否删除
		List<Integer> roleIds = Arrays.asList(roleId);
		Map<String, Object> delMap = Maps.newLinkedHashMap();
		delMap.put("roleIds", roleIds);
		delMap.put("permissionIds", oldList);
		rolePermissionDao.del(delMap);

		List<RolePermission> pers = Lists.newLinkedList();
		for (Integer x : newList) {
			RolePermission rolePer = new RolePermission();
			rolePer.setRoleId(roleId);
			rolePer.setPermissionId(x);
			pers.add(rolePer);
		}
		
		//是否添加
		rolePermissionDao.add(pers);
	}
	
	/**
	 * 清空该角色用户的权限缓存
	 */
	public void clearUserPermCache(PrincipalCollection pc){
		RealmSecurityManager securityManager =  (RealmSecurityManager) SecurityUtils.getSecurityManager();
		UserRealm userRealm = (UserRealm) securityManager.getRealms().iterator().next();
	    userRealm.clearCachedAuthorizationInfo(pc);
	}
	

	
}
