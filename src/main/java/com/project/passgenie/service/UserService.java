package com.project.passgenie.service;

import com.project.passgenie.entity.User;
import com.project.passgenie.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {


    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public boolean findByEmail(String email) {
        try {
            return userRepository.existsByEmailAddress(email);
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve user with email " + email, e);
        }
    }

    public boolean findByUsernameAndPassword(String username, String password) {
        try {
            return userRepository.existsByUserNameAndPassword(username, password);
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve user with username " + username + " and password " + password, e);
        }
    }

    public boolean findByEmailAndPassword(String email, String password) {
        try {
            return userRepository.existsByEmailAddressAndPassword(email, password);
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve user with email " + email + " and password " + password, e);
        }
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


    public User createUser(User user) {
        try {

            user.setIsActive(true);
            user.setCreatedOn(new java.sql.Date(System.currentTimeMillis()));
            user.setUpdatedOn(new java.sql.Date(System.currentTimeMillis()));

            //check for email existence
            if (findByEmail(user.getEmailAddress())) {
                throw new RuntimeException("Email already exists");
            }

            //check for username existence
            if (findByUsername(user.getUserName())) {
                throw new RuntimeException("Username already exists");
            }

            //any other validations
            if (user.getUserName().length() < 5) {
                throw new RuntimeException("Username must be at least 5 characters long");
            }

            if (user.getPassword().length() < 8) {
                throw new RuntimeException("Password must be at least 8 characters long");
            }



            return userRepository.save(user);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create user", e);
        }

    }



}
