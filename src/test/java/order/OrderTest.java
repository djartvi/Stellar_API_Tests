package order;

import client.Extract;
import ingredients.IngredientsClient;
import randomizer.Randomizer;
import ingredients.IngredientsRequest;
import ingredients.IngredientsResponse;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class OrderTest {

    private final int size;
    private final int expectedCode;
    private final boolean success;

    private static IngredientsResponse ingredientsResponse;

    private final Randomizer randomizer = new Randomizer();
    private final OrderClient orderClient = new OrderClient();
    private static final Extract EXTRACT = new Extract();

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
        ValidatableResponse getIngredients = ingredientsClient.getIngredients();
        ingredientsResponse = EXTRACT.jsonObject(getIngredients, IngredientsResponse.class);
    }

    @Test
    @DisplayName("Make order without token")
    public void makeOrderTest() throws InterruptedException {

        IngredientsRequest ingredientsRequest = randomizer.createRandomIngredientsJson(ingredientsResponse, size);

        ValidatableResponse makeOrder = orderClient.makeOrder(ingredientsRequest, "");

        int statusCode = EXTRACT.responseCode(makeOrder);
        Boolean responseMessage = EXTRACT.success(makeOrder);

        assertEquals(expectedCode, statusCode);
        assertEquals(success, responseMessage);
    }
}
