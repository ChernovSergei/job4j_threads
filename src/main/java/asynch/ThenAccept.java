package asynch;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class ThenAccept {

    private static void work() throws InterruptedException {
        int count = 0;
        while (count < 10) {
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

    public static void runAsyncExample() throws Exception {
        CompletableFuture<String> buyProduct = buyProducts("Milk");
        buyProduct.thenAccept((product) -> System.out.println("Child: I put the " + product + " into the frig"));
        work();
        System.out.println(buyProduct.get() + " had been bought.");
    }

    public static void main(String[] args) throws Exception {
        runAsyncExample();
    }
}
