package com.dats.entity;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.dats.dto.User;


@Entity
@Table(name="user")
public class UserEntity implements UserDetails{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique = true)
	private String username; //we use this as StudentID
	private String password;
    private String email;
	private String name;
	private String type; //teacher/std/admin
	private Integer sectionId;
	
	  @Temporal(TemporalType.TIMESTAMP) 
	  private Date lastAccessDate;
	  
	  @Temporal(TemporalType.TIMESTAMP) 
	  private Date createdOn;
	  
	  private String createdBy;
	  
	  private Date updatedOn;
	  
	  private String updatedBy;
	  
	  private boolean enabled;
	  
	  private boolean tokenExpired;
	 
	
	@ManyToMany
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinTable(name="user_role",
		joinColumns = {
				@JoinColumn(name="user_id", referencedColumnName = "id")},
		
		inverseJoinColumns = {
				@JoinColumn(name="role_id", referencedColumnName = "id")
				}
			)
    private Set<RoleEntity> roles;
	
	
	

	public UserEntity() {
		
	}
	
	
	public UserEntity(Long id, String username, String password, String email) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.email = email;
	}
	
	

	public UserEntity(User user) {
		super();
		
        this.id = user.getId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.name = user.getName();
        this.type = user.getType();
        this.sectionId = user.getSectionId();
        this.email = user.getEmail();
        this.lastAccessDate = user.getLastAccessDate();
        this.createdOn = user.getCreatedOn();
        this.createdBy = user.getCreatedBy();
        this.updatedOn = user.getUpdatedOn();
        this.updatedBy = user.getUpdatedBy();
        this.enabled = user.getEnabled();
        this.tokenExpired = user.isTokenExpired();
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@Override	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	@Override
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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


	public Integer getSectionId() {
		return sectionId;
	}


	public void setSectionId(Integer sectionId) {
		this.sectionId = sectionId;
	}


	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	

	public Set<RoleEntity> getRoles() {
		return roles;
	}
	
	public void addRole(RoleEntity role) {
		if (roles == null) {
			roles = new HashSet<RoleEntity>();
		}
		roles.add(role);
	}

	public void setRoles(Set<RoleEntity> roles) {
		this.roles = roles;
	}
	
	
	public Date getLastAccessDate() {
		return lastAccessDate;
	}


	public void setLastAccessDate(Date lastAccessDate) {
		this.lastAccessDate = lastAccessDate;
	}


	public Date getCreatedOn() {
		return createdOn;
	}


	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}


	public String getCreatedBy() {
		return createdBy;
	}


	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}


	public Date getUpdatedOn() {
		return updatedOn;
	}


	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}


	public String getUpdatedBy() {
		return updatedBy;
	}


	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}


	public boolean isTokenExpired() {
		return tokenExpired;
	}


	public void setTokenExpired(boolean tokenExpired) {
		this.tokenExpired = tokenExpired;
	}


	   public boolean getEnabled() {
	        return enabled;
	    }

	    public void setEnabled(boolean enabled) {
	        this.enabled = enabled;
	    }


	@Override
	public String toString() {
		return "UserEntity [id=" + id + ", username=" + username + ", password=" + password + ", email=" + email
				+ ", name=" + name + ", type=" + type + ", sectionId=" + sectionId + ", lastAccessDate="
				+ lastAccessDate + ", createdOn=" + createdOn + ", createdBy=" + createdBy + ", updatedOn=" + updatedOn
				+ ", updatedBy=" + updatedBy + ", enabled=" + enabled + ", tokenExpired=" + tokenExpired + ", roles="
				+ roles + "]";
	}
	
	

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.addAll(getRoles());
		return authorities;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}


	

}

























