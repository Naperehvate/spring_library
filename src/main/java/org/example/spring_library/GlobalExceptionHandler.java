package org.example.spring_library;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public String handleArgumentException(IllegalArgumentException ex, Model model)
    {
        model.addAttribute("error", ex.getMessage());
        return "error";
    }
}
