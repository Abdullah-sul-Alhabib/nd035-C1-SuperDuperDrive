package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.status.StatusCodes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * The type Result controller.
 */
@Controller
@RequestMapping("/result")
public class ResultController {
    /**
     * Get result string.
     *
     * @return the string
     */
    @GetMapping
    public String getResult(@RequestParam("status") int statusCode, Model model){
        model.addAttribute("statusMessage", StatusCodes.getStatusMessageFromNumber(statusCode).getStatusMessage());
        return "result";
    }
}
