package user;

import client.Extract;
import io.qameta.allure.Link;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ExistentEmailUpdateTest {

    private String token;

    private final User user = User.uniqueUser();
    private final UserClient userClient = new UserClient();
    private final Extract extract = new Extract();

    @Before
    public void registerAndGetToken() throws InterruptedException {
        ValidatableResponse response = userClient.register(user);
        token = extract.accessToken(response);
    }

    @Test
    @Link(name = "bug-0001", url = "https://ya.ru")
    @Severity(SeverityLevel.MINOR)
    @DisplayName("Check possibility of updating user using existing email")
    public void checkUserUpdate() throws InterruptedException {

        String json = String.format("{\"email\": \"%s\"}", user.getEmail());

        ValidatableResponse updateUser = userClient.update(json, token);
        int responseCode = extract.responseCode(updateUser);
        String responseMessage = extract.message(updateUser);

        assertEquals(403, responseCode);
        assertEquals("User with such email already exists", responseMessage);
    }

    @After
    public void deleteUser() throws InterruptedException {
        userClient.delete(token);
    }
}
