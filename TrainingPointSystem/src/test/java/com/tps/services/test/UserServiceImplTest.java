package com.tps.services.test;

import com.cloudinary.Cloudinary;
import com.cloudinary.Uploader;
import com.tps.pojo.User;
import com.tps.repositories.UserRepository;
import com.tps.services.FirebaseService;
import com.tps.services.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @Mock
    private Cloudinary cloudinary;

    @Mock
    private Uploader uploader;

    @Mock
    private FirebaseService firebaseService;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void LoadUserByUsername_Success() {
        // Arrange
        User mockUser = new User();
        mockUser.setUsername("testuser");
        mockUser.setPassword("encodedPassword");
        mockUser.setRole("STUDENT");

        when(userRepository.getUserByUsername(anyString())).thenReturn(mockUser);

        // Act
        UserDetails userDetails = userService.loadUserByUsername("testuser");

        // Assert
        assertNotNull(userDetails);
        assertEquals(mockUser.getUsername(), userDetails.getUsername());
        assertEquals(mockUser.getPassword(), userDetails.getPassword());
    }

    @Test
    void LoadUserByUsername_UserNotFound() {
        // Arrange
        when(userRepository.getUserByUsername(anyString())).thenReturn(null);

        // Act and Assert
        assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername("nonexistentuser"));
    }

    @Test
    void AddUser_Success() throws IOException {
        // Arrange
        User newUser = new User();
        newUser.setUsername("newuser");
        newUser.setPassword("password");
        newUser.setRole("STUDENT");
        MultipartFile mockFile = null; // mock MultipartFile

        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(cloudinary.uploader()).thenReturn(uploader);
        when(uploader.upload(any(byte[].class), any())).thenReturn(Collections.singletonMap("secure_url", "https://example.com/image.jpg"));
        when(userRepository.addUser(any(User.class))).thenReturn(newUser);
        doNothing().when(firebaseService).addUserToFirestore(any(User.class));

        // Act
        User addedUser = userService.addUser(newUser);

        // Assert
        assertNotNull(addedUser);
        assertEquals("newuser", addedUser.getUsername());
        assertEquals("STUDENT", addedUser.getRole());
        assertEquals("https://example.com/image.jpg", addedUser.getAvatar());
    }

    @Test
    void AddUser_FileUploadError() throws IOException {
        // Arrange
        User newUser = new User();
        newUser.setUsername("newuser");
        newUser.setPassword("password");
        newUser.setRole("STUDENT");
        MultipartFile mockFile = null; // mock MultipartFile

        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(cloudinary.uploader()).thenReturn(uploader);
        when(uploader.upload(any(byte[].class), any())).thenThrow(IOException.class);

        // Act and Assert
        assertThrows(RuntimeException.class, () -> userService.addUser(newUser));
    }

    @Test
    void GetUserByUsername() {
        // Arrange
        User mockUser = new User();
        mockUser.setUsername("testuser");

        when(userRepository.getUserByUsername(anyString())).thenReturn(mockUser);

        // Act
        User retrievedUser = userService.getUserByUsername("testuser");

        // Assert
        assertNotNull(retrievedUser);
        assertEquals("testuser", retrievedUser.getUsername());
    }

    @Test
    void GetUserById() {
        // Arrange
        User mockUser = new User();
        mockUser.setId(1);

        when(userRepository.getUserById(anyInt())).thenReturn(mockUser);

        // Act
        User retrievedUser = userService.getUserById(1);

        // Assert
        assertNotNull(retrievedUser);
        assertEquals(1, retrievedUser.getId());
    }

    @Test
    void AuthUser() {
        // Arrange
        when(userRepository.authUser(anyString(), anyString())).thenReturn(true);

        // Act
        boolean isAuthenticated = userService.authUser("username", "password");

        // Assert
        assertTrue(isAuthenticated);
    }

    @Test
    void UpdateUser() {
        // Arrange
        User userToUpdate = new User();
        userToUpdate.setUsername("testuser");

        // Act
        userService.updateUser(userToUpdate);

        // Assert - Verify repository method called with correct parameter
        // Adjust according to your repository method signature and verification
        verify(userRepository).updateUser(userToUpdate);
    }

    @Test
    void DeleteUser() {
        // Arrange
        User userToDelete = new User();
        userToDelete.setUsername("testuser");

        // Act
        userService.deleteUser(userToDelete);

        verify(userRepository).deleteUser(userToDelete);
    }

    @Test
    void GetAllUsers() {
        // Arrange
        when(userRepository.getAllUsers(anyMap())).thenReturn(Collections.singletonList(new User()));

        // Act
        List<User> users = userService.getAllUsers(Collections.emptyMap());

        // Assert
        assertNotNull(users);
        assertFalse(users.isEmpty());
    }
}