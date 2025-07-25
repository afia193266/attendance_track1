package com.dats.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data @NoArgsConstructor
public class Section {
	@Id 
	@GeneratedValue(generator = "sequence-generator")
	@GenericGenerator(name = "sequence-generator", strategy =
	  "org.hibernate.id.enhanced.SequenceStyleGenerator", parameters = {
	  @Parameter(name = "sequence_name", 	value = "section_seq"),
	  @Parameter(name = "initial_value", 	value = "100"),
	  @Parameter(name = "increment_size", 	value = "1") })
	private Integer sectionId;
	
	private String sectionName;
	
	private String semester;
	
	private String category; // Male/Female
	
	private String session;
	
	@Column(nullable = false)
	private LocalDateTime	createdOn;
	@Column(nullable = false)
	private String			createdBy;
	
	private LocalDateTime	updateOn;
	private String			updatedBy;
	
	
}
