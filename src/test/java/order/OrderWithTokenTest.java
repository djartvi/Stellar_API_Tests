package order;

import client.Extract;
import ingredients.IngredientsClient;
import ingredients.IngredientsResponse;
import randomizer.Randomizer;
import ingredients.IngredientsRequest;
import user.User;
import user.UserClient;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class OrderWithTokenTest {

    private final int size;
    private final int expectedCode;
    private final boolean success;

    private static String token;
    private static ValidatableResponse response;
    private static IngredientsResponse ingredientsResponse;

    private final Randomizer randomizer = new Randomizer();
    private final OrderClient orderClient = new OrderClient();

    private static final UserClient userClient = new UserClient();
    private static final Extract EXTRACT = new Extract();
    private static final IngredientsClient ingredientsClient = new IngredientsClient();

    public OrderWithTokenTest(int size, int expectedCode, boolean success) {
        this.size = size;
        this.expectedCode = expectedCode;
        this.success = success;
    }

    @Parameterized.Parameters(name = "ingredients: {0}, status code: {1}")
    public static Object[][] ingredientListParameters() {
        return new Object[][] {
                {2, 200, true},
                {3, 200, true},
                {6, 200, true},
                {0, 400, false},
        };
    }

    @BeforeClass
    public static void setListOfIngredients() throws InterruptedException {
        response = ingredientsClient.getIngredients();
        ingredientsResponse = EXTRACT.jsonObject(response, IngredientsResponse.class);

        ValidatableResponse registerUser = userClient.register(User.uniqueUser());
        token = EXTRACT.accessToken(registerUser);
    }

    @Test
    @DisplayName("Make order with token")
    public void makeOrderWithTokenTest() throws InterruptedException {

        IngredientsRequest ingredientsRequest = randomizer.createRandomIngredientsJson(ingredientsResponse, size);

        ValidatableResponse makeOrder = orderClient.makeOrder(ingredientsRequest, token);

        int statusCode = EXTRACT.responseCode(makeOrder);
        Boolean responseMessage = EXTRACT.success(makeOrder);

        assertEquals(expectedCode, statusCode);
        assertEquals(success, responseMessage);
    }

    @AfterClass
    public static void deleteUser() throws InterruptedException {
        userClient.delete(token);
    }
}
