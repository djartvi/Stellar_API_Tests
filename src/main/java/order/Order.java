package order;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;

@Getter
public class Order {

    @SerializedName(value = "_id")
    private String id;
}
