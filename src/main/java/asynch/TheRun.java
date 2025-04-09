package asynch;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class TheRun {
    private static void work() throws InterruptedException {
        int count = 0;
        while (count < 10) {
            System.out.println("You: I am working");
            TimeUnit.SECONDS.sleep(1);
            count++;
        }
    }

    private static CompletableFuture<Void> throwTrashAway() {
        return CompletableFuture.runAsync(
                () -> {
                    System.out.println("Child: Parents, I went to throw the trash away");
                    try {
                        TimeUnit.SECONDS.sleep(5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Child: Parents, I came back!");
                }

        );
    }

    public static void thenRunExample() throws Exception {
        throwTrashAway().thenRun(() -> {
            int count = 0;
            while (count < 3) {
                System.out.println("Child: I am washing my hands");
                try {
                    TimeUnit.MICROSECONDS.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                count++;
            }
            System.out.println("Child: My hands are clean!");
        });
        work();
    }

    public static void main(String[] args) throws Exception {
        thenRunExample();
    }
}