import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import java.util.*;

@ThreadSafe
public class SimpleBlockingQueue<T> {

    @GuardedBy("this")
    private final Queue<T> queue = new LinkedList<>();

    public synchronized void offer(T value) {
            try {
                while (!queue.isEmpty()) {
                    this.wait();
                }
                queue.offer(value);
                this.notify();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
    }

    public synchronized T poll() {
            try {
                while (queue.isEmpty()) {
                    this.wait();
                }
                this.notify();
                return queue.poll();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            return null;
    }
}