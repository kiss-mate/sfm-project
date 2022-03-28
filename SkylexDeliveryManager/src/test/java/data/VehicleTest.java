package data;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class VehicleTest {
    @Test
    public void new_HappyCase() {
        //arrange, act and assert
        assertNotNull(new Vehicle());
    }

    @Test
    public void setGet_plateNumber() {
        //arrange
        var expectedValue1 = "54";
        var vehicle = new Vehicle();

        //act
        vehicle.setPlateNumber(expectedValue1);
        //assert
        assertEquals(expectedValue1,vehicle.getPlateNumber());
    }

    @Test
    public void setGet_inDelivery() {
        //arrange
        var expectedValue1 = true;
        var vehicle = new Vehicle();

        //act
        vehicle.setInDelivery(expectedValue1);
        //assert
        assertEquals(0,vehicle.getId());
        assertEquals(expectedValue1,vehicle.isInDelivery());
    }

    @Test
    public void setGet_maxCapacity() {
        //arrange
        var expectedValue1 = 542;
        var vehicle = new Vehicle();

        //act
        vehicle.setMaxCapacity(expectedValue1);
        //assert
        assertEquals(0,vehicle.getId());
        assertEquals(expectedValue1,vehicle.getMaxCapacity());
    }

    @Test
    public void setGet_currentLoad() {
        //arrange
        var expectedValue1 = 4356;
        var vehicle = new Vehicle();

        //act
        vehicle.setCurrentLoad(expectedValue1);
        //assert
        assertEquals(0,vehicle.getId());
        assertEquals(expectedValue1,vehicle.getCurrentLoad());
    }
}
