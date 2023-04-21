package chapter3

import org.apache.commons.math3.util.MathArrays
import java.util.Arrays
import java.util.concurrent.CountDownLatch
import java.util.concurrent.ThreadPoolExecutor

class KnnClassifierParallelIndividual(
    private val dataset: List<Sample>,
    private val K: Int,
    private val excutor: ThreadPoolExecutor,
    private val numThreads: Int
) {
    fun classfy(sample: Sample) {
        val endcontroler = CountDownLatch(dataset.size)
        val distances = ArrayList<Distance>(dataset.size)
        dataset.withIndex().map { computeDistance(distances, it.value, sample, it.index, endcontroler) }
            .forEach(excutor::execute)
        endcontroler.await()
        //distances.sort() 也可以并行的
        distances.sorted()
        val flagCount = distances.take(K).fold(mutableMapOf<Int, Int>()) { acc, distance ->
            acc[distance.sample.lable] = acc.getOrDefault(distance.sample.lable, 0) + 1
            acc
        }
    }

}

class computeDistance(
    val distances: ArrayList<Distance>, val sample1: Sample,
    val sample2: Sample, val sample1index: Int,
    val endcontroler: CountDownLatch
) :
    Runnable {
    override fun run() {
        distances[sample1index] = Distance(sample1, MathArrays.distance(sample1.attributes, sample2.attributes))
        endcontroler.countDown()
    }

}
