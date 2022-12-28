package order;

import client.ExtractResponse;
import order.randomizer.Randomizer;
import order.serializer.IngredientsRequest;
import order.serializer.IngredientsResponse;
import user.User;
import user.UserClient;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class OrderWithTokenTest {

    private final int size;
    private final int expectedCode;
    private final boolean success;

    private static String token;
    private static IngredientsResponse ingredientsResponse;

    private static final UserClient userClient = new UserClient();
    private final Randomizer randomizer = new Randomizer();
    private final OrderClient orderClient = new OrderClient();
    private static final ExtractResponse extractResponse = new ExtractResponse();

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
        ingredientsResponse = ingredientsClient.getIngredients();

        ValidatableResponse registerUser = userClient.register(User.uniqueUser());

        token = extractResponse.getStringValueByKey(registerUser, "accessToken");
    }

    @Test
    @DisplayName("Make order with token")
    public void makeOrderWithTokenTest() throws InterruptedException {

        List<String> listToSend = randomizer.createRandomList(ingredientsResponse, size);
        IngredientsRequest ingredientsRequest = new IngredientsRequest(listToSend);

        ValidatableResponse makeOrder = orderClient.makeOrder(ingredientsRequest, token);

        int statusCode = extractResponse.responseCode(makeOrder);
        Boolean responseMessage = extractResponse.getBooleanValueByKey(makeOrder, "success");

        assertEquals(expectedCode, statusCode);
        assertEquals(success, responseMessage);
    }

    @AfterClass
    public static void deleteUser() throws InterruptedException {
        userClient.delete(token);
    }
}
