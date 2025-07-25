package com.dats.config;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Scope;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.dats.entity.RoleEntity;
import com.dats.entity.UserEntity;
import com.dats.repo.RoleRepository;
import com.dats.repo.UserRepository;

/*@Component*/
@Component("InitialDataLoader")
//@Scope("view")
public class InitialDataLoader implements ApplicationListener<ContextRefreshedEvent>{
	
	boolean alreadySetup;
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private RoleRepository roleRepo;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		
		if(roleRepo.findAll().size()==0)
		{
			RoleEntity roleAdmin = new RoleEntity();
			roleAdmin.setName("admin");
			roleRepo.save(roleAdmin);
			
			RoleEntity roleStudent = new RoleEntity();
			roleStudent.setName("student");
			roleRepo.save(roleStudent);
			
			RoleEntity roleTeacher = new RoleEntity();
			roleTeacher.setName("teacher");
			roleRepo.save(roleTeacher);
			
			RoleEntity roleGuest = new RoleEntity();
			roleGuest.setName("guest");
			roleRepo.save(roleGuest);
			
			
			/* start create default user admin */
			Set<RoleEntity> rolesForAdmin =  new HashSet<RoleEntity>();
			rolesForAdmin.add(roleAdmin);
			rolesForAdmin.add(roleStudent);
			rolesForAdmin.add(roleTeacher);
			rolesForAdmin.add(roleGuest);
			
			UserEntity userAdmin = new UserEntity();
			userAdmin.setPassword(passwordEncoder.encode("admin"));
			userAdmin.setUsername("admin");
			userAdmin.setEmail("admin@gmail.com");
			userAdmin.setRoles(rolesForAdmin);
			userAdmin.setEnabled(true);
			userRepo.save(userAdmin);    
			/* end create default user admin */
			
			alreadySetup = true;
		}
		
	}


}
