package com.tutorial.books.controller.security;

import com.tutorial.books.entity.Role;
import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithMockLibraryUserSecurityContextFactory.class)
public @interface WithMockLibraryUser {

    int id() default 1;
    Role.Name[] roles() default {};
}
