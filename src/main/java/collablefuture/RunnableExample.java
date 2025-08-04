package collablefuture;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class RunnableExample {
    private static Runnable runnableTask1 = new Runnable() {
        @Override
        public void run() {
            System.out.println("From separate overriden Interface");
        }
    };

    private static Callable<String> callableTask1 = new Callable<String>() {
        @Override
        public String call() throws Exception {
            return "Result from callableTask1";
        }
    };

    public static void main(String[] args) {
        ConsolePrint consolePrint = new ConsolePrint("Print from 1st Thread");
        Thread first = new Thread(consolePrint);
        Thread second = new Thread(
                () -> System.out.println("Print from second Thread")
        );
        Thread third = new Thread(runnableTask1);
        FutureTask<String> future = new FutureTask<>(callableTask1);
        Thread fourth = new Thread(future);
        Thread fifth = new Thread(
                () -> System.out.println("Thread using lambda")
        );
        second.start();
        first.start();
        third.start();
        fourth.start();
        fifth.start();
        try {
            System.out.println(future.get());
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
