package collablefuture;

public class ConsolePrint implements Runnable {
    private String message;

    public ConsolePrint(String message) {
        this.message = message;
    }

    @Override
    public void run() {
        System.out.println(message);
    }
}
