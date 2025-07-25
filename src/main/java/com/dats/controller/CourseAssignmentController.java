package com.dats.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dats.entity.Course;
import com.dats.entity.TeacherCourse;
import com.dats.service.CourseService;
import com.dats.service.SectionService;
import com.dats.service.UserService;

@Controller
public class CourseAssignmentController {
	
	@Autowired CourseService 		courseService;
	@Autowired SectionService 		sectionService;
	@Autowired UserService 			userService;
	
	public static final Logger logger = LoggerFactory.getLogger(CourseAssignmentController.class);
	
	@ModelAttribute
	public void addAttributes(Model model) {
	    model.addAttribute("courseList", courseService.getAllCourse());
	    model.addAttribute("sectionList", sectionService.getAllSection());
	    model.addAttribute("teacherList", userService.getTeachers());
	}
	
	@GetMapping("course-assignmnet.html")
	public String assignedCourse(Model m) {
		//m.addAttribute("teacherCourse", new TeacherCourse());
		return "dats/course/course-assign";
	}
	
	@GetMapping("assign-course.html")
	public String assignCourse(Model m) {
		m.addAttribute("teacherCourse", new TeacherCourse());
		return "dats/course/course-assign";
	}
	
	@PostMapping("course-assign-save")
	public String saveCourseAssignment(@Valid TeacherCourse assignForm, Model m, RedirectAttributes rA) {
		logger.info("assignForm\t" + assignForm);
		TeacherCourse assignedCourse = courseService.assignCourse(assignForm);
		if(assignedCourse == null) {
			m.addAttribute("teacherCourse", assignedCourse).addAttribute("errorMessage", "Check for errors");
			return "dats/course/course-assign";
		}else {
			rA.addFlashAttribute("successMessage", "Course assigned successfully.!!");
			return "redirect:/assign-course.html";
		}
	}
	
	
	
	
}
