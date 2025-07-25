package com.dats.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.dats.dto.Role;
import com.dats.dto.User;
import com.dats.entity.RoleEntity;
import com.dats.entity.UserEntity;
import com.dats.repo.RoleRepository;
import com.dats.repo.UserRepository;
import com.dats.service.SectionService;
import com.dats.service.UserService;


@Service
public class UserServiceImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    
    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

   // private final LoginAttemptService loginAttemptService;

    private final HttpServletRequest request;
    
    @Autowired SectionService	sectionService;
    
    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, HttpServletRequest request) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        //this.loginAttemptService = loginAttemptService;
        this.request = request;
    }

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		UserEntity account = null;
		try {
			account = getUser(username);
			//System.out.println("user account: " + account.getAuthorities());
		} catch (UsernameNotFoundException e) {
			System.out.println("no such user found!!" + e.getMessage());
		}
		if(account == null) {
            throw new UsernameNotFoundException("UserEntity "+username + " has no authorities");
        }
		return new User(account);
	}
  
    @Override
    public User addUser(User user) {
        logger.debug("Adding user = " + user.getUsername());
            UserEntity userEntity = new UserEntity(user);
            if(CollectionUtils.isNotEmpty(user.getRoles())) {
                for(Role roleDto : user.getRoles()) {
                    RoleEntity role = roleRepository.findByName(roleDto.getName());
                    if(role != null) {
                    	 userEntity.addRole(role);
                    }
                }

            }
            logger.info("User Info: "+userEntity);
            userRepository.save(userEntity);
            user = new User(userEntity);
            if(CollectionUtils.isNotEmpty(userEntity.getRoles())) {
                for(RoleEntity role : userEntity.getRoles()) {
                    user.addRole(new Role(role));
                }
            }
            return user;
    }

    @Override
    public User getUser(Long userId) {
        logger.debug("Get user by user id = " + userId);
        return new User(userRepository.findById(userId).get());

    }

    @Override
    public UserEntity getUser(String username) {
        logger.debug("Get user by username = " + username);
        return userRepository.findByUsername(username);
    }

    @Override
    public void updateUser(UserEntity user) {
        logger.debug("Update user = " + user.getUsername());
        userRepository.save(user);
    }

    @Override
    public void updateUser(User user) {
        logger.debug("Update user = " + user.getUsername());
        UserEntity userEntity = new UserEntity(user);
        if(user.getRoles() != null) {
            for(Role roleDto : user.getRoles()) {
                RoleEntity role = roleRepository.findByName(roleDto.getName());
                if(role != null) {
                    userEntity.addRole(role);
                } 
            }

        }
        userRepository.save(userEntity);
    }

    @Override
    public void deleteUser(Long userId) {
        User toBeDeletedUser = getUser(userId);
        if(toBeDeletedUser != null) {
            logger.debug("Deleting user = " + toBeDeletedUser.getUsername());
            userRepository.deleteById(toBeDeletedUser.getId());
        }

    }

    @Override
    public List<User> getUsers() {
        logger.debug("Finding users");
        List<UserEntity> userEntities = userRepository.findAll();
        List<User> userDtos = new ArrayList<>(userEntities.size());
        //TODO need to write mapper
        for(UserEntity user : userEntities) {
            User userDto = new User(user);
            if(CollectionUtils.isNotEmpty(user.getRoles())) {
                List<Role> roles = new ArrayList<>(user.getRoles().size());
                for(RoleEntity role : user.getRoles()) {
                    Role roleDto = new Role(role);
                    roles.add(roleDto);
                }
                userDto.setRoles(roles);
            }
            if(user.getSectionId() != null) {
            	userDto.setSectionName(
            			sectionService.getSection(user.getSectionId()).getSectionName());
            }
            
            userDtos.add(userDto);
        }
        return userDtos;
    }

    @Override
    public List<User> getTeachers() {
        List<UserEntity> userEntities = userRepository.findAll();
        List<User> userDtos = new ArrayList<>(userEntities.size());
        for(UserEntity user : userEntities) {
            User userDto = new User(user);
            if(user.getType()!= null && user.getType().equalsIgnoreCase("Teacher"))
            	userDtos.add(userDto);
        }
        return userDtos;
    }

    @Override
    public List<User> getStudents() {
        List<UserEntity> userEntities = userRepository.findAll();
        List<User> userDtos = new ArrayList<>(userEntities.size());
        for(UserEntity user : userEntities) {
            User userDto = new User(user);
            if(user.getType()!= null && user.getType().equalsIgnoreCase("Student"))
            	userDtos.add(userDto);
        }
        return userDtos;
    }
    
    @Override
    public void assignRoleToUser(Role role, User user) {
        logger.debug("Assigning role= [ " +role.getName() + " ] to user ->  " + user.getUsername());
        RoleEntity assignedRole = roleRepository.findByName(role.getName());
        UserEntity assignedUser = userRepository.findByUsername(user.getUsername());
        assignedUser.addRole(assignedRole);
        userRepository.save(assignedUser);
    }

    @Override
    public void updateProfile(User user) {
        logger.debug("Update user = " + user.getUsername());
        UserEntity storedUser = userRepository.findById(user.getId()).get();
        storedUser.setPassword(user.getPassword());
        userRepository.save(storedUser);
    }

	@Override
	public long countTotalUsers() {
		// TODO Auto-generated method stub
		return userRepository.count();
	}

}
