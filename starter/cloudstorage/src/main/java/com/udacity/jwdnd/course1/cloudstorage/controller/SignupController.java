package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * The type Signup controller.
 */
@Controller
@RequestMapping("/signup")
public class SignupController {

    private final UserService userService;

    /**
     * Instantiates a new Signup controller.
     *
     * @param userService the user service bean
     */
    public SignupController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Signup view GET request mapping.
     *
     * @return the signup page string
     */
    @GetMapping()
    public String signupView() {
        return "signup";
    }

    /**
     * Signup view POST mapping.
     *
     * @param user  the user
     * @param model the model
     * @return the signup page string
     */
    @PostMapping()
    public String signupUser(@ModelAttribute User user, Model model) {
        String signupError = null;

        if (!userService.isUsernameAvailable(user.getUsername())) {
            signupError = "The username already exists.";
        }

        if (signupError == null) {
            int rowsAdded = userService.createUser(user);
            if (rowsAdded < 0) {
                signupError = "There was an error signing you up. Please try again.";
            }
        }

        if (signupError == null) {
            model.addAttribute("signupSuccess", true);
            return "redirect:/login";
        } else {
            model.addAttribute("signupError", signupError);
            return "signup";
        }


    }
}
