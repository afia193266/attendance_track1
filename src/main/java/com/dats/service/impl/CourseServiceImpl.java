package com.dats.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dats.dto.CustomCommonUtils;
import com.dats.dto.User;
import com.dats.entity.Course;
import com.dats.entity.StudentCourse;
import com.dats.entity.TeacherCourse;
import com.dats.entity.UserEntity;
import com.dats.repo.CourseRepo;
import com.dats.repo.StudentCourseRepo;
import com.dats.repo.TeacherCourseRepo;
import com.dats.service.CourseService;
import com.dats.service.SectionService;
import com.dats.service.UserService;

@Service
public class CourseServiceImpl implements CourseService{
	
	@Autowired CourseRepo 			courseRepo;
	@Autowired TeacherCourseRepo 	teacherCourseRepo;
	@Autowired StudentCourseRepo 	studentCourseRepo;
	@Autowired SectionService 		sectionService;
	@Autowired UserService 			userService;
	
	public static final Logger logger = LoggerFactory.getLogger(CourseServiceImpl.class);
	
	@Transactional @Override
	public Course addCourse(Course course) {
		
		//CustomCommonUtils
		course.setCreatedBy(new CustomCommonUtils().getCurrentUserName());
		course.setCreatedOn(LocalDateTime.now());
		
		logger.info("Before save:" + course);
		Course savedCourse = courseRepo.saveAndFlush(course);
		logger.info("After save:" + savedCourse);
		
		return savedCourse;
	}

	@Override
	public Course getCourse(Integer courseId) {
		Optional<Course> course = courseRepo.findById(courseId);
		if(course.isPresent()) {
			return course.get();
		}
		else return null;
	}
	
	@Transactional @Override
	public Course updateCourse(Course updateForm) {
		Optional<Course> courseOptional = courseRepo.findById(updateForm.getCourseId());
		if(courseOptional.isPresent()) {
			Course savedCourse = courseOptional.get();
			
			
			savedCourse.setCourseName(updateForm.getCourseName());
			savedCourse.setCourseCode(updateForm.getCourseCode());
			savedCourse.setCredit(updateForm.getCredit());
			savedCourse.setSemester(updateForm.getSemester());
			
			savedCourse.setUpdatedBy(new CustomCommonUtils().getCurrentUserName());
			savedCourse.setUpdateOn(LocalDateTime.now());
			
			logger.info("Before update:" + savedCourse);
			Course updatedCourse = courseRepo.saveAndFlush(savedCourse);
			logger.info("After update:" + updatedCourse);
			
			return updatedCourse;
			
		}
		else return null;
	}
	
	@Override
	public List<Course> getAllCourse() {
		List<Course> allCourse = courseRepo.findAll();
		if(allCourse.isEmpty())
			return Collections.emptyList();
		else {
		    return allCourse; 	
		}
	}

	@Override
	public void deleteCourse(Integer courseId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public TeacherCourse assignCourse(TeacherCourse courseAssignment) {
		
		logger.info("Before save:" + courseAssignment);
		TeacherCourse savedCourseAssignment = teacherCourseRepo.saveAndFlush(courseAssignment);
		logger.info("After save:" + savedCourseAssignment);
		
		return savedCourseAssignment;
	}

	@Override
	public List<Course> getAssignedCoursesOfTeacher(String teacherId) {
		List<TeacherCourse> assignedCourses = teacherCourseRepo.findAllByTeacherId(teacherId);
		List<Course> teacherCourses = new ArrayList<Course>();
		if(!assignedCourses.isEmpty()) {
			for(TeacherCourse teacherCourse : assignedCourses) {
				Course course = getCourse(teacherCourse.getCourseId());
				course.setSectionName(sectionService
						.getSection(teacherCourse.getSectionId())
						.getSectionName());
				teacherCourses.add(course);
			}
			return teacherCourses;
		}
		else return null;
	}

	@Override
	public List<Course> getCourses(String semester) {
		List<Course> coursesBySemester = courseRepo.findAllBySemester(semester);
		if(coursesBySemester.isEmpty())
			return Collections.emptyList();
		else {
		    return coursesBySemester; 	
		}
	}

	@Override
	public StudentCourse assignCourseStudent(StudentCourse studentCourse) {
		logger.info("Before save:" + studentCourse);
		StudentCourse savedCourseAssignment = studentCourseRepo.saveAndFlush(studentCourse);
		logger.info("After save:" + savedCourseAssignment);
		return savedCourseAssignment;
	}

	@Override
	public List<Course> getAssignedCoursesOfStudent(String studentId) {
		List<StudentCourse> assignedCourses = studentCourseRepo.findAllByStudentId(studentId);
		List<Course> studentCourses = new ArrayList<Course>();
		if(!assignedCourses.isEmpty()) {
			for(StudentCourse studentCourse : assignedCourses) {
				Course course = getCourse(studentCourse.getCourseId());
//				course.setSectionName(sectionService
//						.getSection(studentCourse.getSectionId())
//						.getSectionName());
				studentCourses.add(course);
			}
			return studentCourses;
		}
		else return null;
	}

	@Override
	public List<User> getStudentsByCourse(Integer courseId) {
		List<StudentCourse> studentByCourse = studentCourseRepo.findAllByCourseId(courseId);
		List<User> students = new ArrayList<User>();
		if(!studentByCourse.isEmpty()) {
			for(StudentCourse sc : studentByCourse) {
				UserEntity userEntity = userService.getUser(sc.getStudentId());
				logger.info("user " + userEntity);
				if(userEntity != null) {
					User user =new User();
					BeanUtils.copyProperties(userEntity, user);
					students.add(user);
				}
			}
			return students;
		}
		else return null;
	}
}
