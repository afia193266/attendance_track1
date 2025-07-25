package com.dats.service;

import java.time.LocalDate;
import java.util.List;

import com.dats.entity.Attendance;

public interface AttendanceService {

	public Attendance saveAttendance(Attendance attendance);
	
	public List<Attendance> getAttendance(String studentId);
	
//	public List<Attendance> getAttendance(Integer courseId);
//	
//	public List<Attendance> getAttendance(Integer courseId, String studentId);
	
	
}
