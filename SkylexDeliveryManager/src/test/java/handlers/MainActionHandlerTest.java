package handlers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class MainActionHandlerTest {
    @Test
    public void getters_HappyCase() {
        //arrange, act
        var sut = new MainActionHandler(mock(IDriverActionHandler.class), mock(IVehicleActionHandler.class));

        //assert
        assertNotNull(sut);
        assertNotNull(sut.getDriverActionHandler());
        assertNotNull(sut.getVehicleActionHandler());
    }
}
