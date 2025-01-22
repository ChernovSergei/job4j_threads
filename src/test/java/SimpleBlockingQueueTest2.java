import junit.framework.TestCase;
import org.junit.jupiter.api.Test;
import java.util.concurrent.CopyOnWriteArrayList;
import static org.assertj.core.api.Assertions.assertThat;

public class SimpleBlockingQueueTest2 {
    @Test
    public void whenFetchAllThenGetIt() throws InterruptedException {
        final int size = 5;
        final CopyOnWriteArrayList<Integer> buffer = new CopyOnWriteArrayList<>();
        final SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(size);
        Thread producer = new Thread(
                () -> {
                    for(int i = 0; i < size; i++) {
                        try {
                            queue.offer(i);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );
        Thread consumer = new Thread(
                () -> {
                        try {
                            while(!queue.isEmpty() || !Thread.currentThread().isInterrupted()) {
                                buffer.add(queue.poll());
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            Thread.currentThread().interrupt();
                        }
                }
        );
        producer.start();
        consumer.start();
        producer.join();
        consumer.interrupt();
        consumer.join();
        assertThat(buffer).containsExactly(0, 1, 2, 3, 4);
    }

}