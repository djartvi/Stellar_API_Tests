package client;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

public class ExtractResponse {

    @Step("Extract status code")
    public int responseCode(ValidatableResponse response) {
        return response
                .extract()
                .statusCode();
    }

    @Step("Extract value from JSON")
    public <T> T valueByKey(ValidatableResponse response, String key) {
        return response
                .extract()
                .path(key);
    }

    @Step("Extract value as object")
    public <T> T jsonObject(ValidatableResponse response, Class<T> object) {
        return response
                .extract()
                .body().as(object);
    }
}
