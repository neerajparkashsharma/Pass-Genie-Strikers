package com.project.passgenie;

import com.project.passgenie.entity.User;
import com.project.passgenie.repository.UserRepository;
import com.project.passgenie.service.UserDetailsImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class UserDetailsImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserDetailsImpl userDetailsService;



    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void loadUserByUsername_ExistingUser_ReturnsUserDetails() {
        // Arrange
        User user = new User();
        user.setUserName("testuser");
        user.setPassword("testpassword");
        when(userRepository.findByUserName(anyString())).thenReturn(user);

        // Act
        UserDetails userDetails = userDetailsService.loadUserByUsername("testuser");

        // Assert
        assertNotNull(userDetails);
        assertEquals("testuser", userDetails.getUsername());
        assertEquals("testpassword", userDetails.getPassword());
        assertTrue(userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_USER")));
    }

    @Test
    void loadUserByUsername_NonExistingUser_ThrowsException() {
        // Arrange
        when(userRepository.findByUserName(anyString())).thenReturn(null);

        // Act & Assert
        assertThrows(UsernameNotFoundException.class, () -> userDetailsService.loadUserByUsername("testuser"));
    }

    @Test
    void findByUsername_ExistingUsername_ReturnsTrue() {
        // Arrange
        when(userRepository.findByUserName(anyString())).thenReturn(new User());

        // Act
        boolean result = userDetailsService.findByUsername("testuser");

        // Assert
        assertTrue(result);
    }

    @Test
    void findByUsername_NonExistingUsername_ReturnsFalse() {
        // Arrange
        when(userRepository.findByUserName(anyString())).thenReturn(null);

        // Act
        boolean result = userDetailsService.findByUsername("testuser");

        // Assert
        assertFalse(result);
    }

    @Test
    void findByEmail_ExistingEmail_ReturnsTrue() {
        // Arrange
        when(userRepository.existsByEmailAddress(anyString())).thenReturn(true);

        // Act
        boolean result = userDetailsService.findByEmail("test@example.com");

        // Assert
        assertTrue(result);
    }

    @Test
    void findByEmail_NonExistingEmail_ReturnsFalse() {
        // Arrange
        when(userRepository.existsByEmailAddress(anyString())).thenReturn(false);

        // Act
        boolean result = userDetailsService.findByEmail("test@example.com");

        // Assert
        assertFalse(result);
    }






}
