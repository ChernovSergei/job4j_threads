public class ParallelSearch {

    public static void main(String[] args) {
        final int size = 3;
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<Integer>(size);
        final Thread consumer = new Thread(
                () -> {
                    for (int index = 0; index != size; index++) {
                        try {
                            System.out.println(queue.poll());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            Thread.currentThread().interrupt();
                        }
                    }
                }
        );
        consumer.start();
        final Thread producer = new Thread(
                () -> {
                    for (int index = 0; index != size; index++) {
                        try {
                            queue.offer(index);
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );
        producer.start();
    }
}
