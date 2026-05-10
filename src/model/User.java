package model;

public class User {
    private final String email;
    private final boolean admin;

    public User(String email, boolean admin) {
        this.email = email;
        this.admin = admin;
    }

    public String getEmail() {
        return email;
    }

    public boolean isAdmin() {
        return admin;
    }
}
