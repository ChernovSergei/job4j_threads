package asynch;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class AllOf {
        private static void work() throws InterruptedException {
        int count = 0;
        while (count < 10) {
            System.out.println("You: I am working");
            TimeUnit.SECONDS.sleep(1);
            count++;
        }
    }

    private static CompletableFuture<Void> washHands(String name, int requiredTime) {
        return CompletableFuture.runAsync(
                () -> {
                    System.out.println(name + " went to wash my hands");
                    try {
                        TimeUnit.SECONDS.sleep(requiredTime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(name + " came back!");
                }

        );
    }

    public static void allOfExample() throws Exception {
        CompletableFuture<Void> all = CompletableFuture.allOf(
                washHands("Mather", 2), washHands("Father", 3),
                washHands("Sister", 5), washHands("Brother", 6)
        );
        work();
    }

    public static void main(String[] args) throws Exception {
        allOfExample();
    }
}
