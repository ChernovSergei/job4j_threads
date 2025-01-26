import net.jcip.annotations.ThreadSafe;
import java.util.concurrent.atomic.AtomicInteger;

@ThreadSafe
public class CASCount {
    private final AtomicInteger count = new AtomicInteger();

    public CASCount(Integer initialValue) {
        count.set(initialValue);
    }

    public void increment() {
        count.incrementAndGet();
    }

    public int get() {
        return count.get();
    }
}
