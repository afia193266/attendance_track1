package com.dats.service;

import java.util.List;

import com.dats.entity.Section;

public interface SectionService {
	
	public Section	addSection(Section section);
	
	public Section	getSection(Integer sectionId);
	
	public Section	updateSection(Section section);
	
	public List<Section>	getAllSection();
	
	public void	deleteSection(Integer sectionId);

}
