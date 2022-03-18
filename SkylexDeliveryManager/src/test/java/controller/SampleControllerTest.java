package controller;

import common.exceptions.ArgumentNullException;
import logic.ILogic;
import org.junit.jupiter.api.Test;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.logging.Level;
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
        when(mockScanner.nextLine()).thenReturn("12345");
        var sut = new SampleController(mockLog, mockLogic, mockScanner);

        //act
        sut.handleFakeAction();

        //assert
        verify(mockLogic, times(1)).addOneSample("12345", 12345);
        verify(mockScanner, times(3)).nextLine();
    }

    @Test
    public void handleFakeAction_InputMismatch() {
        //arrange
        var mockLog = mock(Logger.class);
        var mockScanner = mock(Scanner.class);
        when(mockScanner.nextLine()).thenReturn("fake_input");

        var sut = new SampleController(mockLog, mock(ILogic.class),mockScanner);

        //act
        sut.handleFakeAction();

        //assert
        verify(mockLog, times(1)).log(eq(Level.SEVERE), eq("Bad data input"), isA(NumberFormatException.class));
    }
}