package concurrent;

public class ThreadState {
    public static void main(String[] args) {
        Thread first = new Thread(
                () -> { }
        );
        Thread second = new Thread(
                () -> { }
        );
        System.out.print(first.getName() + " ");
        System.out.println(first.getState());
        System.out.print(second.getName() + " ");
        System.out.println(second.getState());
        first.start();
        second.start();
        while (first.getState() != Thread.State.TERMINATED || second.getState() != Thread.State.TERMINATED) {
            System.out.print(first.getName() + " ");
            System.out.println(first.getState());
            System.out.print(second.getName() + " ");
            System.out.println(second.getState());
        }
        System.out.print(Thread.currentThread().getName() + " ");
        System.out.println("- both thread are terminated");
    }
}
