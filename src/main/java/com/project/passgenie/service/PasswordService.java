package com.project.passgenie.service;

import com.project.passgenie.entity.Password;
import com.project.passgenie.repository.PasswordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PasswordService {

    private final PasswordRepository passwordRepository;

    @Autowired
    public PasswordService(PasswordRepository passwordRepository) {
        this.passwordRepository = passwordRepository;
    }

    public Password createPassword(Password password) {
         password.setCreatedOn(new Date(System.currentTimeMillis()));
         password.setIsActive(true);
        try {
            return passwordRepository.save(password);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create password", e);
        }
    }

    public List<Password> getAllPasswordsByUserId(Long userId) {
        try {
            return passwordRepository.findAllByUserIdAndIsActiveTrue(userId);
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve passwords for user with id " + userId, e);
        }
    }

    public Password getPasswordById(Long passwordId) {
        Optional<Password> optionalPassword;
        try {
            optionalPassword = passwordRepository.findByIdAndIsActiveTrue(passwordId);
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve password with id " + passwordId, e);
        }

        if (optionalPassword.isEmpty()) {
            throw new RuntimeException("Password not found with id " + passwordId);
        }
        return optionalPassword.get();
    }

    public Password updatePassword(Long passwordId, Password password) {
        Password existingPassword = getPasswordById(passwordId);
        existingPassword.setWebsite(password.getWebsite());
        existingPassword.setUserName(password.getUserName());
        existingPassword.setPassword(password.getPassword());
        existingPassword.setNotes(password.getNotes());
        existingPassword.setUpdatedOn(new Date(System.currentTimeMillis()));
        try {
            return passwordRepository.save(existingPassword);
        } catch (Exception e) {
            throw new RuntimeException("Failed to update password with id " + passwordId, e);
        }
    }

    public void deletePassword(Long passwordId, Long userId) {
        Password existingPassword = getPasswordById(passwordId);
        if (existingPassword.getUserId().equals(userId)) {
            existingPassword.setIsActive(false);
            try {
                passwordRepository.save(existingPassword);
            } catch (Exception e) {
                throw new RuntimeException("Failed to delete password with id " + passwordId, e);
            }
        } else {
            throw new RuntimeException("Password not found with id " + passwordId + " for user " + userId);
        }
    }
}
