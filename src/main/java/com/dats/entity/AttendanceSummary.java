package com.dats.entity;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.dats.dto.User;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="attdsummary")
@Data @NoArgsConstructor
public class AttendanceSummary {
	
	@Id @GeneratedValue 
	private Integer attdId;

	@Column(nullable = false)
	private Integer courseId;
	
	@Column(nullable = false)
	private LocalDate	attdDate;
	
	@Column(nullable = false)
	private Integer 	classes; // no of classes on that day
	
	@Column(nullable = false)
	private String			createdBy;
	
	


	@Transient
	private String	attdDateStr;
	@Transient
	private Integer totalClass;
	@Transient
	private Course course;
	@Transient
	private List<Attendance> attendanceList;
	@Transient
	private List<User> studentList;

}
