import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import java.util.*;

@ThreadSafe
public class SimpleBlockingQueue<T> {

    @GuardedBy("this")
    private final Queue<T> queue = new LinkedList<>();
    private final int queueSize;

    public SimpleBlockingQueue(int queueSize) {
        this.queueSize = queueSize;
    }

    public synchronized void offer(T value) throws InterruptedException {
                while (queue.size() >= queueSize) {
                    this.wait();
                }
                queue.offer(value);
                this.notify();
    }

    public synchronized T poll() throws InterruptedException {
                T result;
                while (queue.isEmpty()) {
                    this.wait();
                }
                result = queue.poll();
                this.notify();
                return result;
    }

    public synchronized boolean isEmpty() throws InterruptedException {
        return queue.isEmpty();
    }
}