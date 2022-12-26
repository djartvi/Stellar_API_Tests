package client;

import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class RestClient {

    private final String BASE_URI = "https://stellarburgers.nomoreparties.site";
    private final int REQUEST_DELAY = 500;

    protected RequestSpecification spec() throws InterruptedException {
        Thread.sleep(REQUEST_DELAY);
        return given()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URI);
    }
}
