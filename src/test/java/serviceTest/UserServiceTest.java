package serviceTest;

import entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import repository.UserRepository;
import service.UserService;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void testInsertUser() {
        // Arrange
        User user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");
        user.setUsername("user1");
        user.setRole("user");
        user.setScore(0f);
        user.setPassword("pass123");
        user.setPicture(null);
        user.setPhoneNumber(null);
        user.setIsBanned(false);

        when(userRepository.save(any(User.class))).thenReturn(user);

        // Act
        User result = userService.insertUser(user);


        // Assert
        assertNotNull(result);
        assertEquals("user1", result.getUsername());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testGetAllUsers() {
        // Arrange
        User user1 = new User();
        user1.setId(1L);
        user1.setEmail("test1@example.com");
        user1.setUsername("user1");
        user1.setRole("user");

        User user2 = new User();
        user2.setId(2L);
        user2.setEmail("test2@example.com");
        user2.setUsername("user2");
        user2.setRole("moderator");

        List<User> mockUsers = List.of(user1, user2);
        when(userRepository.findAll()).thenReturn(mockUsers);

        // Act
        List<User> result = userService.getAllUsers();

        // Assert
        assertEquals(2, result.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void testFindUserByIdSuccess() {
        // Arrange
        User mockUser = new User();
        mockUser.setId(1L);
        mockUser.setEmail("test@example.com");
        mockUser.setUsername("user1");

        when(userRepository.findById(1L)).thenReturn(Optional.of(mockUser));

        // Act
        User result = userService.findUserById(1L);

        // Assert
        assertNotNull(result);
        assertEquals("user1", result.getUsername());
    }

    @Test
    void testFindUserByIdNotFound() {
        // Arrange
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            userService.findUserById(99L);
        });
    }

    @Test
    void testDeleteUserSuccess() {
        // Arrange
        doNothing().when(userRepository).deleteById(1L);

        // Act
        String result = userService.deleteUser(1L);

        // Assert
        assertEquals("Deletion Successfully", result);
        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteUserFailure() {
        // Arrange
        doThrow(new RuntimeException()).when(userRepository).deleteById(99L);

        // Act
        String result = userService.deleteUser(99L);

        // Assert
        assertEquals("Failed to delete user with id 99", result);
    }
}
