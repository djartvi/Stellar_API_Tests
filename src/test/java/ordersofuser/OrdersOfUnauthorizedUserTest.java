package ordersofuser;

import client.Extract;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import order.OrderClient;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class OrdersOfUnauthorizedUserTest {

    private final OrderClient orderClient = new OrderClient();
    private final Extract extract = new Extract();

    @Test
    @DisplayName("Get orders of unauthorized user")
    public void unauthorizedUserOrdersTest() throws InterruptedException {

        ValidatableResponse getUserOrders = orderClient.getOrdersOfUser("");

        int statusCode = extract.responseCode(getUserOrders);
        String responseMessage = extract.message(getUserOrders);

        assertEquals(401, statusCode);
        assertEquals("You should be authorised", responseMessage);
    }
}
