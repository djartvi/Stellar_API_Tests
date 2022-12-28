package order.serializer;

import java.util.ArrayList;
import java.util.List;

public class IngredientsRequest {

    private List<String> ingredients = new ArrayList<>();

    public IngredientsRequest(List<String> ingredients) {
        this.ingredients = ingredients;
    }
}

