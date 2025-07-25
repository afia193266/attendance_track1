package com.dats.service;

import java.util.List;

import com.dats.dto.User;
import com.dats.entity.Course;
import com.dats.entity.StudentCourse;
import com.dats.entity.TeacherCourse;

public interface CourseService {
	
	public Course	addCourse(Course course);
	
	public Course	getCourse(Integer courseId);
	
	public Course	updateCourse(Course course);
	
	public List<Course>	getAllCourse();
	
	public List<Course>	getCourses(String semester);
	
	public void	deleteCourse(Integer courseId);
	

	
	public TeacherCourse	assignCourse(TeacherCourse course);
	public StudentCourse	assignCourseStudent(StudentCourse course);
	
	public List<Course>	getAssignedCoursesOfTeacher(String teacherId);
	public List<Course>	getAssignedCoursesOfStudent(String studentId);
	public List<User>	getStudentsByCourse(Integer courseId);

	//public Map<> List<Course>	getAllCourse();

}
