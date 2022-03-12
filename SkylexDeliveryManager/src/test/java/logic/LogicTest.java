package logic;

import common.exceptions.ArgumentNullException;
import data.Sample;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import repository.ISampleRepository;

import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LogicTest {
    @Test
    public void new_HappyCase() {
        //arrange, act, assert
        assertNotNull(new Logic(
                mock(Logger.class),
                mock(ISampleRepository.class)));
    }

    @Test
    public void new_LoggerNull() {
        //arrange, act
        var exception = assertThrows(
                ArgumentNullException.class,
                () -> new Logic(
                    null,
                    mock(ISampleRepository.class)));

        //assert
        assertEquals("log", exception.getMessage());
    }

    @Test
    public void new_SampleRepoNull() {
        //arrange, act
        var exception = assertThrows(
                ArgumentNullException.class,
                () -> new Logic(
                        mock(Logger.class),
                        null));

        //assert
        assertEquals("sampleRepo", exception.getMessage());
    }

    @Test
    public void addOneSample_HappyCase() {
        //assert
        var mockRepo = mock(ISampleRepository.class);
        var mockLog = mock(Logger.class);
        var sampleObject = new Sample();
        sampleObject.setValue1("value1");
        sampleObject.setValue2(1234);
        var logic = new Logic(mockLog, mockRepo);

        //act
        logic.addOneSample(sampleObject.getValue1(),sampleObject.getValue2());

        verify(mockRepo, times(1)).insert(any(Sample.class));
        verify(mockLog, times(1)).log(isA(Level.INFO.getClass()), anyString());
    }
}