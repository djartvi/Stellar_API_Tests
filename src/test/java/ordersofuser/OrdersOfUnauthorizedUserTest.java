package ordersofuser;

import client.ExtractResponse;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import order.OrderClient;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class OrdersOfUnauthorizedUserTest {

    private final OrderClient orderClient = new OrderClient();
    private final ExtractResponse extractResponse = new ExtractResponse();

    @Test
    @DisplayName("Get orders of unauthorized user")
    public void unauthorizedUserOrdersTest() throws InterruptedException {

        ValidatableResponse getUserOrders = orderClient.getOrdersOfUser("");

        int statusCode = extractResponse.responseCode(getUserOrders);
        String responseMessage = extractResponse.valueByKey(getUserOrders, "message");

        assertEquals(401, statusCode);
        assertEquals("You should be authorised", responseMessage);
    }
}
