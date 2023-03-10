package user;

import client.Extract;

import com.github.javafaker.Faker;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class UpdateUserTest {

    private String token;

    private final String json;

    private final User user = User.uniqueUser();
    private final UserClient userClient = new UserClient();
    private final Extract extract = new Extract();

    private static final Faker faker = new Faker();

    public UpdateUserTest(String requestKey, String requestValue) {
        json = String.format("{\"%s\": \"%s\"}", requestKey, requestValue);
    }

    @Parameterized.Parameters(name = "{0}: {1}")
    public static Object[][] updatingParameters() {
        return new Object[][] {
                {"email", faker.internet().safeEmailAddress()},
                {"name", faker.name().name()},
                {"password", faker.internet().password()},
        };
    }

    @Before
    public void registerAndGetToken() throws InterruptedException {
        ValidatableResponse response = userClient.register(user);
        token = extract.accessToken(response);
    }

    @Test
    @DisplayName("Check possibility of updating user with token")
    @Description("Check with random parameters for JSON body")
    public void checkUserUpdate() throws InterruptedException {

        ValidatableResponse updateUser = userClient.update(json, token);
        int responseCode = extract.responseCode(updateUser);
        Boolean responseMessage = extract.success(updateUser);

        assertEquals(200, responseCode);
        assertEquals(true, responseMessage);
    }

    @Test
    @DisplayName("Check message updating user without token")
    @Description("Check with random parameters for JSON body")
    public void checkUserUpdateWithoutToken() throws InterruptedException {

        ValidatableResponse updateUser = userClient.update(json, "");
        int responseCode = extract.responseCode(updateUser);
        Boolean responseMessage = extract.success(updateUser);

        assertEquals(401, responseCode);
        assertEquals(false, responseMessage);
    }

    @After
    public void deleteUser() throws InterruptedException {
        userClient.delete(token);
    }
}
