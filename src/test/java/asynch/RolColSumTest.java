package asynch;

import org.junit.jupiter.api.Test;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class RolColSumTest {

    @Test
    public void check3To3SquareMatrixUsingAynchSum() throws ExecutionException, InterruptedException {
        RolColSum.Sums[] expected = new RolColSum.Sums[3];

        for (int i = 0; i < 3; i++) {
            expected[i] = new RolColSum.Sums();
        }
        expected[0].setRowSum(6);
        expected[0].setColSum(3);
        expected[1].setRowSum(9);
        expected[1].setColSum(9);
        expected[2].setRowSum(12);
        expected[2].setColSum(15);

        RolColSum.Sums[] result;
        int rows = 3;
        int clms = 3;
        int[][] matrix = new int[rows][clms];
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < clms; c++) {
                matrix[r][c] = r + 2 * c;
            }
        }
        result = RolColSum.asyncSum(matrix);

        assertArrayEquals(expected, result);
    }

    @Test
    public void check3To3SquareMatrixUsingSimpleSum() throws ExecutionException, InterruptedException {
        RolColSum.Sums[] expected = new RolColSum.Sums[3];

        for (int i = 0; i < 3; i++) {
            expected[i] = new RolColSum.Sums();
        }
        expected[0].setRowSum(6);
        expected[0].setColSum(3);
        expected[1].setRowSum(9);
        expected[1].setColSum(9);
        expected[2].setRowSum(12);
        expected[2].setColSum(15);

        RolColSum.Sums[] result;
        int rows = 3;
        int clms = 3;
        int[][] matrix = new int[rows][clms];
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < clms; c++) {
                matrix[r][c] = r + 2 * c;
            }
        }
        result = RolColSum.sum(matrix);

        assertArrayEquals(expected, result);
    }
}