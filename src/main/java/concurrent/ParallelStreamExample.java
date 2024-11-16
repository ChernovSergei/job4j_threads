package concurrent;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class ParallelStreamExample {
    public static void simpleList() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
        Optional<Integer> multiplication = list.stream()
                .reduce((left, right) -> left * right);
        System.out.println("Simple List");
        System.out.println(multiplication.get());
        System.out.println();
    }

    public static void parallelList() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
        Stream<Integer> stream = list.parallelStream();
        Optional<Integer> multiplication = stream.reduce((left, right) -> left * right);
        System.out.println("Parallel List");
        System.out.println("Is stream parallel - " + stream.isParallel());
        System.out.println(multiplication.get());
        System.out.println();
    }

    public static void parallelVsSerial() {
        IntStream parallel = IntStream.range(1, 100).parallel();
        System.out.println("Parallel vs Serial");
        System.out.println("Is stream parallel - " + parallel.isParallel());
        IntStream sequential = parallel.sequential();
        System.out.println("Is stream parallel after its type change - " + sequential.isParallel());
        System.out.println();
    }

    public static void unOrderedPeek() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
        System.out.println("Unordered parallel peak from list");
        list.stream().parallel().peek(System.out::println).toList();
        System.out.println();
    }

    public static void unUrderedForEach() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
        System.out.println("Unordered parallel list for each");
        list.stream().parallel().forEach(System.out::println);
        System.out.println();
    }

    public static void orderedForEach() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
        System.out.println("Ordered parallel list for each");
        list.stream().parallel().forEachOrdered(System.out::println);
        System.out.println();
    }

    public static void main(String[] args) {
        simpleList();
        parallelList();
        parallelVsSerial();
        unOrderedPeek();
        unUrderedForEach();
        orderedForEach();
    }
}
