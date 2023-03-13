package sort

abstract class Heap<T>(val nums: Array<T>) {
    var maxIndex = nums.size - 1

    init {
        for (i in (maxIndex - 1) / 2 downTo 0) {
            shuffle(i)
        }
    }

    fun shuffle(fromIndex: Int) {
        if(fromIndex == maxIndex) return
        var index = fromIndex
        while (index <= (maxIndex - 1) / 2) {
            val maxChildIndex =
                if (2 * index + 2 > maxIndex) 2 * index + 1 else nums.compare(2 * index + 1, 2 * index + 2)
            if (nums.compare(maxChildIndex, index) == maxChildIndex) {
                nums.swap(maxChildIndex, index)
                index = maxChildIndex
            } else break
        }
    }

    abstract fun Array<T>.compare(intdex1: Int, index1: Int): Int

    fun Array<T>.swap(index1: Int, index2: Int) {
        this[index1] = this[index2].also { this[index2] = this[index1] }
    }

    fun getRoot(): T {
        return nums[0].also {
            nums.swap(0, maxIndex)
            maxIndex--
            shuffle(0)
        }
    }
}

class BigEndHeap(nums: Array<Int>) : Heap<Int>(nums) {
    override fun Array<Int>.compare(index1: Int, index2: Int): Int {
        return if (nums[index1] > nums[index2]) index1 else index2
    }
}

fun main() {
    val nums = arrayOf(2, 4, 6, 1, 4, 3, 10)
    val numsHeap = BigEndHeap(nums)
    while (numsHeap.maxIndex >= 0) {
        println(numsHeap.getRoot())
    }
}