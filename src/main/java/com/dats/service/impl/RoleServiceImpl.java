package com.dats.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dats.dto.Role;
import com.dats.entity.RoleEntity;
import com.dats.repo.RoleRepository;
import com.dats.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService{
private static final Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);
    
    private final RoleRepository roleRepository;
    

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role addRole(Role role) {
        logger.info("Adding role = " + role.getName());
            RoleEntity roleEntity = new RoleEntity(role.getName());
            roleRepository.save(roleEntity);
            role = new Role(roleEntity);
            return role;
    }

    @Override
    public Role getRole(int roleId) {
        logger.info("Get role by role id = " + roleId);
        RoleEntity role = roleRepository.findById(roleId).get();
        if(role != null) {
            Role roleDto = new Role(role);
            return roleDto;
        } else {
            return null;
        }

    }

    @Override
    public Role getRole(String roleName) {
        logger.info("Get role by role name = " + roleName);
        RoleEntity role = roleRepository.findByName(roleName);
        if(role == null) {
            return null;
        } else {
            return new Role(roleRepository.findByName(roleName));
        }
    }

    @Override
    public Role updateRole(Role role) {
		logger.info("Update role = " + role.getName());
		//RoleEntity storedRole = roleRepository.findByName(role.getName());
		RoleEntity storedRole = roleRepository.findById(role.getId()).get();
		if (storedRole !=null) {
			storedRole.setName(role.getName());
			roleRepository.save(storedRole);
			return role;
		}else
			return null;
		 		
    }

    @Override
    public void deleteRole(int roleId) {
        RoleEntity toBeDeletedRole = roleRepository.findById(roleId).get();
        if(toBeDeletedRole != null) {
            logger.info("Deleting role = " + toBeDeletedRole.getName());
            roleRepository.delete(toBeDeletedRole);
        }

    }

    @Override
    public List<Role> getRoles() {
        logger.info("Finding roles");
        List<RoleEntity> roleEntities = roleRepository.findAll();
        List<Role> roles = new ArrayList<>(roleEntities.size());
        for(RoleEntity role : roleEntities) {
            Role roleDto = new Role(role.getId(), role.getName());
            roles.add(roleDto);
        }
        return roles;
    }


    @Override
    public void addRoles(List<Role> roles) {
        List<RoleEntity> roleEntities = new ArrayList<>();
        for(Role dto : roles) {
            roleEntities.add(new RoleEntity(dto.getName()));
        }
        roleRepository.saveAll(roleEntities);
    }
}
