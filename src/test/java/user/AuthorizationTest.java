package user;

import client.ExtractResponse;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AuthorizationTest {

    private ValidatableResponse response;

    private final UserClient userClient = new UserClient();
    private final ExtractResponse extractResponse = new ExtractResponse();
    private final User user = User.uniqueUser();

    @Before
    public void registerUser() throws InterruptedException {
        response = userClient.register(user);
    }

    @Test
    @DisplayName("Check authorization of unique user")
    public void checkAuthorization() throws InterruptedException {

        ValidatableResponse authorization = userClient.authorization(user);
        int responseCode = extractResponse.responseCode(authorization);
        Boolean responseMessage = extractResponse.getBooleanValueByKey(response, "success");

        String token = extractResponse.getStringValueByKey(response, "accessToken");
        userClient.delete(token);

        assertEquals(200, responseCode);
        assertEquals(true, responseMessage);
    }

    @Test
    @DisplayName("Check authorization of existing user")
    public void checkRegistrationExistingUser() throws InterruptedException {

        String token = extractResponse.getStringValueByKey(response, "accessToken");
        userClient.delete(token);

        ValidatableResponse authorization = userClient.authorization(user);

        int responseCode = extractResponse.responseCode(authorization);
        String responseMessage = extractResponse.getStringValueByKey(authorization, "message");

        assertEquals(401, responseCode);
        assertEquals("email or password are incorrect", responseMessage);
    }
}
