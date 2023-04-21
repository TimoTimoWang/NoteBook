package chapter3

import org.apache.commons.math3.util.MathArrays.distance

/*
* knn(k-nearest neighbor)算法思想：
*  一种分类算法思想：
* 假设一个已经正确分类的数据集合，对于新来的样本，在正确分类的样本集合中寻找到里他最近的(欧式距离)K个样本，k个样本中的多数的属于哪个分类，
* 他就是哪个分类
* */
class Sample(val attributes: DoubleArray, val lable: Int)

val dataset: List<Sample> = ArrayList()

class KNNClassfier(val dataset: List<Sample>, val K: Int) {
    fun classfy(sample: Sample): Int {
        //求出样本与数据集中每个样本的欧式距离
        val distanceList = dataset.map { Distance(it, distance(it.attributes, sample.attributes)) }.sorted()
        //求前k个元素最大的类别
        //val flagCount = distanceList.take(K).groupBy { it.sample.lable }.mapValues { it.value.size }
        val flagCount = distanceList.take(K).fold(mutableMapOf<Int, Int>()) { acc, distance ->
            acc[distance.sample.lable] = acc.getOrDefault(distance.sample.lable, 0) + 1
            acc
        }
        val maxFlag = flagCount.maxBy { it.value }!!.key
        return maxFlag
    }
}

class Distance(val sample: Sample, val distance: Double) : Comparable<Distance> {
    override fun compareTo(other: Distance): Int {
        return distance.compareTo(other.distance)
    }
}