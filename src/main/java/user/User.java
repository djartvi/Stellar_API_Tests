package user;

import com.github.javafaker.Faker;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class User {

    private String email;
    private String password;
    private String name;

    public static User uniqueUser() {
        Faker faker = new Faker();

        String email = faker.internet().safeEmailAddress();
        String password = faker.internet().password();
        String name = faker.name().firstName();

        return new User(email, password, name);
    }
}
