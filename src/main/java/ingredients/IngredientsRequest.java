package ingredients;

import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class IngredientsRequest {

    private List<String> ingredients = new ArrayList<>();
}

