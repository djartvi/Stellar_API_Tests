package order;

import client.RestClient;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import ingredients.IngredientsRequest;

public class OrderClient extends RestClient{

    private final String PREFIX = "/api/orders";

    @Step("Make order")
    public ValidatableResponse makeOrder(IngredientsRequest ingredientsRequest, String token) throws InterruptedException{
        return spec()
                .header("Authorization", token)
                .body(ingredientsRequest)
                .post(PREFIX)
                .then();
    }

    @Step("Get orders of user")
    public ValidatableResponse getOrdersOfUser(String token) throws InterruptedException{
        return spec()
                .header("Authorization", token)
                .get(PREFIX)
                .then();
    }
}
