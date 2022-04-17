package data;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class UserTest {
    @Test
    public void setGet_HappyCase() {
        //arrange
        String expectedUserName = "username_to_expect";
        String expectedPwd = "super_strong_password";
        var sut = new User();

        //act
        sut.setUserName(expectedUserName);
        sut.setPassword(expectedPwd);

        //arrange
        assertEquals(expectedUserName, sut.getUserName());
        assertEquals(expectedPwd, sut.getPassword());
        assertEquals(0, sut.getId());
    }
}
