package asynch;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RolColSum {
    public static class Sums {
        private int rowSum;
        private int colSum;

        public int getColSum() {
            return colSum;
        }

        public void setColSum(int colSum) {
            this.colSum = colSum;
        }

        public int getRowSum() {
            return rowSum;
        }

        public void setRowSum(int rowSum) {
            this.rowSum = rowSum;
        }

        @Override
        public boolean equals(Object o) {
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Sums sums = (Sums) o;
            return rowSum == sums.rowSum && colSum == sums.colSum;
        }

        @Override
        public int hashCode() {
            return Objects.hash(rowSum, colSum);
        }
    }

    public static Sums[] sum(int[][] matrix) {
        Sums[] sums = new Sums[matrix.length];
        int rowSum = 0;
        int colSum = 0;
        for (int key = 0; key < matrix.length; key++) {
            sums[key] = new Sums();
        }
        for (int r = 0; r < matrix.length; r++) {
            rowSum = 0;
            for (int c = 0; c < matrix.length; c++) {
                rowSum += matrix[r][c];
            }
            sums[r].setRowSum(rowSum);
        }
        for (int c = 0; c < matrix.length; c++) {
            colSum = 0;
            for (int r = 0; r < matrix.length; r++) {
                colSum += matrix[r][c];
            }
            sums[c].setColSum(colSum);
        }

        return sums;
    }

    public static CompletableFuture<Integer> getTaskCol(int[][] data, int startCol) {
        return CompletableFuture.supplyAsync(() -> {
                    int sum = 0;
                    for (int i = 0; i < data.length; i++) {
                        sum += data[i][startCol];
                    }
                    return sum;
                }
        );
    }

    public static CompletableFuture<Integer> getTaskRow(int[][] data, int startRow) {
        return CompletableFuture.supplyAsync(() -> {
                    int sum = 0;
                    for (int i = 0; i < data.length; i++) {
                        sum += data[startRow][i];
                    }
                    return sum;
                }
        );
    }

    public static Sums[] asyncSum(int[][] matrix) throws ExecutionException, InterruptedException {
        int n = matrix.length;
        Sums[] sums = new Sums[n];
        Map<Integer, CompletableFuture<Integer>> futures = new HashMap<>();
        for (int k = 0; k < n; k++) {
            futures.put(k, getTaskRow(matrix, k));
            futures.put(k + n, getTaskCol(matrix, k));
        }
        for (int key = 0; key < n; key++) {
            sums[key] = new Sums();
            sums[key].setRowSum(futures.get(key).get());
            sums[key].setColSum(futures.get(key + n).get());
        }
        return sums;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Sums[] sums;
        Sums[] sumsSimple;
        int rows = 3;
        int clms = 4;
        int[][] matrix = new int[rows][clms];
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < clms; c++) {
                matrix[r][c] = r + 2 * c;
                System.out.print(matrix[r][c] + " ");
            }
            System.out.println();
        }
        sums = asyncSum(matrix);
        for (int r = 0; r < sums.length; r++) {
            System.out.print(sums[r].getRowSum() + " ");
            System.out.println(sums[r].getColSum());
        }

        sumsSimple = sum(matrix);
        for (int r = 0; r < sumsSimple.length; r++) {
            System.out.print(sumsSimple[r].getRowSum() + " ");
            System.out.println(sumsSimple[r].getColSum());
        }
    }
}
