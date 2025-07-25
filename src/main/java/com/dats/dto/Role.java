package com.dats.dto;

import com.dats.entity.RoleEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class Role {
	
	private Integer id;

	private String name;
	
	@JsonIgnore
	private boolean selected;

	public Role() {

	}

	public Role(String name) {
		this.name = name;
	}

	public Role(final RoleEntity role) {
		if (role != null) {
			this.id = role.getId();
			this.name = role.getName();
		}
	}
	public Role(Integer id, String name) {
		this.id = id;
		this.name = name;
	}


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}


	@Override
	public String toString() {
		return "Role [id=" + id + ", name=" + name + "]";
	}
	
	

}
