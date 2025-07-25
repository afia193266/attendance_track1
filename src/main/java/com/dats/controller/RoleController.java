package com.dats.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.dats.dto.Role;
import com.dats.service.RoleService;

@Controller
public class RoleController {
	  	private static final Logger logger = LoggerFactory.getLogger(RoleController.class);
	  	
	  	@Autowired
	    private RoleService roleService;

	    @GetMapping("/profile-role-list.html")
	    public String landing(Model model)
	    {
	        populateRoles(model);
	        return "profile/role/profile-role";
	    }
	    
	    private void populateRoles(Model model) {
	        List<Role> roles = roleService.getRoles();
	        model.addAttribute("roles", roles);
	    }
	    
	    @GetMapping("/profile-role.html")
	    public String viewRole(Model model)
	    {
	    	populateRoles(model);
	        return "profile/role/profile-role";
	    }
	    
	    @GetMapping("/profile-role-add.html")
	    public String roleAdd(Model model)
	    {
	        Role role = new Role();
	        model.addAttribute("role", role);
	        return "profile/role/profile-role-add";
	    }
	    
	    @PostMapping(value = "/profile-role-add-save")
	    public ModelAndView roleAddSave(@ModelAttribute Role role, @RequestParam String action)
	    {
	    	System.out.println("role: " + role);
	        if(logger.isDebugEnabled())
	        {
	            logger.debug("START - profile-role-add-save");
	        }

	        ModelAndView modelAndView ;

	        if("cancel".equals(action))
	        {
	            modelAndView = getModelAndViewForCancelFlow();
	            return modelAndView;
	        }
	        try
	        {
	            role = roleService.addRole(role);
	            String message = "Role '" + role.getName() + "' successfully added";
	            modelAndView = getModelAndViewForCancelFlow();
	            modelAndView.addObject("successMessage", message);
	        }
	        catch (Exception e)
	        {
	            modelAndView = new ModelAndView("profile/role/profile-role-add");
	            modelAndView.addObject("role", role);
	            modelAndView.addObject("errorMessage", e.getMessage());
	            logger.error(e.getMessage(), e);
	        }

	        if(logger.isDebugEnabled())
	        {
	            logger.debug("END - profile-role-add-save");
	        }

	        return modelAndView;
	    }
	    
	    
	    @GetMapping("/profile-role-update.html")
	    public String roleEdit(Model model, @RequestParam(value = "id") String id)
	    {
	        Integer idLong;
	        try
	        {
	            idLong = Integer.valueOf(id);
	        }
	        catch (NumberFormatException e)
	        {
	            model.addAttribute("errorMessage", "Invalid Role Id. Please provide proper Role Id to Edit.");
	            return "profile/role/profile-role-update";
	        }
	        Role role = roleService.getRole(idLong);
	        model.addAttribute("role", role);
	        return "profile/role/profile-role-update";
	    }

	    @PostMapping(value = "/profile-role-update-save")
	    public ModelAndView roleUpdateSave(@ModelAttribute Role role, @RequestParam String action)
	    {

	        if(logger.isDebugEnabled())
	        {
	            logger.debug("START - profile-role-update-save");
	        }

	        ModelAndView modelAndView ;

	        if("cancel".equals(action))
	        {
	            modelAndView = getModelAndViewForCancelFlow();
	            return modelAndView;
	        }


	        try
	        {
	            role = roleService.updateRole(role);
	            String message = "Role '" + role.getName() + "' successfully updated";
	            modelAndView = getModelAndViewForCancelFlow();
	            modelAndView.addObject("successMessage", message);
	        }
	        catch (Exception e)
	        {
	            modelAndView = new ModelAndView("profile/role/profile-role-update");
	            modelAndView.addObject("role", role);
	            modelAndView.addObject("errorMessage", e.getMessage());
	            logger.error(e.getMessage(), e);
	        }

	        if(logger.isDebugEnabled())
	        {
	            logger.debug("END - profile-role-update-save");
	        }

	        return modelAndView;
	    }

	    @GetMapping("/profile-role-delete.html")
	    public String roleDelete(Model model, @RequestParam(value = "id") String id)
	    {

	        Integer idLong;
	        try
	        {
	            idLong = Integer.valueOf(id);
	        }
	        catch (NumberFormatException e)
	        {
	            model.addAttribute("errorMessage", "Invalid Role Id. Please provide proper Role Id to delete.");
	            populateRoles(model);
	            return "profile/role/profile-role";
	        }
	        try {
	            roleService.deleteRole(idLong);
	            populateRoles(model);
	            model.addAttribute("successMessage", "Successfully deleted Role.");
	            return "profile/role/profile-role";
	        }
	        catch (Exception e)
	        {
	            populateRoles(model);
	            model.addAttribute("errorMessage", e.getMessage());
	            return "profile/role/profile-role";
	        }
	    }
	    
	    
	    private ModelAndView getModelAndViewForCancelFlow() {
	        ModelAndView modelAndView = new ModelAndView("redirect:/profile-role.html");
	        List<Role> roles = roleService.getRoles();
	        modelAndView.addObject("roles", roles);
	        return modelAndView;
	    }
}
