package com.example.registrationLogin.controller;

import jakarta.servlet.http.HttpSession;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.registrationLogin.entity.User;
import com.example.registrationLogin.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ParentController {

    @Autowired
    private UserRepository userRepository;

    // Página inicial
    @GetMapping("/")
    public String show() {
        return "home";
    }

    // Página de admin
    @GetMapping("/admin")
    public String adminPage(Model model) {
        List<User> users = userRepository.findAll().stream()
                .filter(user -> !user.getUsername().equals("admin"))  // Excluindo o admin da lista
                .collect(Collectors.toList());
        model.addAttribute("users", users);
        return "admin"; // Página de administração
    }

    // Página de registro
    @GetMapping("/registration")
    public String registrationPage(Model model) {
        model.addAttribute("user", new User());
        return "registrationPage";  // Formulário de registro
    }

    // Página de login do cliente
    @GetMapping("/loginPage")
    public String loginPage() {
        return "loginPage";  // Página de login
    }

    // Processo de login
    @PostMapping("/login")
    public String loginProcess(@RequestParam("username") String username,
                               @RequestParam("password") String password,
                               HttpSession session,
                               Model model) {
        User dbUser = userRepository.findByUsername(username);
        if (dbUser != null) {
            boolean isPasswordMatch = BCrypt.checkpw(password, dbUser.getPassword());
            if (isPasswordMatch) {
                session.setAttribute("user", dbUser);  // Salva o usuário na sessão
                if ("admin".equals(dbUser.getUsername())) {
                    return "redirect:/admin";  // Se for admin, vai para /admin
                } else {
                    return "redirect:/welcome";  // Se for cliente, vai para /welcome
                }
            } else {
                model.addAttribute("error", "Incorrect password.");
                return "loginPage";
            }
        } else {
            model.addAttribute("error", "User not found.");
            return "loginPage";
        }
    }

    // Página de boas-vindas do cliente
    @GetMapping("/welcome")
    public String welcomePage(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            user = userRepository.findByUsername(user.getUsername());  // Recarrega o usuário da base de dados
            model.addAttribute("username", user.getUsername());
            model.addAttribute("name", user.getName());
            model.addAttribute("dob", user.getDob());
        }
        return "welcome";  // Página de boas-vindas para clientes
    }

    // Página de registro
    @PostMapping("/register")
    public String register(@ModelAttribute("user") User user, Model model) {
        // Processa e salva o usuário (sem tecnologia, como foi solicitado)
        userRepository.save(user);
        model.addAttribute("message", "Registration successful!");
        return "redirect:/loginPage";  // Redireciona para a página de login após o registro
    }

    // Método para deletar o usuário
    @PostMapping("/deleteUser")
    public String deleteUser(@RequestParam("userId") Integer userId) {
        try {
            // Exclui o usuário pelo ID
            userRepository.deleteById(userId);
            return "redirect:/admin";  // Redireciona para a página de admin após excluir
        } catch (Exception e) {
            e.printStackTrace();
            return "error";  // Página de erro se algo der errado
        }
    }
}
