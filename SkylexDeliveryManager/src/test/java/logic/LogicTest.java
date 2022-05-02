package logic;

import common.exceptions.ArgumentNullException;
import common.exceptions.BusinessException;
import data.Driver;
import models.enums.ErrorCodes;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;
import org.mockito.junit.jupiter.MockitoExtension;
import repository.IDriverRepository;

import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LogicTest {
    @Test
    public void new_HappyCase() {
        //arrange, act, assert
        assertNotNull(new Logic(
                mock(Logger.class),
                mock(IDriverRepository.class)));
    }

    @Test
    public void new_LoggerNull() {
        //arrange, act
        var exception = assertThrows(
                ArgumentNullException.class,
                () -> new Logic(
                        null,
                        mock(IDriverRepository.class)));

        //assert
        assertEquals("log", exception.getMessage());
    }

    @Test
    public void new_DriverRepoNull() {
        //arrange, act
        var exception = assertThrows(
                ArgumentNullException.class,
                () -> new Logic(
                        mock(Logger.class),
                        null));

        //assert
        assertEquals("driverRepo", exception.getMessage());
    }

    @Test
    public void addDriver_HappyCase() {
        //arrange
        ArgumentCaptor<Driver> argument = ArgumentCaptor.forClass(Driver.class);
        var driverName = "driver_name";
        var mockLog = mock(Logger.class);
        var mockDriverRepo = mock(IDriverRepository.class);
        var logic = new Logic(mockLog, mockDriverRepo);
        //act
        logic.addDriver(driverName);

        //assert
        verify(mockDriverRepo, times(1)).insert(argument.capture());
        assertEquals(driverName, argument.getValue().getName());
        verify(mockLog, times(1)).log(eq(Level.INFO), contains(driverName));
    }

    @ParameterizedTest
    @MethodSource("nameTestData")
    public void addDriver_NameNullOrWhiteSpace(String name) {
        //arrange
        var logic = new Logic(mock(Logger.class), mock(IDriverRepository.class));

        //act
        var exception = assertThrows(BusinessException.class, () -> logic.addDriver(name));

        //assert
        assertNotNull(exception);
        assertEquals("Driver name was null or whitespace", exception.getMessage());
    }

    @Test
    public void changeOneDriver_HappyCase() {
        //arrange
        ArgumentCaptor<String> argument = ArgumentCaptor.forClass(String.class);
        var driverName = "driver_name";
        var driverId = 123;
        var driver = new Driver();
        driver.setName(driverName);

        var mockLog = mock(Logger.class);

        var mockDriverRepo = mock(IDriverRepository.class);
        when(mockDriverRepo.getById(driverId)).thenReturn(driver);

        var logic = new Logic(mockLog, mockDriverRepo);
        //act
        logic.changeOneDriver(driverId, "new_driver_name");

        //assert
        verify(mockDriverRepo, times(1)).update(anyInt(), argument.capture());
        assertEquals("new_driver_name", argument.getValue());
        verify(mockLog, times(1)).log(eq(Level.INFO), contains(driverName));
    }

    @ParameterizedTest
    @MethodSource("nameTestData")
    public void changeOneDriver_NameNullOrWhitespace(String name) {
        //arrange
        var logic = new Logic(mock(Logger.class), mock(IDriverRepository.class));

        //act
        var exception = assertThrows(BusinessException.class, () -> logic.changeOneDriver(123, name));

        //assert
        assertNotNull(exception);
        assertEquals("Driver name was null or whitespace", exception.getMessage());
    }

    @Test
    public void changeOneDriver_DriverNotFound() {
        //arrange
        var mockDriverRepo = mock(IDriverRepository.class);
        when(mockDriverRepo.getById(anyInt())).thenReturn(null);

        var logic = new Logic(mock(Logger.class), mockDriverRepo);
        //act
        var exception = assertThrows(BusinessException.class, () -> logic.changeOneDriver(123, "new_driver_name"));

        //assert
        assertNotNull(exception);
        assertEquals("Driver object not found", exception.getMessage());
        assertEquals(ErrorCodes.NOT_FOUND_IN_DB, exception.getErrorCode());
    }

    @Test
    public void getOneDriver_HappyCase() {
        //arrange
        var driver = new Driver();
        driver.setName("driver_name");
        var mockDriverRepo = mock(IDriverRepository.class);
        when(mockDriverRepo.getById(anyInt())).thenReturn(driver);
        var logic = new Logic(mock(Logger.class), mockDriverRepo);

        //act
        var result = logic.getOneDriver(1);

        //assert
        assertNotNull(result);
        verify(mockDriverRepo, times(1)).getById(1);
    }

    @Test
    public void getOneDriver_NotFound() {
        var mockDriverRepo = mock(IDriverRepository.class);
        when(mockDriverRepo.getById(anyInt())).thenReturn(null);
        var logic = new Logic(mock(Logger.class), mockDriverRepo);

        //act
        var result = logic.getOneDriver(1);

        //assert
        assertNull(result);
        verify(mockDriverRepo, times(1)).getById(1);
    }

    @Test
    public void getAllDrivers_HappyCase() {
        //arrange
        var mockDriverRepo = mock(IDriverRepository.class);
        var logic = new Logic(mock(Logger.class), mockDriverRepo);

        //act
        var result = logic.getAllDrivers();

        //assert
        assertNotNull(result);
        verify(mockDriverRepo, times(1)).getAll();
    }

    @Test
    public void deleteDriver_HappyCase() {
        //arrange
        var driver = new Driver();
        var mockDriverRepo = mock(IDriverRepository.class);
        when(mockDriverRepo.getById(anyInt())).thenReturn(driver);
        var mockLog = mock(Logger.class);
        var logic = new Logic(mockLog, mockDriverRepo);

        //act
        logic.deleteDriver(123);

        //assert
        verify(mockDriverRepo, times(1)).delete(driver);
        verify(mockLog, times(1)).log(eq(Level.INFO), contains("Removed Driver object"));
    }

    @Test
    public void deleteDriver_NotFound() {
        //arrange
        var mockDriverRepo = mock(IDriverRepository.class);
        when(mockDriverRepo.getById(anyInt())).thenReturn(null);
        var logic = new Logic(mock(Logger.class), mockDriverRepo);

        //act
        var exception =  assertThrows(BusinessException.class, () -> logic.deleteDriver(123));

        //assert
        assertNotNull(exception);
        assertEquals("Driver object not found", exception.getMessage());
        assertEquals(ErrorCodes.NOT_FOUND_IN_DB, exception.getErrorCode());
    }

    private static Stream<String> nameTestData() {
        return Arrays.stream(new String[]{"", null, "   "});
    }
}