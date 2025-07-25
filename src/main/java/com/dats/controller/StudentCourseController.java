package com.dats.controller;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

import com.dats.dto.Enabled;
import com.dats.dto.User;
import com.dats.entity.Course;
import com.dats.entity.StudentCourse;
import com.dats.entity.TeacherCourse;
import com.dats.service.CourseService;
import com.dats.service.SectionService;
import com.dats.service.UserService;

@Controller
public class StudentCourseController {
	
	@Autowired CourseService 		courseService;
	@Autowired SectionService 		sectionService;
	@Autowired UserService 			userService;
	
	public static final Logger logger = LoggerFactory.getLogger(StudentCourseController.class);
	
	@ModelAttribute
	public void addAttributes(Model model) {
	    model.addAttribute("courseList", courseService.getAllCourse());
	    //model.addAttribute("sectionList", sectionService.getAllSection());
	    model.addAttribute("studentList", userService.getStudents());
	}
	
   
//    @ModelAttribute("studentList")
//    public List<User> populateStudents(Model model) {
//	    //model.addAttribute("studentList", userService.getStudents());
//    	return userService.getStudents();
//    }
	
	@GetMapping("assign-course-student.html")
	public String assignCourse(Model m) {
		m.addAttribute("studentCourse", new StudentCourse());
		return "dats/course/course-assign-std";
	}
	
	@PostMapping("find-courses")
	public String findCoursesBySemester(@Valid StudentCourse searchForm, Model m, RedirectAttributes rA) {
		logger.info("searchForm\t" + searchForm);
		StudentCourse studentCourse = new StudentCourse();
		studentCourse.setStudentId(searchForm.getStudentId());
		studentCourse.setSemester(searchForm.getSemester());
		List<Course> courses = courseService.getCourses(searchForm.getSemester());
		if(!courses.isEmpty())
			studentCourse.setCourseList(courses);
		else
			m.addAttribute("errorMessage", "No course found in semester -" + searchForm.getSemester());	
		m.addAttribute("studentCourse", studentCourse);
		return "dats/course/course-assign-std";
	}
	
//	@PostMapping("find-courses")
//	public String findCoursesBySemester(HttpServletRequest req, HttpServletResponse res, Model m) {
//		
//		String username = req.getParameter("username");
//	    String semester = req.getParameter("semester");
//		
//		logger.info("username:  " + username + "\tsemester:" + semester);
//		StudentCourse studentCourse = new StudentCourse();
//		studentCourse.setStudentId(username);
//		List<Course> courses = courseService.getCourses(semester);
//		if(!courses.isEmpty())
//			studentCourse.setCourseList(courses);
//		else
//			m.addAttribute("errorMessage", "No course found in semester -" + semester);	
//		m.addAttribute("studentCourse", studentCourse);
//		return "dats/course/course-assign-std";
//	}
	
	@PostMapping("course-assign-student-save")
	public String saveStudentCourseAssignment(@Valid StudentCourse assignForm, Model m, RedirectAttributes rA) {
		logger.info("assignForm\t" + assignForm);
		for(Course course : assignForm.getCourseList()) {
			if(course != null && !course.toString().isBlank()) {
				StudentCourse studentCourse = new StudentCourse();
				studentCourse.setStudentId(assignForm.getStudentId());
				studentCourse.setCourseId(course.getCourseId());
				
				//save
				StudentCourse assignedCourse = courseService.assignCourseStudent(studentCourse);
			}
		}
		
//		if(assignedCourse == null) {
//			m.addAttribute("teacherCourse", assignedCourse).addAttribute("errorMessage", "Check for errors");
//			return "dats/course/course-assign-std";
//		}else {
//			rA.addFlashAttribute("successMessage", "Course assigned successfully.!!");
//			return "redirect:/assign-course-student.html";
//		}

		rA.addFlashAttribute("successMessage", "Course assigned successfully.!!");
		return "redirect:/assign-course-student.html";
	}
	
	
	
	
}
