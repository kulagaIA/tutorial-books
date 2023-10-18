package com.tutorial.books.exception;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import java.io.PrintWriter;
import java.io.StringWriter;

@ControllerAdvice
public class ExceptionHandlerControllerAdvice {

    @ExceptionHandler(Exception.class)
    public ModelAndView generalErrorHandler(Exception e) {
        var sw = new StringWriter();
        var pw = new PrintWriter(sw);
        e.printStackTrace(pw);

        var error = Error.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.toString())
                .stacktrace(sw.toString())
                .build();

        var model = new ModelAndView();
        model.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        model.getModelMap().addAttribute(error);
        model.setViewName("error/error");

        return model;
    }

    @ExceptionHandler({AuthenticationException.class, AccessDeniedException.class})
    public ModelAndView authenticationErrorHandler(Exception e) {

        var error = Error.builder()
                .status(HttpStatus.FORBIDDEN.toString())
                .build();

        var model = new ModelAndView();
        model.setStatus(HttpStatus.FORBIDDEN);
        model.getModelMap().addAttribute(error);
        model.setViewName("error/forbidden");

        return model;
    }
}
