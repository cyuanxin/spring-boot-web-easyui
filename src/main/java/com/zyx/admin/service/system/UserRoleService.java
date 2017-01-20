package com.zyx.admin.service.system;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zyx.admin.domain.Role;
import com.zyx.admin.domain.User;
import com.zyx.admin.domain.UserRole;
import com.zyx.admin.mapper.UserRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 用户角色service
 *
 * @author ty
 * @date 2015年1月14日
 */
@Service
public class UserRoleService {

    @Autowired
    private UserRoleMapper userRoleMapper;


    /**
     * 添加修改用户角色
     *
     * @param userId
     * @param oldList
     * @param newList
     */
    @Transactional(value = "txManager")
    public void updateUserRole(Integer userId, List<Integer> oldList, List<Integer> newList) {
        // 是否删除
        List<Integer> userIds = Arrays.asList(userId);
        Map<String, Object> delMap = Maps.newLinkedHashMap();
        delMap.put("userIds", userIds);
        delMap.put("roleIds", oldList);
        userRoleMapper.del(delMap);


        List<UserRole> userRoles = Lists.newLinkedList();
        for (Integer x : newList) {
            UserRole userRole = new UserRole();
            userRole.setRoleId(x);
            userRole.setUserId(userId);
            userRoles.add(userRole);
        }
        if (!userRoles.isEmpty()) {
            userRoleMapper.add(userRoles);
        }
    }

    public List<UserRole> findAll(Map<String, Object> map) {
        return userRoleMapper.findAll(map);
    }

    /**
     * 构建UserRole
     *
     * @param userId
     * @param roleId
     * @return UserRole
     */
    private UserRole getUserRole(Integer userId, Integer roleId) {
        UserRole ur = new UserRole();
        ur.setUser(new User(userId));
        ur.setRole(new Role(roleId));
        return ur;
    }

    /**
     * 获取用户拥有角色id集合
     *
     * @param userId
     * @return 结果集合
     */
    public List<Integer> getRoleIdList(Integer userId) {
        Map<String, Object> map = Maps.newLinkedHashMap();
        map.put("userId", userId);
        List<UserRole> userRoles = userRoleMapper.findAll(map);
        List<Integer> res = Lists.newLinkedList();
        for (UserRole userRole : userRoles) {
            res.add(userRole.getRoleId());
        }
        return res;

    }

}
