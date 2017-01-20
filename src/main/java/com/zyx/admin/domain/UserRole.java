package com.zyx.admin.domain;

/**
 * 用户角色entity
 * @author ty
 * @date 2015年1月13日
 */
//@Entity
//@Table(name = "user_role")
public class UserRole implements java.io.Serializable {

	// Fields
	private static final long serialVersionUID = 1L;
	private Integer id;
	private User user;
	private Role role;
	private Integer userId;
	private Integer roleId;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	// Constructors

	/** default constructor */
	public UserRole() {
	}

	/** full constructor */
	public UserRole(User user, Role role) {
		this.user = user;
		this.role = role;
	}

	// Property accessors
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Role getRole() {
		return this.role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

}