package repository;

import common.DbContextSettings;
import data.DbContext;
import data.Driver;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import repository.classes.DriverRepository;

import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RepositoryBaseTest {
    @Test
    public void New_HappyCase() {
        //arrange, act, assert
        assertNotNull(new DriverRepository(
                mock(Logger.class),
                mock(SessionFactory.class)));
    }

    @Test
    public void New_LogNull() {
        //arrange, act
        var exception = assertThrows(Exception.class, () -> new DriverRepository(
                null,
                mock(SessionFactory.class)));

        //assert
        assertNotNull(exception);
        assertEquals("log", exception.getMessage());
    }

    @Test
    public void New_SessionFactoryNull() {
        //arrange, act
        var exception = assertThrows(Exception.class, () -> new DriverRepository(
                mock(Logger.class),
                null));

        //assert
        assertNotNull(exception);
        assertEquals("sessionFactory", exception.getMessage());
    }

    @Test
    public void Insert_HappyCase() {
        //arrange
        var interceptedSettings = DbContextSettings.contextSettings();
        interceptedSettings.put("hibernate.show_sql", "false");
        interceptedSettings.put("hibernate.connection.url", "jdbc:h2:file:./testDb");

        var mockSf = DbContext.getDbContextInstance().getSessionFactory(interceptedSettings);

        var sut = new DriverRepository(mock(Logger.class), mockSf);

        var oneEntity = new Driver();
        oneEntity.setName("name");

        //act
        var id = sut.insert(oneEntity);

        //assert
        assertNotNull(sut.getById(id));
    }

    @Test
    public void GetAll_HappyCase() {
        //arrange
        var interceptedSettings = DbContextSettings.contextSettings();
        interceptedSettings.put("hibernate.show_sql", "false");
        interceptedSettings.put("hibernate.connection.url", "jdbc:h2:file:./testDb");

        var mockSf = DbContext.getDbContextInstance().getSessionFactory(interceptedSettings);

        var sut = new DriverRepository(mock(Logger.class), mockSf);

        //act
        var result = sut.getAll();

        //assert
        assertNotNull(result);

    }

    @Test
    public void GetById_HappyCase() {
        //arrange
        var interceptedSettings = DbContextSettings.contextSettings();
        interceptedSettings.put("hibernate.show_sql", "false");
        interceptedSettings.put("hibernate.connection.url", "jdbc:h2:file:./testDb");

        var oneEntity = new Driver();
        oneEntity.setName("abc");

        var mockSf = DbContext.getDbContextInstance().getSessionFactory(interceptedSettings);

        var sut = new DriverRepository(mock(Logger.class), mockSf);
        sut.insert(oneEntity);

        //act
        var result = sut.getById(oneEntity.getId());

        //assert
        assertNotNull(result);
        assertEquals("abc", result.getName());
    }

    @Test
    public void GetById_NotFound() {
        //arrange
        var interceptedSettings = DbContextSettings.contextSettings();
        interceptedSettings.put("hibernate.show_sql", "false");
        interceptedSettings.put("hibernate.connection.url", "jdbc:h2:file:./testDb");

        var mockSf = DbContext.getDbContextInstance().getSessionFactory(interceptedSettings);

        var sut = new DriverRepository(mock(Logger.class), mockSf);

        //act
        var result = sut.getById(0);

        //assert
        assertNull(result);
    }

    @Test
    public void Delete_HappyCase() {
        //arrange
        var interceptedSettings = DbContextSettings.contextSettings();
        interceptedSettings.put("hibernate.show_sql", "false");
        interceptedSettings.put("hibernate.connection.url", "jdbc:h2:file:./testDb");

        var oneEntity = new Driver();
        oneEntity.setName("abc");

        var mockSf = DbContext.getDbContextInstance().getSessionFactory(interceptedSettings);

        var sut = new DriverRepository(mock(Logger.class), mockSf);
        int id = sut.insert(oneEntity);

        //act
        var isSuccess = sut.delete(id);

        //assert
        assertTrue(isSuccess);
        assertNull(sut.getById(id));
    }

    @Test
    public void Delete_NotFound() {
        //arrange
        var interceptedSettings = DbContextSettings.contextSettings();
        interceptedSettings.put("hibernate.show_sql", "false");
        interceptedSettings.put("hibernate.connection.url", "jdbc:h2:file:./testDb");

        var mockSf = DbContext.getDbContextInstance().getSessionFactory(interceptedSettings);

        var sut = new DriverRepository(mock(Logger.class), mockSf);

        //act
        var isSuccess = sut.delete(1000);

        //assert
        assertFalse(isSuccess);
    }
}
