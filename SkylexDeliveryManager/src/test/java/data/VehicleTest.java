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
        var sample = new Vehicle();

        //act
        sample.setPlateNumber(expectedValue1);
        //assert
        assertEquals(expectedValue1,sample.getPlateNumber());
    }

    @Test
    public void setGet_inDelivery() {
        //arrange
        var expectedValue1 = true;
        var sample = new Vehicle();

        //act
        sample.setInDelivery(expectedValue1);
        //assert
        assertEquals(0,sample.getId());
        assertEquals(expectedValue1,sample.isInDelivery());
    }

    @Test
    public void setGet_maxCapacity() {
        //arrange
        var expectedValue1 = 542;
        var sample = new Vehicle();

        //act
        sample.setMaxCapacity(expectedValue1);
        //assert
        assertEquals(0,sample.getId());
        assertEquals(expectedValue1,sample.getMaxCapacity());
    }

    @Test
    public void setGet_currentLoad() {
        //arrange
        var expectedValue1 = 4356;
        var sample = new Vehicle();

        //act
        sample.setCurrentLoad(expectedValue1);
        //assert
        assertEquals(0,sample.getId());
        assertEquals(expectedValue1,sample.getCurrentLoad());
    }
}
