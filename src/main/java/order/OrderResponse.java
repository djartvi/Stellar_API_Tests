package order;

import lombok.Getter;

import java.util.List;

@Getter
public class OrderResponse {

    private Order order;
    private List<Order> orders;
}
