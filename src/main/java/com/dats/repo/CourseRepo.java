package com.dats.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dats.entity.Course;

@Repository
public interface CourseRepo extends JpaRepository<Course, Integer>{
	
	public List<Course> findAllBySemester(String semester);

}
