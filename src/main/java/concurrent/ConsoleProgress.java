package concurrent;

public class ConsoleProgress implements Runnable {
    char[] process = new char[] {'-', '\\', '|', '/'};
    int index = 0;

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                System.out.print("\rLoad ... " + process[index]);
                Thread.sleep(500);
                index++;
                if (index > 3) {
                    index = 0;
                }
            } catch (InterruptedException e) {
                System.out.println();
                throw new RuntimeException(e);
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread process = new Thread(new ConsoleProgress());
        process.start();
        Thread.sleep(5000);
        process.interrupt();
    }
}
