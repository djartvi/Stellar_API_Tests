package user;

import com.github.javafaker.Faker;

public class User {

    private final String email;
    private final String password;
    private final String name;

    public User(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public static User uniqueUser() {
        Faker faker = new Faker();

        String email = faker.internet().safeEmailAddress();
        String password = faker.internet().password();
        String name = faker.name().firstName();

        return new User(email, password, name);
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }
}
