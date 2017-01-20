package com.zyx.admin.web.system;

import com.google.common.collect.Maps;
import com.zyx.admin.common.persistence.Page;
import com.zyx.admin.common.persistence.PropertyFilter;
import com.zyx.admin.common.web.BaseController;
import com.zyx.admin.domain.User;
import com.zyx.admin.service.system.UserRoleService;
import com.zyx.admin.service.system.UserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * 用户controller
 * @author ty
 * @date 2015年1月13日
 */
@Controller
@RequestMapping("system/user")
public class UserController extends BaseController {

	@Autowired
	private UserService userService;

	@Autowired
	private UserRoleService userRoleService;


	/**
	 * 默认页面
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list() {
		return "system/userList";
	}

	/**
	 * 获取用户json
	 */
	@RequiresPermissions("sys:user:view")
	@RequestMapping(value="json",method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getData(HttpServletRequest request) {
		Page<User> page = getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		Map<String, Object> map = getMapByPropertyFilter(filters);
		com.github.pagehelper.Page<User> result = userService.findAllByPage(new PageRequest(page.getPageNo(), page.getPageSize()), map);
		return getEasyUIData(result);
	}


	/**
	 * 添加用户跳转
	 * 
	 * @param model
	 */
	@RequiresPermissions("sys:user:add")
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) {
		model.addAttribute("user", new User());
		model.addAttribute("action", "create");
		return "system/userForm";
	}

	/**
	 * 添加用户
	 * 
	 * @param user
	 * @param model
	 */
	@RequiresPermissions("sys:user:add")
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(@Valid User user, Model model, HttpSession session) {

        User creator = (User) session.getAttribute("user");
        user.setCreator(creator.getId());

		userService.save(user);
		return "success";
	}

	/**
	 * 修改用户跳转
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequiresPermissions("sys:user:update")
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Integer id, Model model) {
		Map<String, Object> map = Maps.newLinkedHashMap();
		map.put("id", id);
		model.addAttribute("user", userService.findAll(map).get(0));
		model.addAttribute("action", "update");
		return "system/userForm";
	}

	/**
	 * 修改用户
	 * 
	 * @param user
	 * @param model
	 * @return
	 */
	@RequiresPermissions("sys:user:update")
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute @RequestBody User user, Model model, HttpSession session) {
        User updator = (User) session.getAttribute("user");
        user.setUpdator(updator.getId());

		userService.update(user);
		return "success";
	}

	/**
	 * 删除用户
	 * 
	 * @param id
	 * @return
	 */
	@RequiresPermissions("sys:user:delete")
	@RequestMapping(value = "delete/{id}")
	@ResponseBody
	public String delete(@PathVariable("id") Integer id) {
		userService.delete(id);
		return "success";
	}

	/**
	 * 弹窗页-用户拥有的角色
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequiresPermissions("sys:user:roleView")
	@RequestMapping(value = "{userId}/userRole")
	public String getUserRole(@PathVariable("userId") Integer id, Model model) {
		model.addAttribute("userId", id);
		return "system/userRoleList";
	}

	/**
	 * 获取用户拥有的角色ID集合
	 * 
	 * @param id
	 * @return
	 */
	@RequiresPermissions("sys:user:roleView")
	@RequestMapping(value = "{id}/role")
	@ResponseBody
	public List<Integer> getRoleIdList(@PathVariable("id") Integer id) {
		return userRoleService.getRoleIdList(id);
	}

	/**
	 * 修改用户拥有的角色
	 * 
	 * @param id
	 * @param newRoleList
	 * @return
	 */
	@RequiresPermissions("sys:user:roleUpd")
	@RequestMapping(value = "{id}/updateRole")
	@ResponseBody
	public String updateUserRole(@PathVariable("id") Integer id,@RequestBody List<Integer> newRoleList) {
		userRoleService.updateUserRole(id, userRoleService.getRoleIdList(id),newRoleList);
		return "success";
	}

	/**
	 * 修改密码跳转
	 */
	@RequiresPermissions("sys:user:passwdUpd")
	@RequestMapping(value = "updatePwd/{id}", method = RequestMethod.GET)
	public String updatePwdForm(@PathVariable("id") Integer id, Model model, HttpSession session) {
		Map<String, Object> map = Maps.newLinkedHashMap();
		map.put("id", id);
		User user = userService.findAll(map).get(0);
		model.addAttribute("user", user);
		return "system/updatePwd";
	}

	/**
	 * 修改密码
	 */
	@RequiresPermissions("sys:user:passwdUpd")
	@RequestMapping(value = "updatePwd", method = RequestMethod.POST)
	@ResponseBody
	public String updatePwd(User user, HttpSession session) {
			userService.updatePwd(user);
			return "success";
	}

	/**
	 * Ajax请求校验loginName是否唯一。
	 */
	@RequestMapping(value = "checkLoginName")
	@ResponseBody
	public String checkLoginName(String loginName) {
		if (userService.getUser(loginName) == null) {
			return "true";
		} else {
			return "false";
		}
	}


	/**
	 * ajax请求校验原密码是否正确
	 * 
	 * @param oldPassword
	 * @param session
	 * @return
	 */
	@RequiresPermissions("sys:user:update")
	@RequestMapping(value = "checkPwd")
	@ResponseBody
	public String checkPwd(String oldPassword, HttpSession session) {
		if (userService.checkPassword((User) session.getAttribute("user"),oldPassword)) {
			return "true";
		} else {
			return "false";
		}
	}
	
	/**
	 * 所有RequestMapping方法调用前的Model准备方法, 实现Struts2
	 * Preparable二次部分绑定的效果,先根据form的id从数据库查出Task对象,再把Form提交的内容绑定到该对象上。
	 * 因为仅update()方法的form中有id属性，因此仅在update时实际执行.
	 */
	@ModelAttribute
	public void getUser(@RequestParam(value = "id", defaultValue = "-1") Integer id,Model model) {
		if (id != -1) {
			Map<String, Object> map = Maps.newLinkedHashMap();
			map.put("id", id);
			model.addAttribute("user", userService.findAll(map).get(0));
		}
	}

}
