package user;

import client.ExtractResponse;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.After;

import static org.junit.Assert.assertEquals;

public class AuthorizationTest {

    private String token;
    private ValidatableResponse response;

    private final User user = User.uniqueUser();
    private final UserClient userClient = new UserClient();
    private final ExtractResponse extractResponse = new ExtractResponse();

    @Before
    public void registerUser() throws InterruptedException {
        response = userClient.register(user);
    }

    @Test
    @DisplayName("Check authorization of unique user")
    public void checkAuthorization() throws InterruptedException {

        ValidatableResponse authorization = userClient.authorization(user);
        int responseCode = extractResponse.responseCode(authorization);
        Boolean responseMessage = extractResponse.valueByKey(response, "success");

        token = extractResponse.valueByKey(response, "accessToken");
        userClient.delete(token);

        assertEquals(200, responseCode);
        assertEquals(true, responseMessage);
    }

    @Test
    @DisplayName("Check authorization of existing user")
    public void checkRegistrationExistingUser() throws InterruptedException {

        token = extractResponse.valueByKey(response, "accessToken");
        userClient.delete(token);

        ValidatableResponse authorization = userClient.authorization(user);

        int responseCode = extractResponse.responseCode(authorization);
        String responseMessage = extractResponse.valueByKey(authorization, "message");

        assertEquals(401, responseCode);
        assertEquals("email or password are incorrect", responseMessage);
    }

    @After
    public void deleteUser() throws InterruptedException {
        userClient.delete(token);
    }
}
