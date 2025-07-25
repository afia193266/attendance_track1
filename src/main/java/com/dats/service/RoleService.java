package com.dats.service;

import java.util.List;

import com.dats.dto.Role;

public interface RoleService {
    Role addRole(Role role);
    Role getRole(int roleId);
    Role getRole(String roleName);
    Role updateRole(Role role);
    void deleteRole(int roleId);
    List<Role> getRoles();
    void addRoles(List<Role> roles);
}
