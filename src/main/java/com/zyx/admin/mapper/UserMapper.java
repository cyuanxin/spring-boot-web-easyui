package com.zyx.admin.mapper;

import com.github.pagehelper.Page;
import com.zyx.admin.domain.User;

import java.util.Map;

public interface UserMapper {

    Page<User> findAll(Map<String, Object> map);

    void add(User obj);

    int update(User obj);

    int del(Map<String, Object> map);
}
