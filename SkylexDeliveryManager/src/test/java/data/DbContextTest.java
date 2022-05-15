package data;

import common.DbContextSettings;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class DbContextTest {
    @Test
    public void getSessionFactory_HappyCase() {
        //arrange
        DbContext.getDbContextInstance().setSessionFactory(null); //hard reset for testing

        //act and assert
        assertNotNull(DbContext
                .getDbContextInstance()
                .getSessionFactory(DbContextSettings.contextSettings()));
    }

    @Test
    public void getSessionFactory_FailureCase() {
        //arrange
        var config = new HashMap<String, String>();
        config.put("key", "value");
        DbContext.getDbContextInstance().setSessionFactory(null); //hard reset for testing

        //act
        var exception = assertThrows(Exception.class, () -> DbContext
                .getDbContextInstance()
                .getSessionFactory(config));

        //assert
        assertNotNull(exception);
    }
}
