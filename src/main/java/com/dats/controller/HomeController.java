package com.dats.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.dats.dto.CustomCommonUtils;
import com.dats.dto.User;
import com.dats.entity.UserEntity;
import com.dats.repo.UserRepository;
import com.dats.service.CourseService;


@Controller
public class HomeController {
	
	@Autowired CourseService courseService;
	@Autowired UserRepository userRepo;
	
	private static Logger logger = LoggerFactory.getLogger(HomeController.class);
	
//	@GetMapping("/")
//	public String test(Model m, Authentication authentication) {
//		UserEntity currentUser = userRepo.findByUsername(authentication.getName());
//		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//		System.out.println("User has authorities: " + userDetails.getAuthorities());
//		System.out.println("User details: " + currentUser);
//		Boolean student=false,  teacher=false;
//		for(GrantedAuthority ud : userDetails.getAuthorities()) {
//			if( ud.toString().equalsIgnoreCase("student")) {
//				student=true;
//				System.out.println("student " + student);
//			}
//			else if (ud.toString().equalsIgnoreCase("teacher")) {
//				teacher=true;
//				System.out.println("teacher " + teacher);
//			}
//		}
//		
//		if(teacher==true) {
//			System.out.println("Teacher Ok" );
//			m.addAttribute("assignedCourses", 
//				courseService.getAssignedCoursesOfTeacher(
//						new CustomCommonUtils().getCurrentUserName()))
//			 .addAttribute("teacher", true);
//			
//			return "home";
//		}
//		else if(student==true) {
//			System.out.println("Student Ok" );
//			m.addAttribute("assignedCourses", 
//					courseService.getAssignedCoursesOfStudent(
//							new CustomCommonUtils().getCurrentUserName()))
//			 .addAttribute("student", true);
//			return "home";
//		}
//		else
//			return "admin-dashboard"; 
//	}

	@GetMapping("/")
	public String showDashboard(Model m, Authentication authentication) {
		UserEntity currentUser = userRepo.findByUsername(authentication.getName());
		System.out.println("User: " + currentUser);
		
		if(currentUser.getType() == null) {
			return "admin-dashboard"; 
		}
		
		if(currentUser.getType() != null && currentUser.getType().equalsIgnoreCase("teacher")) {
			System.out.println("Teacher Ok" );
			m.addAttribute("assignedCourses", 
				courseService.getAssignedCoursesOfTeacher(
						currentUser.getUsername()))
			 .addAttribute("teacher", true);
		}
		else if(currentUser.getType() != null && currentUser.getType().equalsIgnoreCase("student")) {
			System.out.println("Student Ok" );
			m.addAttribute("assignedCourses", 
					courseService.getAssignedCoursesOfStudent(
							currentUser.getUsername()))
			 .addAttribute("student", true);
		}
			
		return "home";
	}

	
	@GetMapping("/login.html")
	public String login() {
		return "login";
	}
	
	@GetMapping("/add-student")
	public String addStudent(Model m) {
		return "home"; 
	}
	

}
