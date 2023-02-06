package user;

import client.Extract;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AuthorizationTest {

    private ValidatableResponse response;
    private int responseCode;

    private final User user = User.uniqueUser();
    private final UserClient userClient = new UserClient();
    private final Extract extract = new Extract();

    @Test
    @DisplayName("Check authorization of unique user")
    public void checkAuthorization() throws InterruptedException {

        response = userClient.register(user);

        ValidatableResponse authorization = userClient.authorization(user);
        responseCode = extract.responseCode(authorization);
        Boolean responseMessage = extract.success(response);

        String token = extract.accessToken(response);
        userClient.delete(token);

        assertEquals(200, responseCode);
        assertEquals(true, responseMessage);
    }

    @Test
    @DisplayName("Check authorization of nonexistent user")
    public void checkAuthorizationNonexistentUser() throws InterruptedException {

        ValidatableResponse authorization = userClient.authorization(user);
        responseCode = extract.responseCode(authorization);
        String responseMessage = extract.message(authorization);

        assertEquals(401, responseCode);
        assertEquals("email or password are incorrect", responseMessage);
    }
}
