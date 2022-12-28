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

    @Step("Get string value from JSON")
    public String getStringValueByKey(ValidatableResponse response, String key) {
        return response
                .extract()
                .path(key);
    }

    @Step("Get boolean value from JSON")
    public boolean getBooleanValueByKey(ValidatableResponse response, String key) {
        return response
                .extract()
                .path(key);
    }
}
