package com.project.passgenie;

import com.project.passgenie.entity.User;
import com.project.passgenie.repository.UserRepository;
import com.project.passgenie.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder bcryptEncoder;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindByEmail() {
        // Arrange
        String email = "test@example.com";
        when(userRepository.existsByEmailAddress(email)).thenReturn(true);

        // Act
        boolean result = userService.findByEmail(email);

        // Assert
        assertTrue(result);
        verify(userRepository).existsByEmailAddress(email);
    }

    @Test
    void testFindByUsernameAndPassword() {
        // Arrange
        String username = "testuser";
        String password = "password";
        when(userRepository.existsByUserNameAndPassword(username, password)).thenReturn(true);

        // Act
        boolean result = userService.findByUsernameAndPassword(username, password);

        // Assert
        assertTrue(result);
        verify(userRepository).existsByUserNameAndPassword(username, password);
    }

    @Test
    void testFindByEmailAndPassword() {
        // Arrange
        String email = "test@example.com";
        String password = "password";
        when(userRepository.existsByEmailAddressAndPassword(email, password)).thenReturn(true);

        // Act
        boolean result = userService.findByEmailAndPassword(email, password);

        // Assert
        assertTrue(result);
        verify(userRepository).existsByEmailAddressAndPassword(email, password);
    }

    @Test
    void testFindByUsername() {
        // Arrange
        String username = "testuser";
        when(userRepository.findByUserName(username)).thenReturn(new User());

        // Act
        boolean result = userService.findByUsername(username);

        // Assert
        assertTrue(result);
        verify(userRepository).findByUserName(username);
    }

    @Test
    void testCreateUser() {
        // Arrange
        User user = new User();
        user.setUserName("testuser");
        user.setEmailAddress("test@example.com");
        user.setPassword("password");

        when(userRepository.save(user)).thenReturn(user);
        when(userRepository.existsByEmailAddress(anyString())).thenReturn(false);
        when(userRepository.findByUserName(anyString())).thenReturn(null);

        String originalPassword = user.getPassword();
        String encodedPassword = "encodedPassword";
        when(bcryptEncoder.encode(originalPassword)).thenReturn(encodedPassword);

        // Act
        User result = userService.createUser(user);

        // Assert
        assertNotNull(result);
        assertEquals(encodedPassword, result.getPassword());
        verify(userRepository).save(user);
        verify(userRepository).existsByEmailAddress(user.getEmailAddress());
        verify(userRepository).findByUserName(user.getUserName());
        verify(bcryptEncoder).encode(originalPassword);
    }



}
