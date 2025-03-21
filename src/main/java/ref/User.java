package ref;

public class User {
    private int id;
    private String name;
    private String email;

    public static User of(String name, String email) {
        User user = new User();
        user.name = name;
        user.email = email;
        return user;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
