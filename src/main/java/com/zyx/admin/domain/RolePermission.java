package com.zyx.admin.domain;

/**
 * 角色权限entity
 * @author ty
 * @date 2015年1月13日
 */
public class RolePermission implements java.io.Serializable {

	// Fields
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer permissionId;
	private Integer roleId;
	private Perm permission;
	private Role role;

	// Constructors

	/** default constructor */
	public RolePermission() {
	}

	/** full constructor */
	public RolePermission(Perm permission, Role role) {
		this.permission = permission;
		this.role = role;
	}

	public Integer getId() {
		return this.id;
	}

	public Integer getPermissionId() {
		return permissionId;
	}

	public void setPermissionId(Integer permissionId) {
		this.permissionId = permissionId;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Perm getPermission() {
		return this.permission;
	}

	public void setPermission(Perm permission) {
		this.permission = permission;
	}

	public Role getRole() {
		return this.role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

}