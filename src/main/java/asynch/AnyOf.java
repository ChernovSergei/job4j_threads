package asynch;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class AnyOf {
            private static void work() throws InterruptedException {
        int count = 0;
        while (count < 10) {
            System.out.println("You: I am working");
            TimeUnit.SECONDS.sleep(1);
            count++;
        }
    }

    private static CompletableFuture<String> washHands(String name, int requiredTime) {
        return CompletableFuture.supplyAsync(
                () -> {
                    System.out.println(name + " went to wash my hands");
                    try {
                        TimeUnit.SECONDS.sleep(requiredTime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return name + " came back!";
                }

        );
    }

    public static void allOfExample() throws Exception {
        CompletableFuture<Object> all = CompletableFuture.anyOf(
                washHands("Mather", 2), washHands("Father", 3),
                washHands("Sister", 5), washHands("Brother", 6)
        );
        System.out.println("Who completed to wash the hands first - ");
        TimeUnit.SECONDS.sleep(1);
        System.out.println(all.get());
        work();
    }

    public static void main(String[] args) throws Exception {
        allOfExample();
    }
}
