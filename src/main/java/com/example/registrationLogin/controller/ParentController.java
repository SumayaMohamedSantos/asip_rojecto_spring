package com.example.registrationLogin.controller;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.registrationLogin.entity.User;
import com.example.registrationLogin.repository.UserRepository;


import java.util.Arrays;
import java.util.List;

@Controller
public class ParentController {

    @Autowired
    private UserRepository userRepository;
   

    // Display home page
    @GetMapping("/")
    public String show() {  
        return "home";
    }
    
    @GetMapping("/admin")
    public String admin() {
    	return "admin";
    }

    // Display registration page
    @GetMapping("/registration")
    public String registrationPage(Model model) {
        model.addAttribute("user", new User());
        return "registrationPage";
    }

    // Display login page
    @GetMapping("/loginPage")
    public String loginPage(Model model) {
        return "loginPage";
    }
    
    @GetMapping("/login")
    public String login(Model model) {
    	return "welcome";
    }

    // Handle registration form submission
    @PostMapping("/register")
    public String register(@ModelAttribute("user") User user, Model model) {
        // Process technology input
        String techInput = user.getTechnologyInput();
        if (techInput != null && !techInput.isEmpty()) {
            List<String> technologies = Arrays.asList(techInput.split("\\s*,\\s*"));
            user.setTechnology(technologies);
        }
        // Save user (password is hashed in User entity)
        userRepository.save(user);
        model.addAttribute("message", "Registration successful!");
        return "redirect:/loginPage";
    }

    // Handle login form submission
    @PostMapping("/login")
    public String loginProcess(@RequestParam("username") String username,
                               @RequestParam("password") String password, Model model) {
        User dbUser = userRepository.findByUsername(username);
        if (dbUser != null) {
            boolean isPasswordMatch = BCrypt.checkpw(password, dbUser.getPassword());
            if (isPasswordMatch) {
                model.addAttribute("username", username);
                return "welcome";
            } else {
                model.addAttribute("error", "Incorrect password.");
                return "loginPage";
            }
        } else {
            model.addAttribute("error", "User not found.");
            return "loginPage";
        }
    }

    // Display welcome page after login
    @GetMapping("/welcome")
    public String welcomePage(@RequestParam("username") String username, Model model) {
        model.addAttribute("username", username);
        return "welcome";
    }
    
    
    

}
