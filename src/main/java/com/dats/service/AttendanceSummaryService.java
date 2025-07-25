package com.dats.service;

import java.util.List;

import com.dats.dto.AttendanceDetails;
import com.dats.entity.AttendanceSummary;

public interface AttendanceSummaryService {
	
	public AttendanceSummary createAttendaceSheet(Integer courseId);

	public AttendanceSummary saveAttendanceSummary(AttendanceSummary attendanceSummary);
	
	public List<AttendanceSummary> getAttendanceSummary(String studentId);
	
	public AttendanceSummary generateAttendanceReport(Integer courseId);
	
	public AttendanceDetails generateDetailsAttendanceReport(Integer courseId);
	
	public AttendanceSummary generateAttendanceReportStudent(Integer courseId, String studentId);

}
