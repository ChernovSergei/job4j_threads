import java.util.LinkedList;
import java.util.List;

public class ThreadPool {
    private final List<Thread> threads = new LinkedList<>();
    private final SimpleBlockingQueue<Runnable> blockingQueue;
    private int threadsNumber = Runtime.getRuntime().availableProcessors();

    public ThreadPool(int queueSize) throws InterruptedException {
        blockingQueue = new SimpleBlockingQueue<>(queueSize);
        for (int i = 0; i < threadsNumber; i++) {
            Thread thread = new Thread(() -> {
                try {
                    while (!Thread.currentThread().isInterrupted()) {
                        blockingQueue.poll().run();
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
            threads.add(thread);
        }
        for (Thread thread : threads) {
            thread.start();
        }
    }

    public void work(Runnable job) throws InterruptedException {
        this.blockingQueue.offer(job);
    }

    public void shutdown() throws InterruptedException {
        for (Thread thread : threads) {
            thread.interrupt();
        }
    }

    public static void main(String[] args) {
        try {
            int queueSize = 100;
            ThreadPool pool = new ThreadPool(queueSize);
            for (int i = 0; i < queueSize; i++) {
                int taskNumber = i;
                pool.work(() ->
                        System.out.println("offer " + taskNumber)
                );
            }
            Thread.sleep(2000);
            pool.shutdown();
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
