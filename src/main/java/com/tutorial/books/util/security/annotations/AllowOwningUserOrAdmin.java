package com.tutorial.books.util.security.annotations;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static com.tutorial.books.util.Constants.ADMIN;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("(principal != null and #id == principal.id) or hasRole('" + ADMIN + "')")
public @interface AllowOwningUserOrAdmin {
}
