package com.project.passgenie;

import com.project.passgenie.entity.Password;
import com.project.passgenie.repository.PasswordRepository;
import com.project.passgenie.service.PasswordService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PasswordServiceTest {

    @Mock
    private PasswordRepository passwordRepository;

    @InjectMocks
    private PasswordService passwordService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createPassword_ValidPassword_ReturnsCreatedPassword() {
        // Arrange
        Password password = new Password();
        password.setWebsite("example.com");
        password.setUserName("testuser");
        password.setPassword("password123");
        password.setNotes("This is a test password");
        password.setIsActive(true);
        password.setCreatedOn(new Date(System.currentTimeMillis()));

        when(passwordRepository.save(password)).thenReturn(password);

        // Act
        Password createdPassword = passwordService.createPassword(password);

        // Assert
        assertNotNull(createdPassword);
        assertEquals(password, createdPassword);
        verify(passwordRepository, times(1)).save(password);
    }

    @Test
    void getAllPasswordsByUserId_ValidUserId_ReturnsListOfPasswords() {
        // Arrange
        Long userId = 1L;
        List<Password> passwords = new ArrayList<>();
        passwords.add(new Password());
        passwords.add(new Password());
        when(passwordRepository.findAllByUserIdAndIsActiveTrue(userId)).thenReturn(passwords);

        // Act
        List<Password> retrievedPasswords = passwordService.getAllPasswordsByUserId(userId);

        // Assert
        assertNotNull(retrievedPasswords);
        assertEquals(passwords.size(), retrievedPasswords.size());
        assertEquals(passwords, retrievedPasswords);
        verify(passwordRepository, times(1)).findAllByUserIdAndIsActiveTrue(userId);
    }

    @Test
    void getPasswordById_ValidPasswordId_ReturnsPassword() {
        // Arrange
        Long passwordId = 1L;
        Password password = new Password();
        password.setId(passwordId);
        password.setIsActive(true);
        Optional<Password> optionalPassword = Optional.of(password);

        when(passwordRepository.findByIdAndIsActiveTrue(passwordId)).thenReturn(optionalPassword);

        // Act
        Password retrievedPassword = passwordService.getPasswordById(passwordId);

        // Assert
        assertNotNull(retrievedPassword);
        assertEquals(password, retrievedPassword);
        verify(passwordRepository, times(1)).findByIdAndIsActiveTrue(passwordId);
    }

    @Test
    void getPasswordById_PasswordNotFound_ThrowsRuntimeException() {
        // Arrange
        Long passwordId = 1L;
        Optional<Password> optionalPassword = Optional.empty();

        when(passwordRepository.findByIdAndIsActiveTrue(passwordId)).thenReturn(optionalPassword);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> passwordService.getPasswordById(passwordId));
        verify(passwordRepository, times(1)).findByIdAndIsActiveTrue(passwordId);
    }

    @Test
    void updatePassword_ValidPasswordIdAndPassword_ReturnsUpdatedPassword() {
        // Arrange
        Long passwordId = 1L;
        Password existingPassword = new Password();
        existingPassword.setId(passwordId);
        existingPassword.setWebsite("example.com");
        existingPassword.setUserName("testuser");
        existingPassword.setPassword("password123");
        existingPassword.setNotes("This is a test password");
        existingPassword.setIsActive(true);
        existingPassword.setCreatedOn(new Date(System.currentTimeMillis()));

        Password updatedPassword = new Password();
        updatedPassword.setWebsite("updated.com");
        updatedPassword.setUserName("updateduser");
        updatedPassword.setPassword("updated123");
        updatedPassword.setNotes("This is an updated password");

        when(passwordRepository.save(existingPassword)).thenReturn(existingPassword);
        when(passwordRepository.findByIdAndIsActiveTrue(passwordId)).thenReturn(Optional.of(existingPassword));

        // Act
        Password result = passwordService.updatePassword(passwordId, updatedPassword);

        // Assert
        assertNotNull(result);
        assertEquals(updatedPassword.getWebsite(), result.getWebsite());
        assertEquals(updatedPassword.getUserName(), result.getUserName());
        assertEquals(updatedPassword.getPassword(), result.getPassword());
        assertEquals(updatedPassword.getNotes(), result.getNotes());
        verify(passwordRepository, times(1)).save(existingPassword);
        verify(passwordRepository, times(1)).findByIdAndIsActiveTrue(passwordId);
    }

    @Test
    void deletePassword_ValidPasswordIdAndUserId_PasswordIsDeleted() {
        // Arrange
        Long passwordId = 1L;
        Long userId = 1L;
        Password existingPassword = new Password();
        existingPassword.setId(passwordId);
        existingPassword.setUserId(userId);
        existingPassword.setIsActive(true);
        when(passwordRepository.findByIdAndIsActiveTrue(passwordId)).thenReturn(Optional.of(existingPassword));

        // Act
        passwordService.deletePassword(passwordId, userId);

        // Assert
        assertFalse(existingPassword.getIsActive());
        verify(passwordRepository, times(1)).findByIdAndIsActiveTrue(passwordId);
        verify(passwordRepository, times(1)).save(existingPassword);
    }

    @Test
    void deletePassword_PasswordNotFound_ThrowsRuntimeException() {
        // Arrange
        Long passwordId = 1L;
        Long userId = 1L;
        Optional<Password> optionalPassword = Optional.empty();
        when(passwordRepository.findByIdAndIsActiveTrue(passwordId)).thenReturn(optionalPassword);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> passwordService.deletePassword(passwordId, userId));
        verify(passwordRepository, times(1)).findByIdAndIsActiveTrue(passwordId);
        verify(passwordRepository, never()).save(any());
    }
}
