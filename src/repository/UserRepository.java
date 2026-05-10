package repository;

import model.User;

import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    private final List<User> users;

    public UserRepository() {
        users = new ArrayList<>();
        users.add(new User("admin@railway.com", true));
        users.add(new User("ana.popescu@mail.com", false));
        users.add(new User("mihai.ionescu@mail.com", false));
    }

    public User findOrCreateByEmail(String email) {
        for (User user : users) {
            if (user.getEmail().equalsIgnoreCase(email)) {
                return user;
            }
        }

        User user = new User(email, false);
        users.add(user);
        return user;
    }
}
