package asynch;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class ThenCombine {
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

    public static void thenComposeExample() throws Exception {
        CompletableFuture<String> result = buyProducts("Cookies").thenCombine(buyProducts("Milk"),
                (p1, p2) -> "Next products had been bought: " + p1 + "; " + p2 + ".");
        work();
        System.out.println(result.get());
    }

    public static void main(String[] args) throws Exception {
            thenComposeExample();
    }
}
