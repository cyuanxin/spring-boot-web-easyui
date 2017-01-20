package com.zyx.admin.service.system;

import com.google.common.collect.Maps;
import com.zyx.admin.domain.Perm;
import com.zyx.admin.mapper.PermissionMapper;
import com.zyx.admin.mapper.RolePermissionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * 权限service
 *
 * @author ty
 * @date 2015年1月13日
 */
@Service
public class PermissionService {

    @Autowired
    private PermissionMapper permissionMapper;
    @Autowired
    private RolePermissionMapper rolePermissionMapper;


    /**
     * 添加菜单基础操作
     *
     * @param pid   菜单id
     * @param pClassName 菜单权限标识名
     */
    public void addBaseOpe(Integer pid, String pClassName) {
        List<Perm> pList = new ArrayList<Perm>();
        pList.add(new Perm(pid, "添加", "O", "", "sys:" + pClassName + ":add"));
        pList.add(new Perm(pid, "删除", "O", "", "sys:" + pClassName + ":delete"));
        pList.add(new Perm(pid, "修改", "O", "", "sys:" + pClassName + ":update"));
        pList.add(new Perm(pid, "查看", "O", "", "sys:" + pClassName + ":view"));

        //添加没有的基本操作
        List<Perm> existPList = getMenuOperation(pid);
        for (Perm permission : pList) {
            boolean exist = false;
            for (Perm existPermission : existPList) {
                if (permission.getPermCode().equals(existPermission.getPermCode())) {
                    exist = true;
                    break;
                } else {
                    exist = false;
                }
            }
            if (!exist) {
                permissionMapper.add(permission);
            }
        }
    }

    public List<Perm> findAll(Map<String, Object> map) {
        return permissionMapper.findAll(map);
    }

    public List<Perm> findPerAll(Map<String, Object> map) {
        return permissionMapper.findPerAll(map);
    }

    public int add(Perm permission) {
        return permissionMapper.add(permission);
    }
    /**
     * 获取角色拥有的权限集合
     *
     * @param userId
     * @return 结果集合
     */
    public List<Perm> getPermissions(Integer userId) {
        Map<String, Object> map = Maps.newLinkedHashMap();
        map.put("userId", userId);
        return permissionMapper.findDistinctAll(map);
    }

    public int update(Perm permission) {
        return permissionMapper.update(permission);
    }

    public int delete(Map<String, Object> map, Map<String, Object> roperMap) {
        rolePermissionMapper.del(roperMap);
        return permissionMapper.del(map);
    }

    /**
     * 获取角色拥有的菜单
     *
     * @param userId
     * @return 菜单集合
     */
    public List<Perm> getMenus(Integer userId) {
        Map<String, Object> map = Maps.newLinkedHashMap();
        map.put("userId", userId);
        map.put("type", "F");
        return permissionMapper.findAll(map);
    }

    /**
     * 获取所有菜单
     *
     * @return 菜单集合
     */
    public List<Perm> getMenus() {
        Map<String, Object> map = Maps.newLinkedHashMap();
        map.put("type", "F");
        return permissionMapper.findPerAll(map);
    }

    /**
     * 获取菜单下的操作
     *
     * @param pid
     * @return 操作集合
     */
    public List<Perm> getMenuOperation(Integer pid) {
        Map<String, Object> map = Maps.newLinkedHashMap();
        if (pid == null) {
            map.put("pid", "");
        } else {
            map.put("pid", pid);
        }
        map.put("type", "O");
        return permissionMapper.findPerAll(map);
    }

}
