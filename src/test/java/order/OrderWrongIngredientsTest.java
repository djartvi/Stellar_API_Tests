package order;

import client.ExtractResponse;
import com.github.javafaker.Faker;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import ingredients.IngredientsRequest;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class OrderWrongIngredientsTest {

    private final Faker faker = new Faker();
    private final OrderClient orderClient = new OrderClient();
    private final ExtractResponse extractResponse = new ExtractResponse();

    @Test
    @DisplayName("Make order with wrong hash")
    public void makeOrderTest() throws InterruptedException {

        String hash = faker.internet().password(24, 25);
        List<String> listToSend = List.of(hash);
        IngredientsRequest ingredientsRequest = new IngredientsRequest(listToSend);

        ValidatableResponse makeOrder = orderClient.makeOrder(ingredientsRequest, "");

        int statusCode = extractResponse.responseCode(makeOrder);

        assertEquals(500, statusCode);
    }
}
