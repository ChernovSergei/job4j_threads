import java.util.LinkedList;
import java.util.List;

public class ThreadPool {
    private final List<Thread> threads = new LinkedList<>();
    private final SimpleBlockingQueue<Runnable> tasks = new SimpleBlockingQueue<>(40);
    int size;

    public ThreadPool() throws InterruptedException {
        size = Runtime.getRuntime().availableProcessors();
        for (int i = 0; i < size; i++) {
            Thread thread = new Thread(tasks.poll());
            threads.add(thread);
        }
        for (Thread thread : threads) {
            thread.start();
        }
    }

    public void work (Runnable job) throws InterruptedException {
        if(Thread.currentThread().isInterrupted()) {
            throw new IllegalStateException("ThreadPool is topped");
        }
        this.tasks.offer(job);
    }

    public void shutdown() throws InterruptedException {
        for (Thread thread : threads) {
            thread.interrupt();
            thread.join();
        }
    }
}
