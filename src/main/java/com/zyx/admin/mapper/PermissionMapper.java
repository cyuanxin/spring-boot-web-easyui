package com.zyx.admin.mapper;
import com.zyx.admin.domain.Perm;

import java.util.List;
import java.util.Map;

public interface PermissionMapper {

    List<Perm> findAll(Map<String, Object> map);

    List<Perm> findDistinctAll(Map<String, Object> map);

    List<Perm> findPerAll(Map<String, Object> map);

    int add(Perm obj);

    int update(Perm obj);

    int del(Map<String, Object> map);
}
