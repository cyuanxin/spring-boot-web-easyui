package com.zyx.admin.mapper;

import com.github.pagehelper.Page;
import com.zyx.admin.domain.Role;

import java.util.Map;

public interface RoleMapper {

    Page<Role> findAll(Map<String, Object> map);

    Page<Role> findRoleAll(Map<String, Object> map);

    int add(Role obj);

    int update(Role obj);

    int del(Map<String, Object> map);
}
