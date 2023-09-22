package com.tutorial.books.service.impl;

import com.tutorial.books.repository.UserRepository;
import com.tutorial.books.security.LibraryUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static com.tutorial.books.util.Constants.USERNAME_NOT_FOUND;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepository.getByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException(USERNAME_NOT_FOUND));
        return LibraryUserDetails.buildFromUser(user);
    }
}
