package data;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class SampleTest {
    @Test
    public void new_HappyCase() {
        //arrange, act and assert
        assertNotNull(new Sample());
    }

    @Test
    public void setGet_HappyCase() {
        //arrange
        var expectedValue1 = "value1_to_expect";
        var expectedValue2 = 1234;
        var sample = new Sample();

        //act
        sample.setValue1(expectedValue1);
        sample.setValue2(expectedValue2);

        //assert
        assertEquals(0,sample.getId());
        assertEquals(expectedValue1,sample.getValue1());
        assertEquals(expectedValue2,sample.getValue2());
    }
}
