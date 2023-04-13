package chapter1;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MatrixGenerator {
    public static double[][] generate(int rows, int cols) {
        var random = new Random();
        var matrix = new double[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matrix[i][j] = random.nextDouble();
            }
        }
        return matrix;
    }
}

class SerialMutiplier {
    public static double[][] multiply(double matrix1[][], double matrix2[][]) {
        //满足 第一份矩阵的列数等于第二个矩阵的行数
        var nrows = matrix1.length;
        var nmid = matrix1[0].length;
        var ncols = matrix2[0].length;
        var nmatrix = new double[nrows][ncols];
        for (int i = 0; i < nrows; i++) {
            for (int j = 0; j < ncols; j++) {
                double tmp = 0.0;
                for (int k = 0; k < nmid; k++) {
                    tmp += matrix1[i][k] * matrix2[k][j];
                }
                nmatrix[i][j] = tmp;
            }
        }
        return nmatrix;
    }
}

class IndividualmutipklierTask implements Runnable {
    public double[][] matrix1;
    public double[][] matrix2;
    public double[][] result;
    public int i;
    public int j;

    public IndividualmutipklierTask(double[][] matrix1, double[][] matrix2, double[][] result, int i, int j) {
        this.matrix1 = matrix1;
        this.matrix2 = matrix1;
        this.result = result;
        this.i = i;
        this.j = j;
    }

    @Override
    public void run() {
        for (int k = 0; k < matrix1[0].length; k++) {
            result[i][j] += matrix1[i][k] * matrix2[k][j];
        }
    }
}

class ParallelIndividualMultiplier {
    List<Thread> threads = new ArrayList<>();

    public ParallelIndividualMultiplier(double[][] matrix1, double[][] matrix2, double[][] result) throws InterruptedException {
        for (int i = 0; i < matrix1.length; i++) {
            for (int j = 0; j < matrix2[0].length; j++) {
                Thread task = new Thread(new IndividualmutipklierTask(matrix1, matrix2, result, i, j));
                task.start();
                threads.add(task);
                if (threads.size() % 10 == 0) {
                    for (var thread : threads) {
                        thread.join();
                    }
                    threads.clear();
                }
            }
        }
    }
}

class RowmutipklierTask implements Runnable {
    public double[][] matrix1;
    public double[][] matrix2;
    public double[][] result;
    public int i;
    public int j;

    public RowmutipklierTask(double[][] matrix1, double[][] matrix2, double[][] result, int i) {
        this.matrix1 = matrix1;
        this.matrix2 = matrix1;
        this.result = result;
        this.i = i;
    }

    @Override
    public void run() {
        for (int j = 0; j < matrix2[0].length; j++) {
            double tmp = 0.0;
            for (int k = 0; k < matrix2.length; k++) {
                tmp += matrix1[i][k] * matrix2[k][j];
            }
            result[i][j] = tmp;
        }
    }
}

class ParallelRowMultiplier {
    List<Thread> threads = new ArrayList<>();

    public ParallelRowMultiplier(double[][] matrix1, double[][] matrix2, double[][] result) throws InterruptedException {
        for (int i = 0; i < matrix1.length; i++) {
            Thread task = new Thread(new RowmutipklierTask(matrix1, matrix2, result, i));
            task.start();
            threads.add(task);
            if (threads.size() % 10 == 0) {
                for (var thread : threads) {
                    //阻塞父线程 直到子线程运行完成
                    thread.join();
                }
                threads.clear();
            }

        }
    }
}










