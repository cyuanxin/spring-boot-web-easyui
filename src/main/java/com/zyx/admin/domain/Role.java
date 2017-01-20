package com.zyx.admin.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.HashSet;
import java.util.Set;


/**
 * 角色entity
 * @author ty
 * @date 2015年1月13日
 */
public class Role implements java.io.Serializable {

	// Fields
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String name;
	private String roleCode;
	private String description;
	private Short sort;
	private String delFlag;
	@JsonIgnore
	private Set<UserRole> userRoles = new HashSet<UserRole>(0);
	@JsonIgnore
	private Set<RolePermission> rolePermissions = new HashSet<RolePermission>(0);

	// Constructors

	/** default constructor */
	public Role() {
	}
	
	public Role(Integer id) {
		this.id=id;
	}

	/** minimal constructor */
	public Role(String name, String roleCode) {
		this.name = name;
		this.roleCode = roleCode;
	}

	/** full constructor */
	public Role(String name, String roleCode, String description, Short sort,
                String delFlag, Set<UserRole> userRoles,
                Set<RolePermission> rolePermissions) {
		this.name = name;
		this.roleCode = roleCode;
		this.description = description;
		this.sort = sort;
		this.delFlag = delFlag;
		this.userRoles = userRoles;
		this.rolePermissions = rolePermissions;
	}

	// Property accessors
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRoleCode() {
		return this.roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Short getSort() {
		return this.sort;
	}

	public void setSort(Short sort) {
		this.sort = sort;
	}

	public String getDelFlag() {
		return this.delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}

	public Set<UserRole> getUserRoles() {
		return this.userRoles;
	}

	public void setUserRoles(Set<UserRole> userRoles) {
		this.userRoles = userRoles;
	}

	public Set<RolePermission> getRolePermissions() {
		return this.rolePermissions;
	}

	public void setRolePermissions(Set<RolePermission> rolePermissions) {
		this.rolePermissions = rolePermissions;
	}

}