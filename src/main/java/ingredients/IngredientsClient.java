package ingredients;

import client.RestClient;
import io.qameta.allure.Step;

public class IngredientsClient extends RestClient {

    @Step("Get ingredients")
    public IngredientsResponse getIngredients() throws InterruptedException {
        return spec()
                .get("/api/ingredients")
                .then()
                .extract()
                .body().as(IngredientsResponse.class);
    }
}
