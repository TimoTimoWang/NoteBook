package chapter1;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@Warmup(iterations = 2, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 2, time = 5, timeUnit = TimeUnit.SECONDS)
@Fork(1)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
public class BatchMatrix {

    public double[][] matrix1;
    public double[][] matrix2;
    public double[][] r1;
    public double[][] r2;
    public double[][] r3;
    public double[][] r4;

    @Setup
    public void init() {
        int length = 2000;
        matrix1 = MatrixGenerator.generate(length, length);
        matrix2 = MatrixGenerator.generate(length, length);
        r1 = new double[length][length];
        r2 = new double[length][length];
        r3 = new double[length][length];
        r4 = new double[length][length];
    }

    @Benchmark
    public void testSerialMutiplier() {
        SerialMutiplier.multiply(matrix1, matrix2, r1);
    }

//    @Benchmark
//    public void testParallelIndividualMultiplier() throws InterruptedException {
//        ParallelIndividualMultiplier.multiply(matrix1, matrix2, r1);
//    }

//    @Benchmark
//    public void testParallelRowMultiplier() throws InterruptedException {
//        ParallelRowMultiplier.multiply(matrix1, matrix2, r1);
//    }

    @Benchmark
    public void testGroupMultiplier() throws InterruptedException {
        GroupMultiplier.multiply(matrix1, matrix2, r1);
    }

//    public static void main(String[] args) throws RunnerException {
//        Options opt = new OptionsBuilder()
//                .include(BatchMatrix.class.getSimpleName())
//                .warmupIterations(2)
//                .measurementIterations(2)
//                .forks(1)
//                .build();
//        new Runner(opt).run();
//    }
}
