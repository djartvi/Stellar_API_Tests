package user;

import client.ExtractResponse;

import com.github.javafaker.Faker;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class RegisterWithoutAnyFieldTest {

    private final String email;
    private final String password;
    private final String name;

    private final UserClient userClient = new UserClient();
    private final ExtractResponse extractResponse = new ExtractResponse();

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
        ValidatableResponse response = userClient.register(user);

        int responseCode = extractResponse.responseCode(response);
        String responseMessage = extractResponse.valueByKey(response, "message");

        assertEquals(403, responseCode);
        assertEquals("Email, password and name are required fields", responseMessage);
    }
}
