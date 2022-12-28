package ingredients;

import client.RestClient;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

public class IngredientsClient extends RestClient {

    @Step("Get ingredients")
    public ValidatableResponse getIngredients() throws InterruptedException {
        return spec()
                .get("/api/ingredients")
                .then();
    }
}
