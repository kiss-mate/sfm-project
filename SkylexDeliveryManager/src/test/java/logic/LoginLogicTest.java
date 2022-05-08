package logic;

import common.exceptions.ArgumentNullException;
import common.exceptions.BusinessException;
import data.User;
import models.enums.ErrorCodes;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;
import org.mockito.junit.jupiter.MockitoExtension;
import repository.IUserRepository;

import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LoginLogicTest {
    @Test
    public void Constructor_HappyCase() {
        //arrange, act, assert
        assertNotNull(new LoginLogic(mock(Logger.class),mock(IUserRepository.class)));
    }

    @Test
    public void Constructor_LogNull() {
        //arrange, act
        var exception = assertThrows(ArgumentNullException.class, () -> new LoginLogic(null, mock(IUserRepository.class)));
        //assert
        assertEquals("log", exception.getMessage());
    }

    @Test
    public void Constructor_UserRepoNull() {
        //arrange, act
        var exception = assertThrows(ArgumentNullException.class, () -> new LoginLogic(mock(Logger.class), null));
        //assert
        assertEquals("userRepo", exception.getMessage());
    }

    @Test
    public void AddUser_HappyCase() {
        //arrange
        ArgumentCaptor<User> ac = ArgumentCaptor.forClass(User.class);
        String username = "username";
        String password = "password";
        var mockLog = mock(Logger.class);
        var mockUserRepo = mock(IUserRepository.class);
        when(mockUserRepo.insert(any(User.class))).thenReturn(1);
        var loginLogic = new LoginLogic(mockLog, mockUserRepo);

        //act
        loginLogic.addUser(username,password, true);

        //assert
        verify(mockUserRepo, times(1)).insert(ac.capture());
        assertEquals(username, ac.getValue().getUsername());
        assertEquals(password, ac.getValue().getPassword());
        verify(mockLog, times(1)).log(eq(Level.INFO),contains(username));
    }

    @ParameterizedTest
    @MethodSource("validationFailureCases")
    public void AddUser_ValidationsFail(String username, String pwd) {
        //arrange
        var loginLogic = new LoginLogic(mock(Logger.class), mock(IUserRepository.class));

        //act
        var exception = assertThrows(BusinessException.class, () -> loginLogic.addUser(username,pwd, true));

        //assert
        assertNotNull(exception);
        assertTrue(exception.getMessage().contains("is null or whitespace"));
    }

    @Test
    public void ChangeUser_HappyCase() {
        //arrange
        String username = "username";
        String password = "password";
        int userId = 10;
        var user = new User();
        user.setUsername(username);
        user.setPassword(password);

        var mockLog = mock(Logger.class);
        var mockUserRepo = mock(IUserRepository.class);
        when(mockUserRepo.getById(userId)).thenReturn(user);

        var loginLogic = new LoginLogic(mockLog, mockUserRepo);

        //act
        loginLogic.changeOneUser(userId, username, password, true);

        //assert
        verify(mockUserRepo, times(1)).update(eq(userId),eq(username),eq(password), eq(true));
        verify(mockLog, times(1)).log(eq(Level.INFO),contains("User updated: "));
    }

    @ParameterizedTest
    @MethodSource("validationFailureCases")
    public void ChangeUser_ValidationsFail(String username, String pwd) {
        //arrange
        var loginLogic = new LoginLogic(mock(Logger.class), mock(IUserRepository.class));

        //act
        var exception = assertThrows(BusinessException.class, () -> loginLogic.changeOneUser(1,username,pwd, true));

        //assert
        assertNotNull(exception);
        assertTrue(exception.getMessage().contains("is null or whitespace"));
    }

    @Test
    public void ChangeUser_UserNotFound() {
        //arrange
        String username = "username";
        String password = "password";
        int userId = 10;

        var mockLog = mock(Logger.class);
        var mockUserRepo = mock(IUserRepository.class);
        when(mockUserRepo.getById(userId)).thenReturn(null);

        var loginLogic = new LoginLogic(mockLog, mockUserRepo);

        //act
        var exception = assertThrows(BusinessException.class, () -> loginLogic.changeOneUser(userId, username, password, true));

        //assert
        assertNotNull(exception);
        assertEquals("User not found in database", exception.getMessage());
        assertEquals(ErrorCodes.NOT_FOUND_IN_DB, exception.getErrorCode());
    }

    @Test
    public void getOneUser_HappyCase() {
        //arrange
        var user = new User();
        var mockUserRepo = mock(IUserRepository.class);
        when(mockUserRepo.getById(anyInt())).thenReturn(user);

        var loginLogic = new LoginLogic(mock(Logger.class), mockUserRepo);

        //act
        loginLogic.getOneUser(1234);

        //assert
        verify(mockUserRepo, times(1)).getById(1234);
    }

    @Test
    public void getOneUser_NotFound() {
        //arrange
        var mockUserRepo = mock(IUserRepository.class);
        when(mockUserRepo.getById(anyInt())).thenReturn(null);

        var loginLogic = new LoginLogic(mock(Logger.class), mockUserRepo);

        //act
        var result = loginLogic.getOneUser(1234);

        //assert
        assertNull(result);
    }

    @Test
    public void getAllUsers_HappyCase() {
        //arrange
        var mockUserRepo = mock(IUserRepository.class);
        var loginLogic = new LoginLogic(mock(Logger.class), mockUserRepo);

        //act
        var result = loginLogic.getAllUsers();

        //assert
        assertNotNull(result);
    }

    @Test
    public void deleteUser_HappyCase() {
        //arrange
        var user = new User();
        var mockLog = mock(Logger.class);
        var mockUserRepo = mock(IUserRepository.class);
        when(mockUserRepo.getById(anyInt())).thenReturn(user);
        when(mockUserRepo.delete(user)).thenReturn(true);

        var loginLogic = new LoginLogic(mockLog, mockUserRepo);

        //act
        loginLogic.deleteUser(1234);

        //assert
        verify(mockUserRepo, times(1)).delete(user);
        verify(mockLog, times(1)).log(eq(Level.INFO), contains("Removed User object from database: "));
        verify(mockLog, times(0)).log(eq(Level.WARNING), anyString());
    }

    @Test
    public void deleteUser_NotFound() {
        //arrange
        var mockUserRepo = mock(IUserRepository.class);
        when(mockUserRepo.getById(anyInt())).thenReturn(null);

        var loginLogic = new LoginLogic(mock(Logger.class), mockUserRepo);

        //act
        var exception = assertThrows(BusinessException.class, () -> loginLogic.deleteUser(1234));

        //assert
        assertNotNull(exception);
        assertEquals(ErrorCodes.NOT_FOUND_IN_DB, exception.getErrorCode());
    }

    @Test
    public void deleteUser_InnerError() {
        //arrange
        var user = new User();
        var mockLog = mock(Logger.class);
        var mockUserRepo = mock(IUserRepository.class);
        when(mockUserRepo.getById(anyInt())).thenReturn(user);
        when(mockUserRepo.delete(user)).thenReturn(false);

        var loginLogic = new LoginLogic(mockLog, mockUserRepo);

        //act
        loginLogic.deleteUser(1234);

        //assert
        verify(mockUserRepo, times(1)).delete(user);
        verify(mockLog, times(0)).log(eq(Level.INFO), anyString());
        verify(mockLog, times(1)).log(eq(Level.WARNING), contains("Could not remove User object from database:"));
    }

    private static Stream<String[]> validationFailureCases() {
        String[][] stream = {
                {"","valid"},
                {null,"valid"},
                {"   ", "valid"},
                {"valid",""},
                {"valid",null},
                {"valid","   "},
                {"",""}
        };
        return Arrays.stream(stream);
    }
}
