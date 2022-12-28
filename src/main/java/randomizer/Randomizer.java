package randomizer;

import ingredients.Data;
import ingredients.IngredientsRequest;
import ingredients.IngredientsResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Randomizer {

    private final Random random = new Random();
    private final List<String> randomList = new ArrayList<>();

    public String getRandomId(IngredientsResponse response) {
        List<Data> data = response.getData();
        int randomIngredient = random.nextInt(data.size());

        return data.get(randomIngredient).getId();
    }

    public IngredientsRequest createRandomIngredientsJson(IngredientsResponse response, int size) {
        for (int i = 0; i < size; i++) {
            randomList.add(getRandomId(response));
        }

        return new IngredientsRequest(randomList);
    }
}
