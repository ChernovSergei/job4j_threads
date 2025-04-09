package asynch;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class ThenCompose {
        private static void work() throws InterruptedException {
        int count = 0;
        while (count < 20) {
            System.out.println("You: I am working");
            TimeUnit.SECONDS.sleep(1);
            count++;
        }
    }

    private static CompletableFuture<String> buyProducts(String product) {
        return CompletableFuture.supplyAsync(
                () -> {
                    System.out.println("Child: Parents, I went to the shop");
                    try {
                        TimeUnit.SECONDS.sleep(5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Child: Parents, I came back!");
                    return product;
                }
        );
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

    public static void thenComposeExample() throws Exception {
        CompletableFuture<String> result = throwTrashAway().thenCompose(a -> buyProducts("Milk"));
        work();
        System.out.println(result.get() + " had been bought.");
    }

    public static void main(String[] args) throws Exception {
            thenComposeExample();
    }
}
