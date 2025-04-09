package asynch;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class RunAsync {

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

    public static void runAsyncExample() throws Exception {
        CompletableFuture<Void> thowATrash = throwTrashAway();
        work();
    }

    public static void main(String[] args) throws Exception {
        runAsyncExample();
    }
}