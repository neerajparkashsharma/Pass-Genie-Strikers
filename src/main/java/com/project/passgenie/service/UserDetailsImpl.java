package com.project.passgenie.service;

import com.project.passgenie.entity.User;
import com.project.passgenie.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.Collections;

@Service
public class UserDetailsImpl implements UserDetailsService {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bcryptEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(),true,true,true,true, Collections.singleton(getAuthority(user)));
    }
    private SimpleGrantedAuthority getAuthority(User user) {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + "USER");
        return authority;
    }

    public boolean findByUsername(String username) {
        try {
            User u= userRepository.findByUserName(username);
            if(u!=null)
                return true;
            else
                return false;

        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve user with username " + username, e);
        }
    }

    public boolean findByEmail(String email) {
        try {
            return userRepository.existsByEmailAddress(email);
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve user with email " + email, e);
        }
    }

}
