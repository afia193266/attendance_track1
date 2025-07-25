package com.dats.dto;

import java.security.Principal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.dats.controller.HomeController;
import com.dats.entity.UserEntity;
import com.dats.service.UserService;

public class CustomCommonUtils {
	
	@Autowired UserService 	userService;
	
	private static Logger logger = LoggerFactory.getLogger(CustomCommonUtils.class);
	
//	public User getCurrentUser() {
//		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		String currentUserName = authentication.getName();
//
//		logger.info("current user details\t" + authentication.getDetails());
//		logger.info("current user:\t" + currentUserName);
//		//UserDetails userDetails = userService.getUser(currentUserName);
//		User user = (User) UserDetails.loadUserByUsername(currentUserName);
//		//User user = (User) userDetails; 
//		logger.info("current user:\t" + user);
//		//BeanUtils.copyProperties(userEntity, user);
//		return user;
//		
//	}
	
	public String getCurrentUserName() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentUserName = authentication.getName();
		return currentUserName;
		
	}
	
	public LocalDate getFormattedDate(String dateString) {
		// parse date strings into LocalDate object and set to corresponding fields
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		
		if(!dateString.isEmpty() )
			return LocalDate.parse(dateString, formatter);
		else
			return null;
	}

}
