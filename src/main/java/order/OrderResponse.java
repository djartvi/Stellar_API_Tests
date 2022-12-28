package order;

import java.util.List;

public class OrderResponse {

    private List<Order> orders;

    private Order order;

    public OrderResponse(List<Order> orders) {
        this.orders = orders;
    }

    public OrderResponse(Order order) {
        this.order = order;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public Order getOrder() {
        return order;
    }
}
