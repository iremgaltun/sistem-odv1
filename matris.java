import java.util.Random;

public class matris {

    private static final int MATRIX_LENGTH = 5;
    private static final int RANDOM_RANGE = 10;
    private static final int NTHREAD = MATRIX_LENGTH;

    private static final int[][] matrixA = new int[MATRIX_LENGTH][MATRIX_LENGTH];
    private static final int[][] matrixB = new int[MATRIX_LENGTH][MATRIX_LENGTH];
    private static final int[][] matrixResult = new int[MATRIX_LENGTH][MATRIX_LENGTH];

    public static void main(String[] args) {

        initMatrices();

        Thread[] threads = new Thread[NTHREAD];
        for (int i = 0; i < NTHREAD; i++) {
            final int row = i;
            threads[i] = new Thread(() -> calculateRow(row));
            threads[i].start();
        }

        for (int i = 0; i < NTHREAD; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


        reportResult();
    }

    private static void initMatrices() {
        Random random = new Random();
        for (int i = 0; i < MATRIX_LENGTH; i++) {
            for (int j = 0; j < MATRIX_LENGTH; j++) {
                matrixA[i][j] = random.nextInt(RANDOM_RANGE);
                matrixB[i][j] = random.nextInt(RANDOM_RANGE);
            }
        }
    }

    private static void calculateRow(int row) {
        for (int col = 0; col < MATRIX_LENGTH; col++) {
            int sum = 0;
            for (int k = 0; k < MATRIX_LENGTH; k++) {
                sum += matrixA[row][k] * matrixB[k][col];
            }
            synchronized (matrixResult) {
                matrixResult[row][col] = sum;
            }
        }
    }

    private static void reportResult() {
        for (int i = 0; i < MATRIX_LENGTH; i++) {
            for (int j = 0; j < MATRIX_LENGTH; j++) {
                System.out.printf("%4d", matrixResult[i][j]);
            }
            System.out.println();
        }
    }
}
