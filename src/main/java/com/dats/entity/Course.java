package com.dats.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data @NoArgsConstructor
public class Course {
	@Id 
	@GeneratedValue(generator = "sequence-generator")
	@GenericGenerator(name = "sequence-generator", strategy =
	  "org.hibernate.id.enhanced.SequenceStyleGenerator", parameters = {
	  @Parameter(name = "sequence_name", 	value = "course_seq"),
	  @Parameter(name = "initial_value", 	value = "1"),
	  @Parameter(name = "increment_size", 	value = "1") })
	private Integer courseId;
	
	private String courseCode;
	
	private String courseName;
	
	private Double credit;
	
	private String semester;
	
	@Column(nullable = false)
	private LocalDateTime	createdOn;
	@Column(nullable = false)
	private String			createdBy;
	
	private LocalDateTime	updateOn;
	private String			updatedBy;
	
	
	//all transient fields those are not stored into db
	@Transient
	private String 			sectionName;
	
	
}

