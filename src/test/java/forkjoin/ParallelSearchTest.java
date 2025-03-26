package forkjoin;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class ParallelSearchTest {
    String[] names = {
            "Alexey",
            "Maria",
            "Anton",
            "Ivan",
            "Sofia",
            "Michail",
            "Gadzilka",
            "Gremlin",
            "Alexandr",
            "Pushkin",
            "Mubariz",
    };

    Object[] objects = {
            "Alexey",
            11,
            4l,
            Character.getName(3),
            "Sofia",
            43,
            54d,
            Integer.valueOf(4),
            "Alexandr",
            55l,
            "Mubariz",
    };

    Integer[] numbers1 = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13};

    Integer[] numbers2 = {1, 2, 3, 4, 5};

    @Test
    void whenStringArrayParallelSearch() {
        Integer expected = 6;
        Integer actual = ParallelSearch.getIndex(names, "Gadzilka");
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void whenDifferentTypesParallelSearch() {
        Integer expected = 9;
        Integer actual = ParallelSearch.getIndex(objects, 55l);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void whenLinearSearch() {
        Integer expected = 3;
        Integer actual = ParallelSearch.getIndex(numbers2, 4);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void whenElementIsAbsent() {
        Integer actual = ParallelSearch.getIndex(numbers1, 14);
        assertThat(actual).isNull();
    }
}