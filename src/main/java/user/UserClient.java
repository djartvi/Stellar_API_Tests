package user;

import client.RestClient;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

public class UserClient extends RestClient {

    private final String PREFIX = "/api/auth/";

    @Step("Register unique user")
    public ValidatableResponse register(User user) throws InterruptedException{
        return spec()
                .body(user)
                .post(PREFIX + "register")
                .then();
    }

    @Step("login")
    public ValidatableResponse authorization(User user) throws InterruptedException{
        String json = String.format("{\"email\": \"%s\", \"password\": \"%s\"}",
                user.getEmail(), user.getPassword());

        return spec()
                .body(json)
                .post(PREFIX + "login")
                .then();
    }

    @Step("Update user")
    public ValidatableResponse update(String body, String token) throws InterruptedException{
        return spec()
                .header("Authorization", token)
                .body(body)
                .patch(PREFIX + "user")
                .then();
    }

    @Step("Delete user")
    public ValidatableResponse delete(String token) throws InterruptedException{
        return spec()
                .header("Authorization", token)
                .delete(PREFIX + "user")
                .then();
    }
}
