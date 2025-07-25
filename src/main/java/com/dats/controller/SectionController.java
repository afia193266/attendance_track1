package com.dats.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dats.entity.Section;
import com.dats.service.SectionService;

@Controller
public class SectionController {
	
	@Autowired SectionService sectionService;
	
	public static final Logger logger = LoggerFactory.getLogger(SectionController.class);
	
	@ModelAttribute
	public void addAttributes(Model model) {
	    model.addAttribute("sectionList", sectionService.getAllSection());
	}
	
	@GetMapping("section.html")
	public String showSectionHome(Model m) {
		return "dats/section/section.html";
	}
	
	@GetMapping("section-add.html")
	public String showSectionAddPage(Model m) {
		m.addAttribute("section", new Section());
		return "dats/section/section-add.html";
	}
	
	@PostMapping("section-add-save")
	public String saveSectionAddPage(@Valid Section section, Model m, RedirectAttributes rA) {
		
		Section savedSection = sectionService.addSection(section);
		if(savedSection == null) {
			m.addAttribute("section", section).addAttribute("errorMessage", "Section\t " + section.getSectionName() +"\t already exists..!!");
			return "dats/section/section-add.html";
		}else {
			rA.addFlashAttribute("successMessage", "Section\t " + section.getSectionName() +"\t saved successfully.!!");
			return "redirect:/section-add.html";
		}
	}
	
	@GetMapping("section-update.html")
	public String showSectionUpdatePage(Model m,  @RequestParam Integer id, RedirectAttributes ra) {
		Section sectionEntity = sectionService.getSection(id);
		if(sectionEntity != null){
			m.addAttribute("section", sectionEntity).addAttribute("editButton", true);
			return "dats/section/section-add.html";
		}else {
			ra.addFlashAttribute("errorMessage", "Could not find any section\t ");
			return "redirect:/section-add.html";			
		}
	}
	
	@PostMapping("section-update-save")
	public String updateSectionAddPage(@Valid Section section, Model m, RedirectAttributes rA) {
		Section savedSection = sectionService.updateSection(section);
		if(savedSection == null) {
			m.addAttribute("section", section).addAttribute("editButton", true)
			 .addAttribute("errorMessage", "Section\t " + section.getSectionName() +"\t already exists..!!");
			return "dats/section/section-add.html";
		}else {
			rA.addFlashAttribute("successMessage", "Section\t " + section.getSectionName() +"\t updated successfully.!!");
			return "redirect:/section.html";
		}
	}
	
	

}
