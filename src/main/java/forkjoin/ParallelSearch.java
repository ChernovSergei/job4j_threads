package forkjoin;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ParallelSearch<T> extends RecursiveTask<Integer> {
    private final T[] array;
    private final int from;
    private final int to;
    private final T wantedObject;

    public ParallelSearch(T[] array, int from, int to, T wantedObject) {
        this.array = array;
        this.from = from;
        this.to = to;
        this.wantedObject = wantedObject;
    }

    @Override
    protected Integer compute() {
        if (to - from < 10) {
            return indexLinearSearch(from, to);
        }
        int middle = (from + to) / 2;
        ParallelSearch<T> leftSort = new ParallelSearch<>(array, from, middle, wantedObject);
        ParallelSearch<T> rightSort = new ParallelSearch<>(array, middle + 1, to, wantedObject);
        leftSort.fork();
        rightSort.fork();
        Integer leftResult = leftSort.join();
        Integer rightResult = rightSort.join();
        return (leftResult == -1 && rightResult == -1) ? null : Math.max(leftResult, rightResult);
    }

    public static <T> Integer getIndex(T[] array, T wantedObject) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        return (Integer) forkJoinPool.invoke(new ParallelSearch(array, 0, array.length - 1, wantedObject));
    }

    private int indexLinearSearch(int from, int to) {
        int result = -1;
        for (int i = from; i < to; i++) {
            if (array[i].equals(wantedObject)) {
                result = i;
            }
        }
        return result;
    }
}
