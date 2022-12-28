package randomizer;

import client.ExtractResponse;
import ingredients.Data;
import ingredients.IngredientsClient;
import ingredients.IngredientsRequest;
import ingredients.IngredientsResponse;
import io.restassured.response.ValidatableResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Randomizer {

    public static String getRandomId(IngredientsResponse ingredients) throws InterruptedException {
        List<Data> data = ingredients.getData();
        Random random = new Random();
        int randomIngredient = random.nextInt(data.size());

        return data.get(randomIngredient).getId();
    }

    public static IngredientsRequest createRandomIngredientsJson(IngredientsResponse ingredients, int size) throws InterruptedException {
        List<String> randomList = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            randomList.add(getRandomId(ingredients));
        }

        return new IngredientsRequest(randomList);
    }
}
