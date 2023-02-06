package user;

import client.Extract;

import com.github.javafaker.Faker;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class RegisterWithoutAnyFieldTest {

    private final String email;
    private final String password;
    private final String name;

    private ValidatableResponse response;
    private int responseCode;

    private final UserClient userClient = new UserClient();
    private final Extract extract = new Extract();

    private static final Faker faker = new Faker();

    public RegisterWithoutAnyFieldTest(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    @Parameterized.Parameters(name = "{0}, {1}, {2}")
    public static Object[][] userJsonParameters() {
        return new Object[][] {
                {null, faker.internet().password(), faker.name().name()},
                {faker.internet().safeEmailAddress(), null, faker.name().name()},
                {faker.internet().safeEmailAddress(), faker.internet().password(), null},
        };
    }

    @Test
    @DisplayName("Check registration of unique user")
    @Description("Check with random parameters for JSON body")
    public void checkRegistrationWithoutRequiredFields() throws InterruptedException {

        User user = new User(email, password, name);
        response = userClient.register(user);

        responseCode = extract.responseCode(response);
        String responseMessage = extract.message(response);

        assertEquals(403, responseCode);
        assertEquals("Email, password and name are required fields", responseMessage);
    }

    @After
    public void deleteUser() throws InterruptedException {
        if (responseCode == 200) {
            String token = extract.accessToken(response);
            userClient.delete(token);
        }
    }
}
