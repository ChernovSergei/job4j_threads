import ref.User;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EmailNotification {
    ExecutorService pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    public void emailTo(User user) {
        String emailTemplate = "subject = Notification {%s} to email {%s}. \n"
                + "body = Add a new event to {%s}";
        pool.submit(new Runnable() {
            @Override
            public void run() {
                System.out.println(String.format(emailTemplate, user.getName(), user.getEmail(), user.getName()));
            }
        });
    }

    public void close() {
        pool.shutdown();
        while (!pool.isTerminated()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public void send(String subject, String body, String email) {

    }

    public static void main(String[] args) {
        EmailNotification emailNotification = new EmailNotification();
        List<User> users = new LinkedList<>();
        users.add(User.of("Vasiliy",  "vasiliy@gmail.com"));
        users.add(User.of("Maria", "maria@gmail.com"));
        users.add(User.of("Luda", "luda@gmail.com"));
        users.add(User.of("Marat", "marat@gmail.com"));
        users.add(User.of("Evgeny", "evgeny@gmail.com"));
        users.add(User.of("Nikolay", "nikolay@gmail.com"));
        users.add(User.of("Petr", "petr@gmail.com"));
        users.add(User.of("Tarantino", "tarantino@gmail.com"));
        users.add(User.of("Maksim", "maksim@gmail.com"));
        users.forEach(u -> emailNotification.emailTo(u));
        emailNotification.close();
    }
}
