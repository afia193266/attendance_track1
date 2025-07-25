package com.dats.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dats.dto.Enabled;
import com.dats.dto.Role;
import com.dats.dto.User;
import com.dats.exceptions.NoDataFoundException;
import com.dats.service.RoleService;
import com.dats.service.SectionService;
import com.dats.service.UserService;

@Controller
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService 		userService;

    private final RoleService 		roleService;

    private final PasswordEncoder 	passwordEncoder;

    private final SectionService 	sectionService;

    @Autowired
    public UserController(UserService userService, PasswordEncoder passwordEncoder, 
    		RoleService roleService, SectionService sectionService) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
        this.sectionService = sectionService;
    }
    
    @ModelAttribute("allEnabled")
    public List<Enabled> populateEnabled() {
        return Arrays.asList(Enabled.values());
    }

    
    @GetMapping("/profile-user-list.html")
    public String landing(Model model)
    {
        populateUsers(model);
        return "profile/profile-user-list";
    }
    
    private void populateUsers(Model model) {
        List<User> users = userService.getUsers();
        model.addAttribute("users", users);
    }
    
    @GetMapping("/profile-user.html")
    public String viewProfileUser(Model model)
    {
    	populateUsers(model);
        return "profile/user/profile-user";
    }
    
    @GetMapping("/profile-user-add.html")
    public String userAdd(Model model)
    {
        User user = new User();
        user.setRoles(roleService.getRoles());
        model.addAttribute("user", user)
        	.addAttribute("sectionList", sectionService.getAllSection());
        return "profile/user/profile-user-add";
    }
    
    @Transactional
    @PostMapping(value = "/profile-user-add-save")
    public ModelAndView userAddSave(@ModelAttribute User user, @RequestParam String action)
    {

        if(logger.isDebugEnabled())
        {
            logger.debug("START - profile-user-add-save");
        }

        ModelAndView modelAndView ;

        if("cancel".equals(action))
        {
            modelAndView = getModelAndViewForCancelFlow();
            return modelAndView;
        }
        try
        {
            if(user.getPassword() == null)
            {
                modelAndView = new ModelAndView("profile/user/profile-user-add");
                modelAndView.addObject("errorMessage", "Password is required.");
                modelAndView.addObject("user", user);
                return modelAndView;
            }
            if(!user.getPassword().equals(user.getConfirm()))
            {
                modelAndView = new ModelAndView("profile/user/profile-user-add");
                modelAndView.addObject("errorMessage", "Confirm does not patch with Password.");
                modelAndView.addObject("user", user);
                return modelAndView;
            }

            List<Role> selectedRole = new ArrayList<>();
            for(Role role : user.getRoles())
            {
                if (role.isSelected())
                {
                    selectedRole.add(role);
                }
            }
            user.getRoles().clear();
            if(CollectionUtils.isNotEmpty(selectedRole)) {
                user.setRoles(selectedRole);
            }
            else {
                user.setRoles(roleService.getRoles());
            	modelAndView = new ModelAndView("profile/user/profile-user-add");
                modelAndView.addObject("errorMessage", "Please select atleast one role ");
                modelAndView.addObject("user", user);
                return modelAndView;
            }
            String planPassword = user.getPassword();
            user.setPassword(passwordEncoder.encode(planPassword));
            user = userService.addUser(user);
            String message = "User '" + user.getUsername() + "' successfully added";
            modelAndView = getModelAndViewForCancelFlow();

            //modelAndView =  new ModelAndView("redirect:/profile-user.html");
            modelAndView.addObject("successMessage", message);
            
        }
        catch (Exception e)
        {
            modelAndView = new ModelAndView("profile/user/profile-user-add");
            modelAndView.addObject("user", user);
            modelAndView.addObject("errorMessage", e.getMessage());
            logger.error(e.getMessage(), e);
        }

        if(logger.isDebugEnabled())
        {
            logger.debug("END - profile-user-add-save");
        }

        return modelAndView;
    }
    
    @GetMapping("/profile-user-update.html")
    public String userEdit(Model model, @RequestParam(value = "id") String id)
    {
    	
        if(id == null)
        {
            model.addAttribute("errorMessage", "Invalid User Id. Please provide proper User Id to Edit.");
            return "profile/user/profile-user-update";
        }
        Long idLong;
        try
        {
            idLong = Long.valueOf(id);
        }
        catch (NumberFormatException e)
        {
            model.addAttribute("errorMessage", "Invalid User Id. Please provide proper User Id to Edit.");
            return "profile/user/profile-user-update";
        }
        User user = userService.getUser(idLong);
        System.out.println("user: " + user);
        if(user == null)
        {
            model.addAttribute("errorMessage", "Could not found User with Id : "+id);
            return "profile/user/profile-user-update";
        }

        Map<Integer, Role> stringRoleMap = new HashMap<>();
        if(CollectionUtils.isNotEmpty(user.getRoles()))
        {
            for (Role selectedRole : user.getRoles())
            {
                stringRoleMap.put(selectedRole.getId(), selectedRole);
            }
        }
        List<Role> storedRoles = roleService.getRoles();
        List<Role> allRoles = new ArrayList<>(storedRoles.size());
        for(Role role : storedRoles)
        {
            if (stringRoleMap.get(role.getId()) == null)
            {
                role.setSelected(false);
                allRoles.add(role);
            }
            else
            {
                role.setSelected(true);
                allRoles.add(role);
            }
        }
        if(CollectionUtils.isNotEmpty(user.getRoles()))
        {
            user.getRoles().clear();
        }
        user.setTempPassword(user.getPassword());
        user.setRoles(allRoles);

        model.addAttribute("user", user)
    	.addAttribute("sectionList", sectionService.getAllSection());
        return "profile/user/profile-user-update";
    }

    @Transactional
    @PostMapping(value = "/profile-user-update-save")
    public ModelAndView userUpdateSave(@ModelAttribute User user, @RequestParam String action)
    {
    	System.out.println("pass: " + user.getTempPassword());
        if(logger.isDebugEnabled())
        {
            logger.debug("START - profile-user-update-save");
        }

        ModelAndView modelAndView ;

        if("cancel".equals(action))
        {
            modelAndView = getModelAndViewForCancelFlow();
            return modelAndView;
        }


        try
        {
            String planPassword = user.getPassword();
            if(planPassword != null)
            {
                if(!planPassword.equals(user.getConfirm())) {
                    modelAndView = new ModelAndView("profile/user/profile-user-update");
                    modelAndView.addObject("errorMessage", "Confirm does not patch with Password.");
                    modelAndView.addObject("user", user);
                    return modelAndView;
                }
                user.setPassword(passwordEncoder.encode(planPassword));
            }
            else
            {
                user.setPassword(user.getTempPassword());
            }
            List<Role> selectedRole = new ArrayList<>();
            for(Role role : user.getRoles())
            {
                if (role.isSelected())
                {
                    selectedRole.add(role);
                }
            }
            user.getRoles().clear();
            if(CollectionUtils.isNotEmpty(selectedRole)) {
                user.setRoles(selectedRole);
            }
            userService.updateUser(user);
            String message = "User '" + user.getUsername() + "' successfully updated";
            modelAndView = getModelAndViewForCancelFlow();
            modelAndView.addObject("successMessage", message);
        }
        catch (Exception e)
        {
            Map<String,String> params=new HashMap<>();
            params.put("id", String.valueOf(user.getId()));
            modelAndView = new ModelAndView("profile/user/profile-user-update", params);
            modelAndView.addObject("user", user);
                modelAndView.addObject("errorMessage", e.getMessage());
            logger.error(e.getMessage(), e);
        }

        if(logger.isDebugEnabled())
        {
            logger.debug("END - profile-user-update-save");
        }

        return modelAndView;
    }

    

    @GetMapping("/profile-user-delete.html")
    public String userDelete(Model model, @RequestParam(value = "id") String id, RedirectAttributes redirectAttributes)
    {

        if(StringUtils.isBlank(id))
        {
            model.addAttribute("errorMessage", "Invalid User Id. Please provide proper User Id to delete.");
            populateUsers(model);
            return "profile/user/profile-user";
        }
        Long idLong;
        try
        {
            idLong = Long.valueOf(id);
        }
        catch (NumberFormatException e)
        {
            model.addAttribute("errorMessage", "Invalid User Id. Please provide proper User Id to delete.");
            populateUsers(model);
            return "profile/user/profile-user";
        }
        try {
            User userBeforeDelete = userService.getUser(idLong);
            userService.deleteUser(idLong);
            populateUsers(model);
            //model.addAttribute("successMessage", "Successfully deleted User.");
            redirectAttributes.addFlashAttribute("successMessage", "Successfully deleted User " + userBeforeDelete.getUsername());
            return "redirect:/profile-user.html";
        }
        catch (NoDataFoundException e)
        {
            populateUsers(model);
            model.addAttribute("errorMessage", e.getMessage());
            return "profile/user/profile-user";
        }
    }
    
    
    
    private ModelAndView getModelAndViewForCancelFlow() {
        ModelAndView modelAndView =  new ModelAndView("redirect:/profile-user.html");
        //ModelAndView modelAndView = new ModelAndView("profile/user/profile-user");
        List<User> users = userService.getUsers();
        modelAndView.addObject("users", users);
        return modelAndView;
    }
    
}
