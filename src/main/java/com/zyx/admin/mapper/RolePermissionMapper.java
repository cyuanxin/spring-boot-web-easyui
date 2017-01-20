package com.zyx.admin.mapper;

import com.zyx.admin.domain.RolePermission;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface RolePermissionMapper {

    List<RolePermission> findAll(Map<String, Object> map);

    void add(@Param("list") List<RolePermission> list);

    int update(RolePermission obj);

    int del(Map<String, Object> map);
}
