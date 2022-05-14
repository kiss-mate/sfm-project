package repository;

import common.DbContextSettings;
import common.exceptions.ArgumentNullException;
import data.DbContext;
import data.Vehicle;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import repository.classes.VehicleRepository;

import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class VehicleRepositoryTest {

    @Test
    public void Constructor_HappyCase() {
         //arrange, act, assert
         assertNotNull(new VehicleRepository(mock(Logger.class), mock(SessionFactory.class)));
    }

    @Test
    public void Constructor_LogNull() {
        //arrange, act
        var exception = assertThrows(ArgumentNullException.class, () -> new VehicleRepository(null, mock(SessionFactory.class)));

        //assert
        assertNotNull(exception);
        assertEquals("log", exception.getMessage());
    }

    @Test
    public void Constructor_SessionFactorNull() {
        //arrange, act
        var exception = assertThrows(ArgumentNullException.class, () -> new VehicleRepository(mock(Logger.class), null));

        //assert
        assertNotNull(exception);
        assertEquals("sessionFactory", exception.getMessage());
    }

    @Test
    public void update_HappyCase() {
        //arrange
        var interceptedSettings = DbContextSettings.contextSettings();
        interceptedSettings.put("hibernate.show_sql", "false");
        interceptedSettings.put("hibernate.connection.url", "jdbc:h2:file:./testDb");

        var mockSf = DbContext.getDbContextInstance().getSessionFactory(interceptedSettings);

        var vehicle = new Vehicle();
        vehicle.setPlateNumber("ABC123");
        vehicle.setMaxCapacity(1000);
        vehicle.setCurrentLoad(0);
        vehicle.setInDelivery(false);

        var vehicleRepo = new VehicleRepository(mock(Logger.class),mockSf);
        int vehicleId = vehicleRepo.insert(vehicle);

        //act
        vehicleRepo.update(vehicleId, vehicle.getPlateNumber(), true, vehicle.getMaxCapacity(), 300);

        //assert
        assertEquals(300, vehicleRepo.getById(vehicleId).getCurrentLoad());
        assertTrue(vehicleRepo.getById(vehicleId).isInDelivery());
    }

    @Test
    public void update_NotFound() {
        //arrange
        var interceptedSettings = DbContextSettings.contextSettings();
        interceptedSettings.put("hibernate.show_sql", "false");
        interceptedSettings.put("hibernate.connection.url", "jdbc:h2:file:./testDb");

        var mockSf = DbContext.getDbContextInstance().getSessionFactory(interceptedSettings);

        var vehicleRepo = new VehicleRepository(mock(Logger.class),mockSf);

        //act
        var isSuccess = vehicleRepo.update(1234, "ABC123", false, 1000, 0);

        //assert
        assertFalse(isSuccess);
    }
}
