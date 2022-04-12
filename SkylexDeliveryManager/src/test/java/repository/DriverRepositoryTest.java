package repository;

import common.DbContextSettings;
import common.exceptions.ArgumentNullException;
import data.DbContext;
import data.Driver;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;

import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class DriverRepositoryTest {
    @Test
    public void New_HappyCase() {
        //arrange, act, assert
        assertNotNull(new DriverRepository(
                mock(Logger.class),
                mock(SessionFactory.class)
        ));
    }

    @Test
    public void New_LogNull() {
        //arrange, act
        var exception = assertThrows(ArgumentNullException.class, () -> new DriverRepository(
                null,
                mock(SessionFactory.class)
        ));

        //assert
        assertNotNull(exception);
        assertEquals("log", exception.getMessage());
    }

    @Test
    public void New_SessionFactoryNull() {
        //arrange, act
        var exception = assertThrows(ArgumentNullException.class, () -> new DriverRepository(
                mock(Logger.class),
                null
        ));

        //assert
        assertNotNull(exception);
        assertEquals("sessionFactory", exception.getMessage());
    }

    @Test
    public void Update_HappyCase() {
        //arrange
        var interceptedSettings = DbContextSettings.contextSettings();
        interceptedSettings.put("hibernate.show_sql", "false");
        interceptedSettings.put("hibernate.connection.url", "jdbc:h2:file:./testDb");

        var oneEntity = new Driver();
        oneEntity.setName("name");

        var mockSf = DbContext.getDbContextInstance().getSessionFactory(interceptedSettings);

        var sut = new DriverRepository(mock(Logger.class), mockSf);
        int id = sut.insert(oneEntity);

        //act
        sut.update(id,"new_name");

        //assert
        assertEquals("new_name", sut.getById(id).getName());
    }
}
