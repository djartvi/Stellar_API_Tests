package ingredients;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;

@Getter
public class Data {

    @SerializedName(value = "_id")
    private String id;
}
