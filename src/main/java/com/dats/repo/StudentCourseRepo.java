package com.dats.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dats.entity.StudentCourse;
import com.dats.entity.TeacherCourse;

public interface StudentCourseRepo extends JpaRepository<StudentCourse, Integer>{
	
	public List<StudentCourse> findAllByStudentId(String studentId);
	public List<StudentCourse> findAllByCourseId(Integer courseId);
	

}
