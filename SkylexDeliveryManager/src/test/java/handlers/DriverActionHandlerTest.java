package handlers;

import common.constants.ActionSource;
import common.constants.InputFieldKeys;
import common.exceptions.BusinessException;
import data.Driver;
import handlers.classes.DriverActionHandler;
import logic.ILogic;
import models.MainViewDto;
import models.MainViewModel;
import models.enums.ErrorCodes;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DriverActionHandlerTest {
    @Test
    public void handleAction_Add_HappyCase() {
        //arrange
        var testViewDto = new MainViewDto(new MainViewModel(), ActionSource.ADD);
        testViewDto.getMainViewModel().getInputFieldValues().put(InputFieldKeys.DRIVER_NAME_INPUT_FIELD_KEY, "driver_name_to_expect");

        var mockLogic = mock(ILogic.class);
        when(mockLogic.addDriver(eq("driver_name_to_expect"))).thenReturn(new Driver());

        var sut = new DriverActionHandler(mock(Logger.class), mockLogic);

        //act
        var result = sut.handleAction(testViewDto);

        //assert
        assertTrue(result.contains("ADD action was successful."));
    }

    @Test
    public void handleAction_Add_FailureCase() {
        //arrange
        var testViewDto = new MainViewDto(new MainViewModel(), ActionSource.ADD);
        testViewDto.getMainViewModel().getInputFieldValues().put(InputFieldKeys.DRIVER_NAME_INPUT_FIELD_KEY, "");
        var mockLog = mock(Logger.class);
        var mockLogic = mock(ILogic.class);
        when(mockLogic.addDriver("")).thenThrow(new BusinessException("mockException", ErrorCodes.DRIVER_NAME_EMPTY_OR_NULL));
        var sut = new DriverActionHandler(mockLog, mockLogic);

        //act
        var result = sut.handleAction(testViewDto);

        //assert
        assertTrue(result.contains("cannot save this as a name for your driver"));
        verify(mockLog, times(1)).log(eq(Level.WARNING), contains("Business exception occurred"), any(BusinessException.class));
    }

    @Test
    public void handleAction_Remove_HappyCase() {
        //arrange
        var testViewDto = new MainViewDto(new MainViewModel(), ActionSource.REMOVE);
        testViewDto.getMainViewModel().setSelectedDriver(new Driver());

        var mockLogic = mock(ILogic.class);
        when(mockLogic.deleteDriver(anyInt())).thenReturn(true);

        var sut = new DriverActionHandler(mock(Logger.class), mockLogic);

        //act
        var result = sut.handleAction(testViewDto);

        //assert
        assertTrue(result.contains("REMOVE action was successful."));
    }

    @Test
    public void handleAction_Remove_FailureCase() {
        //arrange
        var testViewDto = new MainViewDto(new MainViewModel(), ActionSource.REMOVE);
        testViewDto.getMainViewModel().setSelectedDriver(null);

        var mockLog = mock(Logger.class);
        var sut = new DriverActionHandler(mockLog, mock(ILogic.class));

        //act
        var result = sut.handleAction(testViewDto);

        //assert
        assertTrue(result.contains("Please select a driver to perform the REMOVE action!"));
        verify(mockLog, times(1)).log(eq(Level.WARNING), contains("Business exception occurred"), any(BusinessException.class));
    }

    @Test
    public void handleAction_Update_HappyCase() {
        //arrange
        var testViewDto = new MainViewDto(new MainViewModel(), ActionSource.UPDATE);
        testViewDto.getMainViewModel().getInputFieldValues().put(InputFieldKeys.DRIVER_NAME_INPUT_FIELD_KEY, "valid name");
        testViewDto.getMainViewModel().setSelectedDriver(new Driver());

        var mockLogic = mock(ILogic.class);

        var sut = new DriverActionHandler(mock(Logger.class), mockLogic);

        //act
        var result = sut.handleAction(testViewDto);

        //assert
        assertTrue(result.contains("UPDATE action was successful."));
    }

    @Test
    public void handleAction_Update_FailureCase() {
        //arrange
        var testViewDto = new MainViewDto(new MainViewModel(), ActionSource.UPDATE);
        testViewDto.getMainViewModel().getInputFieldValues().put(InputFieldKeys.DRIVER_NAME_INPUT_FIELD_KEY, "valid name");
        testViewDto.getMainViewModel().setSelectedDriver(null);

        var mockLog = mock(Logger.class);
        var sut = new DriverActionHandler(mockLog, mock(ILogic.class));

        //act
        var result = sut.handleAction(testViewDto);

        //assert
        assertTrue(result.contains("Please select a driver to perform the UPDATE action!"));
        verify(mockLog, times(1)).log(eq(Level.WARNING), contains("Business exception occurred"), any(BusinessException.class));
    }

    @Test
    public void handleAction_NotRecognized() {
        //arrange
        var testViewDto = new MainViewDto(new MainViewModel(), "invalid");
        var mockLog = mock(Logger.class);
        var sut = new DriverActionHandler(mockLog, mock(ILogic.class));

        //act
        var result = sut.handleAction(testViewDto);

        //assert
        assertEquals("Cannot perform this action", result);
    }
}
