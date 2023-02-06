package user;

import client.Extract;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RegisterUserTest {

    private ValidatableResponse response;

    private final User user = User.uniqueUser();
    private final UserClient userClient = new UserClient();
    private final Extract extract = new Extract();

    @Before
    public void registerUser() throws InterruptedException {
        response = userClient.register(user);
    }

    @Test
    @DisplayName("Check registration of unique user")
    public void checkRegistration() {

        int responseCode = extract.responseCode(response);
        Boolean responseMessage = extract.success(response);

        assertEquals(200, responseCode);
        assertEquals(true, responseMessage);
    }

    @Test
    @DisplayName("Check registration of existing user")
    public void checkRegistrationExistingUser() throws InterruptedException {

        ValidatableResponse registerExistingUser = userClient.register(user);

        int responseCode = extract.responseCode(registerExistingUser);
        String responseMessage = extract.message(registerExistingUser);

        assertEquals(403, responseCode);
        assertEquals("User already exists", responseMessage);
    }

    @After
    public void deleteUser() throws InterruptedException {
        String token = extract.accessToken(response);
        userClient.delete(token);
    }
}
