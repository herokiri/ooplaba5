package ru.dstu.oop.laba5.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.dstu.oop.laba5.entities.User;
import ru.dstu.oop.laba5.services.CarService;
import ru.dstu.oop.laba5.services.UserService;

import java.security.Principal;

@Controller
public class MainController {
    @Autowired
    UserService userService;

    @Autowired
    CarService carService;

    @GetMapping("/")
    public String main(Model model, Principal principal) {
        if (principal != null) {
            model.addAttribute("username", principal.getName());
        } else {
            model.addAttribute("showLogin", true);
        }
        model.addAttribute("cars", carService.getAllCars());
        return "main.html";
    }


    @GetMapping("/login")
    public String loginForm(Model model) {
        return "login.html";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "registration.html";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("user") User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "registration.html";
        }

        // Check if the username or email already exists
        if (userService.existsByUsername(user.getUsername())) {
            bindingResult.rejectValue("username", "error.username", "Username already exists");
            return "registration.html";
        }


        // Save the user
        userService.createNewUser(user);

        return "login.html";
    }
}
