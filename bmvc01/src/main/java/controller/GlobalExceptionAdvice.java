package controller;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionAdvice {
    @ExceptionHandler(RuntimeException.class)
    public String runtimeExp(RuntimeException exception, Model model) {
        model.addAttribute("ExceptionType", exception.getClass());
        model.addAttribute("message", exception.getMessage());
        return "error";
    }
    
    @ResponseStatus(value=HttpStatus.NOT_FOUND, reason="NullPointException")
    @ExceptionHandler(NullPointerException.class)
    public String npExp(NullPointerException e) {
    	e.printStackTrace();
        return "error";
    }
}
