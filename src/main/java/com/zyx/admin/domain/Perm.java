package com.zyx.admin.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.HashSet;
import java.util.Set;

/**
 * 权限 entity
 * @author zhangyuanxin
 * @date 2015年1月13日
 */
//permission 用了导致bug
public class Perm {

	// Fields
	private Integer id;
	private Integer pid;
	private String name;
	private String type;
	private Integer sort;
	private String url;
	private String icon;
	private String permCode;
	private String description;
	private String state;
	@JsonIgnore
	private Set<RolePermission> rolePermissions = new HashSet<RolePermission>(0);

	// Constructors

	/** default constructor */
	public Perm() {
	}

	/** minimal constructor */
	public Perm(String name) {
		this.name = name;
	}

	public Perm(Integer id) {
		this.id=id;
	}

	public Perm(Integer id, Integer pid, String name){
		this.id=id;
		this.pid=pid;
		this.name=name;
	}

	public Perm(Integer pid, String name, String type, String url, String permCode){
		this.pid=pid;
		this.name=name;
		this.type=type;
		this.url=url;
		this.permCode=permCode;
	}

	/** full constructor */


	// Property accessors


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getPermCode() {
        return permCode;
    }

    public void setPermCode(String permCode) {
        this.permCode = permCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}