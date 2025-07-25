package com.dats.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "role")
public class RoleEntity implements GrantedAuthority{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@Column(nullable = false, unique = true)
	@NotEmpty
	private String name;

	/*
	 * @ManyToMany
	 * 
	 * @JoinTable( name="role_permission", joinColumns =
	 * {@JoinColumn(name="role_id", referencedColumnName = "id")},
	 * inverseJoinColumns = {@JoinColumn(name="permission_id", referencedColumnName
	 * = "id")} )
	 * 
	 * @LazyCollection(LazyCollectionOption.FALSE) private Set<PermissionEntity>
	 * permissions;
	 */
	
	public RoleEntity() {
	}

	public RoleEntity(String name) {
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

	@Override
	public String getAuthority() {
		// TODO Auto-generated method stub
		return getName();
	}

	@Override
	public String toString() {
		return "RoleEntity [id=" + id + ", name=" + name + "]";
	}

	/*
	 * public Set<PermissionEntity> getPermissions() { return permissions; }
	 * 
	 * public void setPermissions(Set<PermissionEntity> permissions) {
	 * this.permissions = permissions; }
	 */

	
	
}
