package controller;

import common.exceptions.ArgumentNullException;
import logic.ILogic;
import org.junit.jupiter.api.Test;

import java.util.Scanner;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SampleControllerTest {
    @Test
    public void new_HappyCase() {
        //arrange, act, assert
        assertNotNull(new SampleController(
                mock(Logger.class),
                mock(ILogic.class),
                mock(Scanner.class)));
    }

    @Test
    public void new_LogNull() {
        //arrange, act
        var exception = assertThrows(ArgumentNullException.class, () -> new SampleController(
                null,
                mock(ILogic.class),
                mock(Scanner.class)));

        //assert
        assertEquals("log", exception.getMessage());
    }

    @Test
    public void new_LogicNull() {
        //arrange, act
        var exception = assertThrows(ArgumentNullException.class, () -> new SampleController(
                mock(Logger.class),
                null,
                mock(Scanner.class)));

        //assert
        assertEquals("logic", exception.getMessage());
    }

    @Test
    public void new_ScannerNull() {
        //arrange, act
        var exception = assertThrows(ArgumentNullException.class, () -> new SampleController(
                mock(Logger.class),
                mock(ILogic.class),
                null));

        //assert
        assertEquals("scanner", exception.getMessage());
    }

    @Test
    public void handleFakeAction_HappyCase() {
        //arrange
        var mockLog = mock(Logger.class);
        var mockLogic = mock(ILogic.class);
        var mockScanner = mock(Scanner.class);
        when(mockScanner.nextLine()).thenReturn("fake_input");
        when(mockScanner.nextInt()).thenReturn(1234);

        var controller = new SampleController(mockLog, mockLogic, mockScanner);

        //act
        controller.handleFakeAction();

        //assert
        verify(mockLogic, times(1)).addOneSample("fake_input", 1234);
        verify(mockScanner, times(2)).nextLine();
        verify(mockScanner, times(1)).nextInt();
    }
}