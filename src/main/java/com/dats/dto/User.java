package com.dats.dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.dats.entity.RoleEntity;
import com.dats.entity.UserEntity;


public class User implements UserDetails{
	
	
	private static final long serialVersionUID = 1L;

    private Long id;

    private String username; //we use this as StudentID

    private String password;
    

	private String name;
	private String type; //teacher/std/admin
	private Integer sectionId;
	private String sectionName;
	
    private String email;

    private String tempPassword;

    private String confirm;
    
    private Date lastAccessDate;

    private Date createdOn;

    private String createdBy;

    private Date updatedOn;

    private String updatedBy;

    private boolean enabled;
    
    private boolean tokenExpired;
    
    private boolean attendance; //for attendance
    
    private List<Role> roles;
    
    Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();
    
    public User(UserEntity user) {
		// TODO Auto-generated constructor stub
    	if (user !=null) {
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
            if (user.getRoles() != null) {
				if (this.roles == null) {
					this.roles = new ArrayList<Role>();
				}
				for (RoleEntity role : user.getRoles()) {
					this.roles.add(new Role(role));
				}
            }
			for (GrantedAuthority grantedAuthority : user.getAuthorities()) {
				GrantedAuthority authority = new SimpleGrantedAuthority(grantedAuthority.getAuthority());
				grantedAuthorities.add(authority);
			}
		}
	}
    
	public User() {
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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

	public String getSectionName() {
		return sectionName;
	}

	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
	public String getTempPassword() {
		return tempPassword;
	}

	public void setTempPassword(String tempPassword) {
		this.tempPassword = tempPassword;
	}

	public String getConfirm() {
		return confirm;
	}

	public void setConfirm(String confirm) {
		this.confirm = confirm;
	}
	
	public boolean isTokenExpired() {
		return tokenExpired;
	}

	public void setTokenExpired(boolean tokenExpired) {
		this.tokenExpired = tokenExpired;
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

   public boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isAttendance() {
		return attendance;
	}

	public void setAttendance(boolean attendance) {
		this.attendance = attendance;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	
	public void addRole( Role role) {
        if(roles == null) {
            roles = new ArrayList<>();
        }
        roles.add(role);
    }


	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", name=" + name + ", type="
				+ type + ", sectionId=" + sectionId + ", email=" + email + ", tempPassword=" + tempPassword
				+ ", confirm=" + confirm + ", lastAccessDate=" + lastAccessDate + ", createdOn=" + createdOn
				+ ", createdBy=" + createdBy + ", updatedOn=" + updatedOn + ", updatedBy=" + updatedBy + ", enabled="
				+ enabled + ", tokenExpired=" + tokenExpired + ", attendance=" + attendance + ", roles=" + roles
				+ ", grantedAuthorities=" + grantedAuthorities + "]";
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return grantedAuthorities;
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
