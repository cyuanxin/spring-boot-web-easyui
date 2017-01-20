package com.zyx.admin.web.system;

import com.google.common.collect.Maps;
import com.zyx.admin.common.persistence.Page;
import com.zyx.admin.common.persistence.PropertyFilter;
import com.zyx.admin.common.web.BaseController;
import com.zyx.admin.domain.Role;
import com.zyx.admin.domain.User;
import com.zyx.admin.service.system.RolePermissionService;
import com.zyx.admin.service.system.RoleService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.*;

/**
 * 角色controller
 *
 * @author ty
 * @date 2015年1月13日
 */
@Controller
@RequestMapping("system/role")
public class RoleController extends BaseController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private RolePermissionService rolePermissionService;

    /**
     * 默认页面
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public String list() {
        return "system/roleList";
    }

    /**
     * 角色集合(JSON)
     */
    @RequiresPermissions("sys:role:view")
    @RequestMapping(value = "json", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getData(HttpServletRequest request) {
        Page<Role> page = getPage(request);
        List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
        Map<String, Object> map = getMapByPropertyFilter(filters);
        com.github.pagehelper.Page<Role> result = roleService.findAllByPage(new PageRequest(page.getPageNo(), page.getPageSize()), map);
        return getEasyUIData(result);
    }

    /**
     * 获取角色拥有的权限ID集合
     *
     * @param id
     * @return
     */
    @RequiresPermissions("sys:role:permView")
    @RequestMapping("{id}/json")
    @ResponseBody
    public List<Integer> getRolePermissions(@PathVariable("id") Integer id) {
        List<Integer> permissionIdList = rolePermissionService.getPermissionIds(id);
        return permissionIdList;
    }

    /**
     * 修改角色权限
     *
     * @param id
     * @param
     * @return
     */
    @RequiresPermissions("sys:role:permUpd")
    @RequestMapping(value = "{id}/updatePermission")
    @ResponseBody
    public String updateRolePermission(@PathVariable("id") Integer id, @RequestBody List<Integer> newRoleIdList, HttpSession session) {
        List<Integer> oldRoleIdList = rolePermissionService.getPermissionIds(id);

        //获取application中的sessions
        @SuppressWarnings("rawtypes")
        HashSet sessions = (HashSet) session.getServletContext().getAttribute("sessions");
        Enumeration<String>  test = session.getServletContext().getAttributeNames();

        @SuppressWarnings("unchecked")
        Iterator<Session> iterator = sessions.iterator();
        PrincipalCollection pc = null;
        //遍历sessions
        while (iterator.hasNext()) {
            HttpSession s = (HttpSession) iterator.next();
            User user = getLoginUser();
            if (user.getId() == id) {
                pc = (PrincipalCollection) s.getAttribute(String.valueOf(id));
                //清空该用户权限缓存
                rolePermissionService.clearUserPermCache(pc);
                s.removeAttribute(String.valueOf(id));
                break;
            }
        }

        rolePermissionService.updateRolePermission(id, oldRoleIdList, newRoleIdList);

        return "success";
    }

    /**
     * 添加角色跳转
     *
     * @param model
     * @return
     */
    @RequiresPermissions("sys:role:add")
    @RequestMapping(value = "create", method = RequestMethod.GET)
    public String createForm(Model model) {
        model.addAttribute("role", new Role());
        model.addAttribute("action", "create");
        return "system/roleForm";
    }

    /**
     * 添加角色
     *
     * @param role
     * @param model
     * @return
     */
    @RequiresPermissions("sys:role:add")
    @RequestMapping(value = "create", method = RequestMethod.POST)
    @ResponseBody
    public String create(@Valid Role role, Model model) {
        roleService.add(role);
        return "success";
    }

    /**
     * 修改角色跳转
     *
     * @param id
     * @param model
     * @return
     */
    @RequiresPermissions("sys:role:update")
    @RequestMapping(value = "update/{id}", method = RequestMethod.GET)
    public String updateForm(@PathVariable("id") Integer id, Model model) {
        Map<String, Object> map = Maps.newLinkedHashMap();
        map.put("id", id);
        model.addAttribute("role", roleService.findAll(map).get(0));
        model.addAttribute("action", "update");
        return "system/roleForm";
    }

    /**
     * 修改角色
     *
     * @param role
     * @param model
     * @return
     */
    @RequiresPermissions("sys:role:update")
    @RequestMapping(value = "update", method = RequestMethod.POST)
    @ResponseBody
    public String update(@Valid @ModelAttribute("role") Role role, Model model) {
        roleService.update(role);
        return "success";
    }

    /**
     * 删除角色
     *
     * @param id
     * @return
     */
    @RequiresPermissions("sys:role:delete")
    @RequestMapping(value = "delete/{id}")
    @ResponseBody
    public String delete(@PathVariable("id") Integer id) {
        Map<String, Object> map = Maps.newLinkedHashMap();
        map.put("id", id);
        roleService.del(map);
        return "success";
    }

    @ModelAttribute
    public void getRole(@RequestParam(value = "id", defaultValue = "-1") Integer id, Model model) {
        if (id != -1) {
            Map<String, Object> map = Maps.newLinkedHashMap();
            map.put("id", id);
            model.addAttribute("role", roleService.findAll(map).get(0));
        }
    }
}
