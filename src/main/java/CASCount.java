import net.jcip.annotations.ThreadSafe;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

@ThreadSafe
public class CASCount {
    private final AtomicInteger count = new AtomicInteger();

    public CASCount(Integer initialValue) {
        count.set(initialValue);
    }

    public void increment() {
        int incrementedValue;
        int initialValue;
        do {
            initialValue = count.get();
            incrementedValue = initialValue + 1;
        } while (!count.compareAndSet(initialValue, incrementedValue));
    }

    public int get() {
        return count.get();
    }
}
