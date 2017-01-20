package com.zyx.admin.service.system;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zyx.admin.domain.Role;
import com.zyx.admin.mapper.RoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 角色service
 * @author ty
 * @date 2015年1月13日
 */
@Service
public class RoleService {

	@Autowired
	private RoleMapper roleMapper;

	public Page<Role> findAllByPage(PageRequest pageRequest, Map<String, Object> map) {
		PageHelper.startPage(pageRequest.getPageNumber(), pageRequest.getPageSize());
		return roleMapper.findRoleAll(map);
	}



	public List<Role> findAll(Map<String, Object> map) {
		return roleMapper.findAll(map);
	}

	public int add(Role role) {
		return roleMapper.add(role);
	}

	public int update(Role role) {
		return roleMapper.update(role);
	}

	public int del(Map<String, Object> map) {
		return roleMapper.del(map);
	}

}
