package randomizer;

import ingredients.Data;
import ingredients.IngredientsResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Randomizer {

    public String getRandomId(IngredientsResponse ingredients) throws InterruptedException {
        List<Data> data = ingredients.getData();
        Random random = new Random();
        int randomIngredient = random.nextInt(data.size());

        return data.get(randomIngredient).getId();
    }

    public List<String> createRandomList(IngredientsResponse ingredients, int size) throws InterruptedException {
        List<String> randomList = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            randomList.add(getRandomId(ingredients));
        }

        return randomList;
    }
}
