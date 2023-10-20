package com.tutorial.books.controller.security;

import com.tutorial.books.entity.Role;
import com.tutorial.books.entity.User;
import com.tutorial.books.security.LibraryUserDetails;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.util.Arrays;
import java.util.stream.Collectors;

public class WithMockLibraryUserSecurityContextFactory implements WithSecurityContextFactory<WithMockLibraryUser> {
    @Override
    public SecurityContext createSecurityContext(WithMockLibraryUser annotation) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        var roles = Arrays.stream(annotation.roles())
                .map(roleName -> Role.builder().name(roleName).build())
                .collect(Collectors.toSet());
        var user = User.builder().id(annotation.id()).roles(roles).build();
        var libraryUserDetailsPrincipal = LibraryUserDetails.buildFromUser(user);
        var auth = UsernamePasswordAuthenticationToken
                .authenticated(libraryUserDetailsPrincipal, "abobapass", libraryUserDetailsPrincipal.getAuthorities());
        context.setAuthentication(auth);
        return context;
    }
}
