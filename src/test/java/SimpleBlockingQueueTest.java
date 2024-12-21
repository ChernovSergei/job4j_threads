import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.stream.IntStream;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SimpleBlockingQueueTest {

    @Test
    public void checkIfQueueHasAllElements() throws InterruptedException {
        ArrayList<Integer> result = new ArrayList<>();
        ArrayList<Integer> expected = new ArrayList<>();
        expected.add(1);
        expected.add(2);
        expected.add(3);
        expected.add(4);
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(5);
        Thread thread1 = new Thread(() -> IntStream.range(1, 5).forEach(i -> {
            try {
                queue.offer(i);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }));
        Thread thread2 = new Thread(() -> IntStream.range(1, 5).forEach(i -> {
            try {
                result.add(queue.poll());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }));
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        assertTrue(result.containsAll(expected));
    }
}