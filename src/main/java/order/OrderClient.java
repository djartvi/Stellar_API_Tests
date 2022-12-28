package order;

import client.RestClient;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import order.serializer.IngredientsRequest;

public class OrderClient extends RestClient{

    private final String PREFIX = "/api/orders";

    @Step("Register unique user")
    public ValidatableResponse makeOrder(IngredientsRequest ingredientsRequest, String token) throws InterruptedException{
        return spec()
                .header("Authorization", token)
                .body(ingredientsRequest)
                .post(PREFIX)
                .then();
    }
}
