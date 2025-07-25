package com.dats.service.impl;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dats.dto.CustomCommonUtils;
import com.dats.entity.Section;
import com.dats.repo.SectionRepo;
import com.dats.service.SectionService;

@Service
public class SectionServiceImpl implements SectionService{

	@Autowired SectionRepo sectionRepo;
	
	public static final Logger logger = LoggerFactory.getLogger(SectionServiceImpl.class);
	
	@Transactional @Override
	public Section addSection(Section section) {
		
		//CustomCommonUtils
		section.setCreatedBy(new CustomCommonUtils().getCurrentUserName());
		section.setCreatedOn(LocalDateTime.now());
		
		logger.info("Before save:" + section);
		Section savedSection = sectionRepo.saveAndFlush(section);
		logger.info("After save:" + savedSection);
		
		return savedSection;
	}

	@Override
	public Section getSection(Integer sectionId) {
		Optional<Section> section = sectionRepo.findById(sectionId);
		if(section.isPresent()) {
			return section.get();
		}
		else return null;
	}
	
	@Transactional @Override
	public Section updateSection(Section updateForm) {
		Optional<Section> sectionOptional = sectionRepo.findById(updateForm.getSectionId());
		if(sectionOptional.isPresent()) {
			Section savedSection = sectionOptional.get();
			
			/** update section if not null and empty*/
//			if(updateForm != null && !updateForm.toString().isBlank()) {
//				
//			}
			
			savedSection.setSectionName(updateForm.getSectionName());
			savedSection.setSemester(updateForm.getSemester());
			savedSection.setSession(updateForm.getSession());
			savedSection.setCategory(updateForm.getCategory());
			
			
			savedSection.setUpdatedBy(new CustomCommonUtils().getCurrentUserName());
			savedSection.setUpdateOn(LocalDateTime.now());
			
			logger.info("Before update:" + savedSection);
			Section updatedSection = sectionRepo.saveAndFlush(savedSection);
			logger.info("After update:" + updatedSection);
			
			return updatedSection;
			
		}
		else return null;
	}
	
	@Override
	public List<Section> getAllSection() {
		List<Section> allSection = sectionRepo.findAll();
		if(allSection.isEmpty())
			return Collections.emptyList();
		else {
		    return allSection; 	
		}
	}

	@Override
	public void deleteSection(Integer sectionId) {
		// TODO Auto-generated method stub
		
	}
}
