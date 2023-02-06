package ordersofuser;

import client.Extract;
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
    private final Extract extract = new Extract();

    @Test
    @DisplayName("Get orders of user")
    public void userOrdersTest() throws InterruptedException {

        ValidatableResponse response = ingredientsClient.getIngredients();
        IngredientsResponse ingredientsResponse = extract.jsonObject(response, IngredientsResponse.class);
        IngredientsRequest ingredientsRequest = randomizer.createRandomIngredientsJson(ingredientsResponse, 1);

        ValidatableResponse registerUser = userClient.register(User.uniqueUser());
        token = extract.accessToken(registerUser);

        ValidatableResponse makeOrder = orderClient.makeOrder(ingredientsRequest, token);
        OrderResponse orderResponse = extract.jsonObject(makeOrder, OrderResponse.class);
        String orderId = orderResponse.getOrder().getId();

        ValidatableResponse getUserOrders = orderClient.getOrdersOfUser(token);
        OrderResponse userOrdersResponse = extract.jsonObject(getUserOrders, OrderResponse.class);

        int statusCode = extract.responseCode(getUserOrders);
        String userOrderId = userOrdersResponse.getOrders().get(0).getId();

        System.out.println(userOrderId);

        assertEquals(200, statusCode);
        assertEquals(orderId, userOrderId);
    }

    @After
    public void deleteUser() throws InterruptedException {
        userClient.delete(token);
    }
}
