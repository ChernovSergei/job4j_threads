package ref;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class UserCache {
    ConcurrentHashMap<Integer, User> users = new ConcurrentHashMap<>();
    private final AtomicInteger id = new AtomicInteger();

    public void addUser(User user) {
        users.put(id.incrementAndGet(), User.of(user.getName(), user.getEmail()));
    }

    public User findById(int id) {
        return User.of(users.get(id).getName(), users.get(id).getEmail());
    }

    public List<User> findAll() {
        return users.values().stream().map(u -> User.of(u.getName(), u.getEmail())).collect(Collectors.toList());
    }
}
