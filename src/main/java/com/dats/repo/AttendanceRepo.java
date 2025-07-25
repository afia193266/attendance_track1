package com.dats.repo;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dats.entity.Attendance;
@Repository
public interface AttendanceRepo extends JpaRepository<Attendance, Integer>{
	
	public List<Attendance> findAllByStudentId(String studentId);
	
	//public List<Attendance> findAllByCourseId(Integer courseId);
	
	//public List<Attendance> findAllByCourseIdAndStudentId(Integer courseId, String studentId);
	
	@Query(nativeQuery = true, value = "SELECT count(*) FROM ("
			+ " SELECT count(distinct course_id) FROM Attendance a "
			+ " where a.course_id=:courseId group by a.attd_date) e; ")
	public Long	countTotalClass(@Param("courseId") Integer courseId);

	@Query(nativeQuery = true, value ="SELECT SUM(attendance) FROM Attendance a "
			+ " where a.student_id=:studentId and course_id=:courseId")
	public Integer sumAttendanceByStudentIdAndCourseId(@Param("studentId") String studentId, @Param("courseId") Integer courseId);
}
