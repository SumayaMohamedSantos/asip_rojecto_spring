package com.example.registrationLogin.controller;

import jakarta.servlet.http.HttpSession;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.registrationLogin.entity.User;
import com.example.registrationLogin.repository.UserRepository;


import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

    @PostMapping("/register")
    public String register(@ModelAttribute("user") User user, Model model) {
        // Processa toda entrada de tecnologia
        String techInput = user.getTechnologyInput();
        if (techInput != null && !techInput.isEmpty()) {
            // Dividir a string de tecnologias, removendo espaços extras
            List<String> technologies = Arrays.stream(techInput.split(","))
                    .map(String::trim)
                    .collect(Collectors.toList());

            user.setTechnology(technologies);
        }

        // Salvar usuário (senha será hashada no método setPassword da entidade)
        userRepository.save(user);

        model.addAttribute("message", "Registration successful!");
        return "redirect:/loginPage";
    }

    @PostMapping("/login")
    public String loginProcess(@RequestParam("username") String username,
                               @RequestParam("password") String password,
                               HttpSession session,
                               Model model) {
        User dbUser = userRepository.findByUsername(username);
        if (dbUser != null) {
            boolean isPasswordMatch = BCrypt.checkpw(password, dbUser.getPassword());
            if (isPasswordMatch) {
                session.setAttribute("user", dbUser);  // Salvar utilisador na sessão
                return "redirect:/welcome";
            } else {
                model.addAttribute("error", "Incorrect password.");
                return "loginPage";
            }
        } else {
            model.addAttribute("error", "User not found.");
            return "loginPage";
        }
    }

    @GetMapping("/welcome")
    public String welcomePage(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            // Recarrega o utilidadr do banco de dados para garantir que a sessão esta ativa
            user = userRepository.findByUsername(user.getUsername());

            model.addAttribute("username", user.getUsername());
            model.addAttribute("name", user.getName());
            model.addAttribute("dob", user.getDob());
            model.addAttribute("technology", user.getTechnology());
        }
        return "welcome";
    }




}
