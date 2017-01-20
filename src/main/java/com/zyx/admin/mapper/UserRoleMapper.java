package com.zyx.admin.mapper;

import com.github.pagehelper.Page;
import com.zyx.admin.domain.UserRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface UserRoleMapper {

    Page<UserRole> findAll(Map<String, Object> map);

    void add(@Param("list") List<UserRole> objs);

    int update(UserRole obj);

    int del(Map<String, Object> map);
}
