package com.dats.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dats.entity.AttendanceSummary;

@Repository
public interface AttendanceSummaryRepo extends JpaRepository<AttendanceSummary, Integer>{

	public List<AttendanceSummary> findAllByCourseId(Integer courseId);
	
	@Query(nativeQuery = true, value ="SELECT sum(classes) FROM attdsummary "
			+ " where course_id=:courseId")
	public Integer sumTotalClass(@Param("courseId") Integer courseId);
}

