package repository;

import common.DbContextSettings;
import common.exceptions.ArgumentNullException;
import data.DbContext;
import data.User;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class UserRepositoryTest {
    @Test
    public void New_HappyCase() {
        //arrange, act, assert
        assertNotNull(new UserRepository(
                mock(Logger.class),
                mock(SessionFactory.class)
        ));
    }

    @Test
    public void New_LogNull() {
        //arrange, act
        var exception = assertThrows(ArgumentNullException.class, () -> new UserRepository(
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
        var exception = assertThrows(ArgumentNullException.class, () -> new UserRepository(
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

        var oneUser = new User();
        oneUser.setUsername("username");
        oneUser.setPassword("password");

        var mockSf = DbContext.getDbContextInstance().getSessionFactory(interceptedSettings);

        var sut = new UserRepository(mock(Logger.class), mockSf);
        int id = sut.insert(oneUser);

        //act
        sut.update(id,"new_username", "new_password", true);

        //assert
        assertEquals("new_username", sut.getById(id).getUsername());
        assertEquals("new_password", sut.getById(id).getPassword());
    }
}
