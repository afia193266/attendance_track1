package com.dats.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data @NoArgsConstructor
public class TeacherCourse {
	@Id 
	@GeneratedValue(generator = "sequence-generator")
	@GenericGenerator(name = "sequence-generator", strategy =
	  "org.hibernate.id.enhanced.SequenceStyleGenerator", parameters = {
	  @Parameter(name = "sequence_name", 	value = "tchrcrs_seq"),
	  @Parameter(name = "initial_value", 	value = "1"),
	  @Parameter(name = "increment_size", 	value = "1") })
	private Integer id;
	
	private String teacherId; // userName
	
	private Integer courseId;
	
	private Integer sectionId;

}
