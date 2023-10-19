package com.tutorial.books.security;

import com.tutorial.books.entity.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Getter
@Setter
public class LibraryUserDetails implements UserDetails {

    private int id;

    private String username;

    private String password;

    private String salt;

    private boolean accountNonExpired;

    private boolean accountNonLocked;

    private boolean credentialsNonExpired;

    private boolean enabled;

    private Collection<? extends GrantedAuthority> authorities;

    private LibraryUserDetails() {
    }

    public static UserDetails buildFromUser(User user) {
        var libraryUserDetails = new LibraryUserDetails();
        libraryUserDetails.setId(user.getId());
        libraryUserDetails.setUsername(user.getUsername());
        libraryUserDetails.setPassword(user.getPassword());
        libraryUserDetails.setAuthorities(user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().label))
                .toList());
        libraryUserDetails.setAccountNonLocked(true);
        libraryUserDetails.setAccountNonExpired(true);
        libraryUserDetails.setCredentialsNonExpired(true);
        libraryUserDetails.setEnabled(true);
        return libraryUserDetails;
    }

}
