package ordersofuser;

import client.ExtractResponse;
import ingredients.IngredientsResponse;
import org.junit.After;
import user.User;
import user.UserClient;
import order.OrderClient;
import order.OrderResponse;
import randomizer.Randomizer;
import ingredients.IngredientsClient;
import ingredients.IngredientsRequest;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class OrdersOfUserTest {

    String token;

    private final Randomizer randomizer = new Randomizer();
    private final IngredientsClient ingredientsClient = new IngredientsClient();
    private final OrderClient orderClient = new OrderClient();
    private final UserClient userClient = new UserClient();
    private final ExtractResponse extractResponse = new ExtractResponse();

    @Test
    @DisplayName("Get orders of user")
    public void userOrdersTest() throws InterruptedException {

        ValidatableResponse response = ingredientsClient.getIngredients();
        IngredientsResponse ingredientsResponse = extractResponse.jsonObject(response, IngredientsResponse.class);
        IngredientsRequest ingredientsRequest = randomizer.createRandomIngredientsJson(ingredientsResponse,1);

        ValidatableResponse registerUser = userClient.register(User.uniqueUser());
        token = extractResponse.valueByKey(registerUser, "accessToken");

        ValidatableResponse makeOrder = orderClient.makeOrder(ingredientsRequest, token);
        OrderResponse orderResponse = extractResponse.jsonObject(makeOrder, OrderResponse.class);
        String orderId = orderResponse.getOrder().getId();

        ValidatableResponse getUserOrders = orderClient.getOrdersOfUser(token);
        OrderResponse userOrdersResponse = extractResponse.jsonObject(getUserOrders, OrderResponse.class);

        int statusCode = extractResponse.responseCode(getUserOrders);
        String userOrderId = userOrdersResponse.getOrders().get(0).getId();

        assertEquals(200, statusCode);
        assertEquals(orderId, userOrderId);
    }

    @After
    public void deleteUser() throws InterruptedException {
        userClient.delete(token);
    }
}
