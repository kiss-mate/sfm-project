package logic;

import common.exceptions.ArgumentNullException;
import common.exceptions.BusinessException;
import data.Driver;
import models.enums.ErrorCodes;
import data.Vehicle;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;
import org.mockito.junit.jupiter.MockitoExtension;
import repository.IDriverRepository;
import repository.IPackageRepository;
import repository.IVehicleRepository;

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
                mock(IDriverRepository.class),
                mock(IVehicleRepository.class),
                mock(IPackageRepository.class)));
    }

    @Test
    public void new_LoggerNull() {
        //arrange, act
        var exception = assertThrows(
                ArgumentNullException.class,
                () -> new Logic(
                        null,
                        mock(IDriverRepository.class),
                        mock(IVehicleRepository.class),
                        mock(IPackageRepository.class)));

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
                        null,
                        mock(IVehicleRepository.class),
                        mock(IPackageRepository.class)));

        //assert
        assertEquals("driverRepo", exception.getMessage());
    }

    @Test
    public void new_VehicleRepoNull() {
        //arrange, act
        var exception = assertThrows(
                ArgumentNullException.class,
                () -> new Logic(
                        mock(Logger.class),
                        mock(IDriverRepository.class),
                        null,
                        mock(IPackageRepository.class)));

        //assert
        assertEquals("vehicleRepo", exception.getMessage());
    }

    @Test
    public void new_PackageRepoNull() {
        //arrange, act
        var exception = assertThrows(
                ArgumentNullException.class,
                () -> new Logic(
                        mock(Logger.class),
                        mock(IDriverRepository.class),
                        mock(IVehicleRepository.class),
                        null));

        //assert
        assertEquals("packageRepo", exception.getMessage());
    }

    //region DRIVER RELATED LOGIC TESTS

    @Test
    public void addDriver_HappyCase() {
        //arrange
        ArgumentCaptor<Driver> argument = ArgumentCaptor.forClass(Driver.class);
        var driverName = "driver_name";
        var mockLog = mock(Logger.class);
        var mockDriverRepo = mock(IDriverRepository.class);
        var mockVehicleRepo = mock(IVehicleRepository.class);
        var mockPackageRepo = mock(IPackageRepository.class);
        var logic = new Logic(mockLog, mockDriverRepo, mockVehicleRepo, mockPackageRepo);
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
        var logic = new Logic(mock(Logger.class), mock(IDriverRepository.class), mock(IVehicleRepository.class), mock(IPackageRepository.class));

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
        var mockVehicleRepo = mock(IVehicleRepository.class);
        var mockPackageRepo = mock(IPackageRepository.class);
        when(mockDriverRepo.getById(driverId)).thenReturn(driver);

        var logic = new Logic(mockLog, mockDriverRepo, mockVehicleRepo, mockPackageRepo);
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
        var logic = new Logic(mock(Logger.class), mock(IDriverRepository.class), mock(IVehicleRepository.class), mock(IPackageRepository.class));

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

        var logic = new Logic(mock(Logger.class), mockDriverRepo, mock(IVehicleRepository.class), mock(IPackageRepository.class));
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
        var logic = new Logic(mock(Logger.class), mockDriverRepo, mock(IVehicleRepository.class), mock(IPackageRepository.class));

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
        var logic = new Logic(mock(Logger.class), mockDriverRepo, mock(IVehicleRepository.class), mock(IPackageRepository.class));

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
        var mockVehicleRepo = mock(IVehicleRepository.class);
        var logic = new Logic(mock(Logger.class), mockDriverRepo, mockVehicleRepo, mock(IPackageRepository.class));

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
        when(mockDriverRepo.delete(any(Driver.class))).thenReturn(true);
        var mockLog = mock(Logger.class);

        var logic = new Logic(mockLog, mockDriverRepo, mock(IVehicleRepository.class), mock(IPackageRepository.class));

        //act
        logic.deleteDriver(123);

        //assert
        verify(mockDriverRepo, times(1)).delete(driver);
        verify(mockLog, times(1)).log(eq(Level.INFO), contains("Removed Driver object from the database:"));
    }

    @Test
    public void deleteDriver_NotFound() {
        //arrange
        var mockDriverRepo = mock(IDriverRepository.class);
        when(mockDriverRepo.getById(anyInt())).thenReturn(null);
        var logic = new Logic(mock(Logger.class), mockDriverRepo, mock(IVehicleRepository.class), mock(IPackageRepository.class));

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

    //endregion


    //region VEHICLE RELATED LOGIC TESTS

    @Test
    public void addVehicle_HappyCase() {
        //arrange
        ArgumentCaptor<Vehicle> argument = ArgumentCaptor.forClass(Vehicle.class);
        var vehiclePlateNumber = "5HG58F";
        var mockLog = mock(Logger.class);
        var mockDriverRepo = mock(IDriverRepository.class);
        var mockVehicleRepo = mock(IVehicleRepository.class);
        var logic = new Logic(mockLog, mockDriverRepo, mockVehicleRepo, mock(IPackageRepository.class));
        //act
        logic.addVehicle(vehiclePlateNumber, 1);

        //assert
        verify(mockVehicleRepo, times(1)).insert(argument.capture());
        assertEquals(vehiclePlateNumber, argument.getValue().getPlateNumber());
        assertEquals(1, argument.getValue().getMaxCapacity());
        assertEquals(0, argument.getValue().getCurrentLoad());
        assertFalse(argument.getValue().isInDelivery());
        verify(mockLog, times(1)).log(eq(Level.INFO), contains(vehiclePlateNumber));
    }

    @ParameterizedTest
    @MethodSource("vehicleFailureTestData")
    public void addVehicle_PlateNumberNullOrWhiteSpace(String name, double maxCapacity) {
        //arrange
        var logic = new Logic(mock(Logger.class), mock(IDriverRepository.class), mock(IVehicleRepository.class), mock(IPackageRepository.class));

        //act
        var exception = assertThrows(BusinessException.class, () -> logic.addVehicle(name, maxCapacity));

        //assert
        assertNotNull(exception);
        if (maxCapacity <= 0)
            assertEquals("Capacity is zero or negative", exception.getMessage());
        else assertEquals("Vehicle plate number was null or whitespace", exception.getMessage());
    }


    @Test
    public void changeOneVehicle_InDelivery_HappyCase_1() {
        //arrange
        ArgumentCaptor<Double> argument = ArgumentCaptor.forClass(Double.class);
        var plateNumber = "ABC123";
        var vehicleId = 123;
        var vehicle = new Vehicle();
        vehicle.setPlateNumber(plateNumber);
        vehicle.setCurrentLoad(100);
        vehicle.setMaxCapacity(1000);
        vehicle.setInDelivery(true);

        var mockLog = mock(Logger.class);

        var mockVehicleRepo = mock(IVehicleRepository.class);
        when(mockVehicleRepo.getById(vehicleId)).thenReturn(vehicle);

        var logic = new Logic(mockLog, mock(IDriverRepository.class), mockVehicleRepo, mock(IPackageRepository.class));
        //act
        logic.changeOneVehicle(vehicleId, vehicle.getPlateNumber(), vehicle.getMaxCapacity(), 500, vehicle.isInDelivery());

        //assert
        verify(mockVehicleRepo, times(1)).update(eq(vehicleId), eq(vehicle.getPlateNumber()), eq(true), eq(vehicle.getMaxCapacity()), argument.capture());
        assertEquals(500, argument.getValue());
    }

    @Test
    public void changeOneVehicle_InDelivery_FailureCase() {
        //arrange
        var plateNumber = "ABC123";
        var vehicleId = 123;
        var vehicle = new Vehicle();
        vehicle.setPlateNumber(plateNumber);
        vehicle.setCurrentLoad(100);
        vehicle.setMaxCapacity(1000);
        vehicle.setInDelivery(true);

        var mockLog = mock(Logger.class);

        var mockVehicleRepo = mock(IVehicleRepository.class);
        when(mockVehicleRepo.getById(vehicleId)).thenReturn(vehicle);

        var logic = new Logic(mockLog, mock(IDriverRepository.class), mockVehicleRepo, mock(IPackageRepository.class));
        //act
        var exception = assertThrows(BusinessException.class,
                () ->  logic.changeOneVehicle(vehicleId, "new plate number", vehicle.getMaxCapacity(),100, vehicle.isInDelivery()));     ;

        //assert
        assertNotNull(exception);
        assertTrue(exception.getMessage().contains("Cannot change capacity or plate number of vehicle in delivery"));
    }

    @Test
    public void changeOneVehicle_VehicleNotFound() {
        //arrange
        var mockVehicleRepo = mock(IVehicleRepository.class);

        when(mockVehicleRepo.getById(anyInt())).thenReturn(null);

        var logic = new Logic(mock(Logger.class), mock(IDriverRepository.class), mockVehicleRepo, mock(IPackageRepository.class));

        //act
        var exception = assertThrows(BusinessException.class, () ->
                logic.changeOneVehicle(123, "45GHU5", 20, 0,false));

        //assert
        assertNotNull(exception);
        assertEquals("No such vehicle", exception.getMessage());
    }

    @Test
    public void getOneVehicle_HappyCase() {
        //arrange
        var vehicle = new Vehicle();
        vehicle.setPlateNumber("vehicle_num");

        var mockVehicleRepo = mock(IVehicleRepository.class);

        when(mockVehicleRepo.getById(anyInt())).thenReturn(vehicle);
        var logic = new Logic(mock(Logger.class), mock(IDriverRepository.class), mockVehicleRepo, mock(IPackageRepository.class));

        //act
        var result = logic.getOneVehicle(1);

        //assert
        assertNotNull(result);
        verify(mockVehicleRepo, times(1)).getById(1);
    }

    @Test
    public void getOneVehicle_NotFound() {
        var mockVehicleRepo = mock(IVehicleRepository.class);
        when(mockVehicleRepo.getById(anyInt())).thenReturn(null);
        var logic = new Logic(mock(Logger.class), mock(IDriverRepository.class), mockVehicleRepo, mock(IPackageRepository.class));

        //act
        var result = logic.getOneVehicle(1);

        //assert
        assertNull(result);
        verify(mockVehicleRepo, times(1)).getById(1);
    }

    @Test
    public void getAllVehicles_HappyCase() {
        //arrange
        var mockVehicleRepo = mock(IVehicleRepository.class);
        var logic = new Logic(mock(Logger.class), mock(IDriverRepository.class), mockVehicleRepo, mock(IPackageRepository.class));

        //act
        var result = logic.getAllVehicles();

        //assert
        assertNotNull(result);
        verify(mockVehicleRepo, times(1)).getAll();
    }

    @Test
    public void deleteVehicle_HappyCase() {
        //arrange
        var vehicle = new Vehicle();
        var mockVehicleRepo = mock(IVehicleRepository.class);
        when(mockVehicleRepo.getById(anyInt())).thenReturn(vehicle);
        when(mockVehicleRepo.delete(any(Vehicle.class))).thenReturn(true);
        var mockLog = mock(Logger.class);
        var logic = new Logic(mockLog, mock(IDriverRepository.class), mockVehicleRepo, mock(IPackageRepository.class));

        //act
        logic.deleteVehicle(123);

        //assert
        verify(mockVehicleRepo, times(1)).delete(vehicle);
        verify(mockLog, times(1)).log(eq(Level.INFO), contains("Remove successful"));
    }

    @Test
    public void deleteVehicle_NotFound() {
        //arrange
        var mockDriverRepo = mock(IDriverRepository.class);
        var logic = new Logic(mock(Logger.class), mockDriverRepo, mock(IVehicleRepository.class), mock(IPackageRepository.class));

        //act
        var exception =  assertThrows(BusinessException.class, () -> logic.deleteVehicle(123));

        //assert
        assertNotNull(exception);
        assertEquals("Cannot find vehicle, unable to remove it", exception.getMessage());
    }

    private static Stream<Object[]> vehicleFailureTestData() {
        Object[][] array = {
                {null, 100},
                {"   ", 100},
                {"ABC123", -12},
                {"ABC123", 0}
        };
        return Arrays.stream(array);
    }

    //endregion
}