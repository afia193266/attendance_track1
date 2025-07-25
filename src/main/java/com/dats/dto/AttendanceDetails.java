package com.dats.dto;

import java.util.List;

import com.dats.entity.Attendance;
import com.dats.entity.AttendanceSummary;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor
public class AttendanceDetails {
	
	private AttendanceSummary 	attendanceSummary;
	private Attendance 			attendance;
	private User 				student;
	
	private List<AttendanceSummary> 	atdSumList;
	private List<Attendance> 			atdDetList;
	private List<User> 					stdList;
	
	

}
