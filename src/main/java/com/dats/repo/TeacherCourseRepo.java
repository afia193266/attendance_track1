package com.dats.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dats.entity.TeacherCourse;

public interface TeacherCourseRepo extends JpaRepository<TeacherCourse, Integer>{
	
	public List<TeacherCourse> findAllByTeacherId(String teacherId);

}
