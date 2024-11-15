package threads;

public class SecondThread extends Thread {
    @Override
    public void run() {
        ThreadLocalDemo.threadLocal.set("thread#2");
        System.out.println(ThreadLocalDemo.threadLocal.get());
    }
}
