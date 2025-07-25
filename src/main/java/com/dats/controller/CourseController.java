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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dats.entity.Course;
import com.dats.service.CourseService;

@Controller
public class CourseController {
	
	@Autowired CourseService courseService;
	
	public static final Logger logger = LoggerFactory.getLogger(CourseController.class);
	
	@ModelAttribute
	public void addAttributes(Model model) {
	    model.addAttribute("courseList", courseService.getAllCourse());
	}
	
	@GetMapping("course.html")
	public String showCourseHome(Model m) {
		return "dats/course/course.html";
	}
	
	@GetMapping("course-add.html")
	public String showCourseAddPage(Model m) {
		m.addAttribute("course", new Course());
		return "dats/course/course-add.html";
	}
	
	@PostMapping("course-add-save")
	public String saveCourseAddPage(@Valid Course course, Model m, RedirectAttributes rA) {
		
		Course savedCourse = courseService.addCourse(course);
		if(savedCourse == null) {
			m.addAttribute("course", course).addAttribute("errorMessage", "Course\t " + course.getCourseName() +"\t already exists..!!");
			return "dats/course/course-add.html";
		}else {
			rA.addFlashAttribute("successMessage", "Course\t " + course.getCourseName() +"\t saved successfully.!!");
			return "redirect:/course-add.html";
		}
	}
	
	@GetMapping("course-update.html")
	public String showCourseUpdatePage(Model m,  @RequestParam Integer id, RedirectAttributes ra) {
		Course courseEntity = courseService.getCourse(id);
		if(courseEntity != null){
			m.addAttribute("course", courseEntity).addAttribute("editButton", true);
			return "dats/course/course-add.html";
		}else {
			ra.addFlashAttribute("errorMessage", "Could not find any course\t ");
			return "redirect:/course-add.html";			
		}
	}
	
	@PostMapping("course-update-save")
	public String updateCourseAddPage(@Valid Course course, Model m, RedirectAttributes rA) {
		Course savedCourse = courseService.updateCourse(course);
		if(savedCourse == null) {
			m.addAttribute("course", course).addAttribute("editButton", true)
			 .addAttribute("errorMessage", "Course\t " + course.getCourseName() +"\t already exists..!!");
			return "dats/course/course-add.html";
		}else {
			rA.addFlashAttribute("successMessage", "Course\t " + course.getCourseName() +"\t updated successfully.!!");
			return "redirect:/course.html";
		}
	}

}
