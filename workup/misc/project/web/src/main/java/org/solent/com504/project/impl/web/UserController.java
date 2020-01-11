package org.solent.com504.project.impl.web;

import java.util.Arrays;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.solent.com504.project.impl.validator.UserValidator;
import org.solent.com504.project.model.user.dto.Role;
import org.solent.com504.project.model.user.dto.User;
import org.solent.com504.project.model.user.service.SecurityService;
import org.solent.com504.project.model.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    final static Logger LOG = LogManager.getLogger(UserController.class);

    {
        LOG.debug("UserController created");
    }

    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserValidator userValidator;

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registration(Model model) {
        model.addAttribute("userForm", new User());

        return "registration";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registration(@ModelAttribute("userForm") User userForm, BindingResult bindingResult, Model model) {
        userValidator.validate(userForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return "registration";
        }

        userService.create(userForm);

        securityService.autologin(userForm.getUsername(), userForm.getPasswordConfirm());

        return "redirect:/welcome";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model, String error, String logout) {
        if (error != null) {
            model.addAttribute("error", "Your username and password is invalid.");
        }

        if (logout != null) {
            model.addAttribute("message", "You have been logged out successfully.");
        }

        return "login";
    }

    @RequestMapping(value = {"/", "/welcome"}, method = RequestMethod.GET)
    public String welcome(Model model) {
        return "welcome";
    }

    @RequestMapping(value = {"/users"}, method = RequestMethod.GET)
    public String users(Model model) {
        List<User> userList = userService.findAll();

        LOG.debug("users called:");
        for (User user : userList) {
            LOG.debug(" user:" + user);
        }

        model.addAttribute("userListSize", userList.size());
        model.addAttribute("userList", userList);

        return "users";
    }

    @RequestMapping(value = {"/viewModifyUser"}, method = RequestMethod.GET)
    public String modifyuser(Model model,
            @RequestParam(value = "username", required = true) String username) {
        User user = userService.findByUsername(username);

        LOG.debug("viewUser called for username=" + username + " user=" + user);
        model.addAttribute("user", user);
        
        
        List<String> availableRoles= userService.getAvailableUserRoleNames();
        model.addAttribute("availableRoles", availableRoles);
        

        return "viewModifyUser";
    }

    @RequestMapping(value = {"/viewModifyUser"}, method = RequestMethod.POST)
    public String updateuser(Model model,
            @RequestParam(value = "username", required = true) String username,
            @RequestParam(value = "firstName", required = false) String firstName,
            @RequestParam(value = "secondName", required = false) String secondName,
            @RequestParam(value = "roles", required = false) String roles
    ) {
        LOG.debug("updateUser called for username=" + username);
        User user = userService.findByUsername(username);

        if (firstName != null) {
            user.setFirstName(firstName);
        }
        if (secondName != null) {
            user.setSecondName(secondName);
        }
        
        user = userService.save(user);
        
        // update roles
        if(roles != null){
            List<String> roleNames = Arrays.asList(roles.split(","));
            user = userService.updateUserRoles(username, roleNames);
        }

        model.addAttribute("user", user);
        
        List<String> availableRoles= userService.getAvailableUserRoleNames();
        model.addAttribute("availableRoles", availableRoles);
        
        // add message if there are any 
        model.addAttribute("errorMessage", "");
        model.addAttribute("message", "User "+user.getUsername() +" updated successfully");

        return "viewModifyUser";
    }
}
