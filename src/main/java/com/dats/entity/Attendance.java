package com.dats.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.dats.dto.User;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data @NoArgsConstructor
public class Attendance {
	@Id 
	@GeneratedValue(generator = "sequence-generator")
	@GenericGenerator(name = "sequence-generator", strategy =
	  "org.hibernate.id.enhanced.SequenceStyleGenerator", parameters = {
	  @Parameter(name = "sequence_name", 	value = "attendance_seq"),
	  @Parameter(name = "initial_value", 	value = "1"),
	  @Parameter(name = "increment_size", 	value = "1") })
	private Integer id;
	
	@Column(nullable = false)
	private Integer attdId;

	@Column(nullable = false)
	private String studentId; // userId
	
	@Column(columnDefinition = "boolean default false", nullable = false)
	private boolean			attendance;

	@Column(nullable = false)
	private Integer courseId;
//	
//	@Column(nullable = false)
//	private LocalDate	attdDate;
//	
//	@Column(nullable = false)
//	private String			createdBy;
	
	


	@Transient
	private Integer totalPresent;
	@Transient
	private String percentage;
//	@Transient
//	private String	attdDateStr;
//	@Transient
//	private Integer totalClass;
//	@Transient
//	private Course course;
//	@Transient
//	private List<User> studentList;
	
	
	
}


