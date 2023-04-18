package chapter1;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class MatrixGenerator {
    public static double[][] generate(int rows, int cols) {
        Random random = new Random();
        double[][] matrix = new double[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matrix[i][j] = random.nextDouble();
            }
        }
        return matrix;
    }
}

class SerialMutiplier {
    public static void multiply(double matrix1[][], double matrix2[][],double result[][]) {
        //满足 第一份矩阵的列数等于第二个矩阵的行数
        int nrows = matrix1.length;
        int nmid = matrix1[0].length;
        int ncols = matrix2[0].length;
        for (int i = 0; i < nrows; i++) {
            for (int j = 0; j < ncols; j++) {
                double tmp = 0.0;
                for (int k = 0; k < nmid; k++) {
                    tmp += matrix1[i][k] * matrix2[k][j];
                }
                result[i][j] = tmp;
            }
        }
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

    public static void multiply(double[][] matrix1, double[][] matrix2, double[][] result) throws InterruptedException {
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < matrix1.length; i++) {
            for (int j = 0; j < matrix2[0].length; j++) {
                Thread task = new Thread(new IndividualmutipklierTask(matrix1, matrix2, result, i, j));
                task.start();
                threads.add(task);
                if (threads.size() % 10 == 0) {
                    for (Thread thread : threads) {
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

    public static void multiply(double[][] matrix1, double[][] matrix2, double[][] result) throws InterruptedException {
        List<Thread> threads = new ArrayList();
        for (int i = 0; i < matrix1.length; i++) {
            Thread task = new Thread(new RowmutipklierTask(matrix1, matrix2, result, i));
            task.start();
            threads.add(task);
            if (threads.size() % 10 == 0) {
                //阻塞父线程 直到子线程运行完成
                for (Thread thread : threads) {
                    thread.join();
                }
                threads.clear();
            }
        }
    }
}

class GroupMultiplierTask implements Runnable {
    public double[][] matrix1;
    public double[][] matrix2;
    public double[][] result;
    public int startIndex;
    public int endIndex;

    public GroupMultiplierTask(double[][] matrix1, double[][] matrix2, double[][] result, int startIndex, int endIndex) {
        this.matrix1 = matrix1;
        this.matrix2 = matrix2;
        this.result = result;
        this.startIndex = startIndex;
        this.endIndex = endIndex;

    }

    @Override
    public void run() {
        for (int i = startIndex; i < endIndex; i++) {
            for (int j = 0; j < matrix2[0].length; j++) {
                double tmp = 0.0;
                for (int k = 0; k < matrix2.length; k++) {
                    tmp += matrix1[i][k] * matrix2[k][j];
                }
                result[i][j] = tmp;
            }
        }
    }
}

class GroupMultiplier{
    public static void  multiply(double[][] matrix1, double[][] matrix2, double[][] result) throws InterruptedException {
        List<Thread> threadsthreads = new ArrayList();
        int numThreads = Runtime.getRuntime().availableProcessors();
        int step = matrix1.length / numThreads;
        for (int i = 0; i < numThreads; i += step) {
            Thread thread = new Thread(new GroupMultiplierTask(matrix1, matrix2, result, i, step));
            threadsthreads.add(thread);
        }
        for (Thread thread : threadsthreads) {
            thread.join();
        }
    }
}










