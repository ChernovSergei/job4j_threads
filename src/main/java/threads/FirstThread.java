package threads;

public class FirstThread extends Thread {
    @Override
    public void run() {
        ThreadLocalDemo.threadLocal.set("thread#1");
        System.out.println(ThreadLocalDemo.threadLocal.get());
    }
}
