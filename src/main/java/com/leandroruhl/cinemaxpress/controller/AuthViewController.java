package com.leandroruhl.cinemaxpress.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Controller class for managing user login and registration views.
 * <p>
 * This class handles HTTP requests related to user login and registration views. It provides
 * endpoints for displaying the login page and the registration page.
 */
@Controller
class AuthViewController {

    /**
     * Displays the login page.
     *
     * @return The name of the login view template.
     */
    @GetMapping("/login")
    String login() {
        return "login";
    }

    /**
     * Displays the registration page.
     *
     * @param error A flag indicating whether the registration failed.
     * @param model The {@link Model} object containing the view data.
     * @return The name of the registration view template.
     */
    @GetMapping("/register")
    public String showRegistrationForm(@RequestParam(name = "error", required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("errorMessage", "Username is already taken!");
        }
        return "register";
    }
}