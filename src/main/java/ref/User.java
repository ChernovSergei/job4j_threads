package ref;

import java.util.LinkedList;
import java.util.List;

public class User {
    private int id;
    private String name;

    public static User of(String name) {
        User user = new User();
        user.name = name;
        return user;
    }

    public static List<User> ofList(List<User> cashUsers) {
        List<User> users = new LinkedList<>();
        cashUsers.forEach(u -> users.add(of(u.getName())));
        return users;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
