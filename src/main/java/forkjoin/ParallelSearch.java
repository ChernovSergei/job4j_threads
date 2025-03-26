package forkjoin;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ParallelSearch extends RecursiveTask<Integer> {
    private final Object[] array;
    private final int from;
    private final int to;
    private final Object wantedObject;

    public ParallelSearch(Object[] array, int from, int to, Object wantedObject) {
        this.array = array;
        this.from = from;
        this.to = to;
        this.wantedObject = wantedObject;
    }

    @Override
    protected Integer compute() {
        if (from == to) {
            return array[from].equals(wantedObject) ? from : null;
        }
        int middle = (from + to) / 2;
        ParallelSearch leftSort = new ParallelSearch(array, from, middle, wantedObject);
        ParallelSearch rightSort = new ParallelSearch(array, middle + 1, to, wantedObject);
        leftSort.fork();
        rightSort.fork();
        Integer leftResult = leftSort.join();
        Integer rightResult = rightSort.join();
        if (leftResult != null) {
            return leftResult;
        }
        if (rightResult != null) {
            return rightResult;
        }
        return null;
    }

    public static Integer getIndex(Object[] array, Object element) {
        Integer result = null;
        if (array.length < 10) {
            for (int i = 0; i < array.length; i++) {
                if (array[i].equals(element)) {
                    result = i;
                }
            }
        } else {
            ForkJoinPool forkJoinPool = new ForkJoinPool();
            result = forkJoinPool.invoke(new ParallelSearch(array, 0, array.length - 1, element));
        }
        return result;
    }
}
