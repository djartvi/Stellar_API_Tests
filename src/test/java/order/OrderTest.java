package order;

import client.ExtractResponse;
import order.randomizer.Randomizer;
import order.serializer.IngredientsRequest;
import order.serializer.IngredientsResponse;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class OrderTest {

    private final int size;
    private final int expectedCode;
    private final boolean success;

    private static IngredientsResponse ingredientsResponse;

    private final Randomizer randomizer = new Randomizer();
    private final OrderClient orderClient = new OrderClient();
    private final ExtractResponse extractResponse = new ExtractResponse();

    private static final IngredientsClient ingredientsClient = new IngredientsClient();

    public OrderTest(int size, int expectedCode, boolean success) {
        this.size = size;
        this.expectedCode = expectedCode;
        this.success = success;
    }

    @Parameterized.Parameters(name = "ingredients: {0}, status code: {1}")
    public static Object[][] ingredientListParameters() {
        return new Object[][] {
                {1, 200, true},
                {2, 200, true},
                {5, 200, true},
                {0, 400, false},
        };
    }

    @BeforeClass
    public static void setListOfIngredients() throws InterruptedException {
        ingredientsResponse = ingredientsClient.getIngredients();
    }

    @Test
    @DisplayName("Make order without token")
    public void makeOrderTest() throws InterruptedException {

        List<String> listToSend = randomizer.createRandomList(ingredientsResponse, size);
        IngredientsRequest ingredientsRequest = new IngredientsRequest(listToSend);

        ValidatableResponse makeOrder = orderClient.makeOrder(ingredientsRequest, "");

        int statusCode = extractResponse.responseCode(makeOrder);
        Boolean responseMessage = extractResponse.getBooleanValueByKey(makeOrder, "success");

        assertEquals(expectedCode, statusCode);
        assertEquals(success, responseMessage);
    }
}
