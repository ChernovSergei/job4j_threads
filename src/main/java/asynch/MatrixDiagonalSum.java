package asynch;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class MatrixDiagonalSum {

    public static int[] asyncSum(int[][] matrix) throws ExecutionException, InterruptedException {
        int n = matrix.length;
        int[] sums = new int[2 * n];
        Map<Integer, CompletableFuture<Integer>> futures = new HashMap<>();
        futures.put(0, getMainDiagonalTask(matrix, 0, n - 1, 0));
        for (int k = 1; k <= n; k++) {
            futures.put(k, getTask(matrix, 0, k - 1, k - 1));
            if (k < n) {
                futures.put(2 * n - k, getTask(matrix, n - k, n - 1, n - 1));
            }
        }
        for (Integer key: futures.keySet()) {
            sums[key] = futures.get(key).get();
        }
        return sums;
    }

    public static CompletableFuture<Integer> getTask(int[][] data, int startRow, int endRow, int startCol) {
        return CompletableFuture.supplyAsync(() -> {
            int sum = 0;
            int col = startCol;
            for (int i = startRow; i <= endRow; i++) {
                sum += data[i][col];
                col--;
            }
            return sum;
        }
        );
    }

    public static CompletableFuture<Integer> getMainDiagonalTask(int[][] data, int startRow, int endRow, int startCol) {
        return CompletableFuture.supplyAsync(() -> {
                    int sum = 0;
                    int col = startCol;
                    for (int i = startRow; i <= endRow; i++) {
                        sum += data[i][col];
                        col++;
                    }
                    return sum;
                }
        );
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        int[] sums;
        int rows = 3;
        int clms = 3;
        int[][] matrix = new int[rows][clms];
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < clms; c++) {
                matrix[r][c] = r + c + 1;
                System.out.print(matrix[r][c] + " ");
            }
            System.out.println();
        }
        sums = asyncSum(matrix);
        for(int r = 0; r < sums.length; r++) {
            System.out.println(sums[r]);
        }
    }
}
