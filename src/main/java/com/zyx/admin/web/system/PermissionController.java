package com.zyx.admin.web.system;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zyx.admin.common.web.BaseController;
import com.zyx.admin.domain.Perm;
import com.zyx.admin.service.system.PermissionService;
import com.zyx.admin.service.system.RolePermissionService;
import com.zyx.admin.system.utils.UserUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 权限controller
 *
 * @author ty
 * @date 2015年1月13日
 */
@Controller
@RequestMapping("system/permission")
public class PermissionController extends BaseController {

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private RolePermissionService rolePermissionService;

    /**
     * 默认页面
     */
    @RequestMapping(method = RequestMethod.GET)
    public String list() {
        return "system/permissionList";
    }

    /**
     * 菜单页面
     */
    @RequestMapping(value = "menu", method = RequestMethod.GET)
    public String menuList() {
        return "system/menuList";
    }

    /**
     * 菜单集合(JSON)
     */
    @RequiresPermissions("sys:perm:menu:view")
    @RequestMapping(value = "menu/json", method = RequestMethod.GET)
    @ResponseBody
    public List<Perm> menuDate() {
        List<Perm> permissionList = permissionService.getMenus();
        return permissionList;
    }

    /**
     * 权限集合(JSON)
     */
    //@RequiresPermissions("sys:perm:view")
    @RequestMapping(value = "json", method = RequestMethod.GET)
    @ResponseBody
    public List<Perm> getData() {
        List<Perm> permissionList = permissionService.findPerAll(Maps.newLinkedHashMap());
        return permissionList;
    }

    /**
     * 获取菜单下的操作
     */
    @RequiresPermissions("sys:perm:view")
    @RequestMapping("ope/json")
    @ResponseBody
    public Map<String, Object> menuOperationDate(Integer pid) {
        List<Perm> menuOperList = permissionService.getMenuOperation(pid);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("rows", menuOperList);
        map.put("total", menuOperList.size());
        return map;
    }

    /**
     * 当前登录用户的权限集合
     */
    @RequestMapping("i/json")
    @ResponseBody
    public List<Perm> myPermissionDate() {
        List<Perm> permissionList = permissionService.getPermissions(UserUtil.getCurrentUser().getId());
        return permissionList;
    }

    /**
     * 某用户的权限集合
     */
    @RequiresPermissions("sys:perm:view")
    @RequestMapping("{userId}/json")
    @ResponseBody
    public List<Perm> otherPermissionDate(@PathVariable("userId") Integer userId) {
        List<Perm> permissionList = permissionService.getPermissions(userId);
        return permissionList;
    }

    /**
     * 添加权限跳转
     */
    @RequestMapping(value = "create", method = RequestMethod.GET)
    public String createForm(Model model) {
        model.addAttribute("permission", new Perm());
        model.addAttribute("action", "create");
        return "system/permissionForm";
    }

    /**
     * 添加菜单跳转
     */
    @RequestMapping(value = "menu/create", method = RequestMethod.GET)
    public String menuCreateForm(Model model) {
        model.addAttribute("permission", new Perm());
        model.addAttribute("action", "create");
        return "system/menuForm";
    }

    /**
     * 添加权限/菜单
     */
    @RequiresPermissions("sys:perm:add")
    @RequestMapping(value = "create", method = RequestMethod.POST)
    @ResponseBody
    public String create(@Valid Perm permission, Model model) {
        permissionService.add(permission);
        return "success";
    }

    /**
     * 添加菜单基础操作
     *
     * @param pid
     * @return
     */
    @RequiresPermissions("sys:perm:add")
    @RequestMapping(value = "createBase/{pname}/{pid}", method = RequestMethod.GET)
    @ResponseBody
    public String create(@PathVariable("pname") String pname, @PathVariable("pid") Integer pid) {
        permissionService.addBaseOpe(pid, pname);
        return "success";
    }

    /**
     * 修改权限跳转
     */
    @RequestMapping(value = "update/{id}", method = RequestMethod.GET)
    public String updateForm(@PathVariable("id") Integer id, Model model) {
        Map<String, Object> map = Maps.newLinkedHashMap();
        map.put("id", id);
        model.addAttribute("permission", permissionService.findAll(map).get(0));
        model.addAttribute("action", "update");
        return "system/permissionForm";
    }

    /**
     * 修改菜单跳转
     */
    @RequestMapping(value = "menu/update/{id}", method = RequestMethod.GET)
    public String updateMenuForm(@PathVariable("id") Integer id, Model model) {
        Map<String, Object> map = Maps.newLinkedHashMap();
        map.put("id", id);
        model.addAttribute("permission", permissionService.findPerAll(map).get(0));
        model.addAttribute("action", "update");
        return "system/menuForm";
    }

    /**
     * 修改权限/菜单
     */
    @RequiresPermissions("sys:perm:update")
    @RequestMapping(value = "update", method = RequestMethod.POST)
    @ResponseBody
    public String update(@Valid Perm permission, Model model) {
        permissionService.update(permission);
        return "success";
    }

    /**
     * 删除权限
     */
    @RequiresPermissions("sys:perm:delete")
    @RequestMapping(value = "delete/{id}")
    @ResponseBody
    public String delete(@PathVariable("id") Integer id) {
        Map<String, Object> delMap = Maps.newLinkedHashMap();
        Map<String, Object> getIdMap = Maps.newLinkedHashMap();
        getIdMap.put("pid", id);
        List<Perm> pers = permissionService.findPerAll(getIdMap);

        List<Integer> list = Lists.newLinkedList();
        list.add(id);
        pers.stream().forEach(x -> list.add(x.getId()));
        delMap.put("permissionIds", list);
        delMap.put("ids", list);
        permissionService.delete(delMap);
        return "success";
    }

    @ModelAttribute
    public void getPermission(@RequestParam(value = "id", defaultValue = "-1") Integer id, Model model) {
        if (id != -1) {
            Map<String, Object> map = Maps.newLinkedHashMap();
            map.put("id", id);
            model.addAttribute("permission", permissionService.findAll(map));
        }
    }
}
