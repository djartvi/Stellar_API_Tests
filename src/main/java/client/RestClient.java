package client;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class RestClient {

    private static final String BASE_URI = "https://stellarburgers.nomoreparties.site";
    private static final int REQUEST_DELAY = 500;

    protected RequestSpecification spec() throws InterruptedException {
        Thread.sleep(REQUEST_DELAY);

        return given()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URI)
                .filter(new AllureRestAssured());
    }
}
