package user;

import client.ExtractResponse;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RegisterUserTest {

    private ValidatableResponse response;

    private final UserClient userClient = new UserClient();
    private final ExtractResponse extractResponse = new ExtractResponse();
    private final User user = User.uniqueUser();

    @Before
    public void registerUser() throws InterruptedException {
        response = userClient.register(user);
    }

    @Test
    @DisplayName("Check registration of unique user")
    public void checkRegistration() {

        int responseCode = extractResponse.responseCode(response);
        Boolean responseMessage = extractResponse.valueByKey(response, "success");

        assertEquals(200, responseCode);
        assertEquals(true, responseMessage);
    }

    @Test
    @DisplayName("Check registration of existing user")
    public void checkRegistrationExistingUser() throws InterruptedException {

        ValidatableResponse registerExistingUser = userClient.register(user);

        int responseCode = extractResponse.responseCode(registerExistingUser);
        String responseMessage = extractResponse.valueByKey(registerExistingUser, "message");

        assertEquals(403, responseCode);
        assertEquals("User already exists", responseMessage);
    }

    @After
    public void deleteUser() throws InterruptedException {
        String token = extractResponse.valueByKey(response, "accessToken");
        userClient.delete(token);
    }
}
