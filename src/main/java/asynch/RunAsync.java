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

    private static CompletableFuture<Void> throwTrashAway(String name, String action, int sleep) {
        return CompletableFuture.runAsync(
                () -> {
                    System.out.println(name + ": Parents, I went to " + action);
                    try {
                        TimeUnit.SECONDS.sleep(sleep);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(name + ": Parents, I've completed!");
                }

        );
    }

    public static void runAsyncExample() throws Exception {
        CompletableFuture<Void> thowATrash = throwTrashAway("Child", "throw the trash.", 5);
        CompletableFuture<Void> washDish = throwTrashAway("Brother", "wash the dishes", 2);
        CompletableFuture<Void> watchTV = throwTrashAway("GrandFather", "watch TV.", 7);
        work();
    }

    public static void main(String[] args) throws Exception {
        runAsyncExample();
    }
}