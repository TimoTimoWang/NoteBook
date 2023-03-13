package kotlins

//快速查找法
fun findKthLargest(nums: IntArray, k: Int): Int {
    return nums[getK(nums, nums.size - k, 0, nums.size - 1)]
}

fun getK(nums: IntArray, targetIndex: Int, left: Int, right: Int): Int {
    val piovtIndex = quickSearch(nums, left, right)
    if (piovtIndex == targetIndex) {
        return targetIndex
    } else if (piovtIndex < targetIndex) {
        return getK(nums, targetIndex, piovtIndex + 1, right)
    } else {
        return getK(nums, targetIndex, left, piovtIndex - 1)
    }
}

fun quickSearch(nums: IntArray, left: Int, right: Int): Int {
    var left1 = left
    var right1 = right
    var pivot = nums[left1]
    while (left1 < right1) {
        while (left1 < right1 && nums[right1] >= pivot) {
            right1--
        }
        if (left1 < right1) {
            nums.swap(left1, right1)
            left1++
        }
        while (left1 < right1 && nums[left1] < pivot) {
            left1++
        }
        if (left1 < right1) {
            nums.swap(left1, right1)
            right1--
        }
    }
    return left1
}

fun IntArray.swap(index1: Int, index2: Int) {
    this[index1] = this[index2].also { this[index2] = this[index1] }
}


//优先队列法
//大根对堆
//大根堆策略
val IntArray.compare1: (Int, Int) -> Int
    get() = { index1: Int, index2: Int -> if (this[index1] > this[index2]) index1 else index2 }

//小根堆策略
val IntArray.compare2: (Int, Int) -> Int
    get() = { index1: Int, index2: Int -> if (this[index1] < this[index2]) index1 else index2 }


abstract class heap<T>(val nums: Array<T>) {
    var maxIndex = nums.size - 1

    init {
        for (i in (maxIndex - 1) / 2 downTo 0) {
            shuffle(i)
        }
    }

    fun shuffle(fromIndex: Int) {
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
        //if (maxIndex < 0) return -1
        nums.swap(0, maxIndex)
        maxIndex--
        shuffle(0)
        return nums[maxIndex + 1]
    }
}

fun findKthLargest2(nums: Array<Int>, k: Int): Int {

    return bigEndHeap(nums).let {
        var k = k
        while (k > 0) {
            it.getRoot()
            k--
        }
        it.getRoot()
    }
}

class bigEndHeap(nums: Array<Int>) : heap<Int>(nums) {
    override fun Array<Int>.compare(index1: Int, index2: Int): Int {
        return if (nums[index1] > nums[index2]) index1 else index2
    }
}


//class bigEndHeap2<Int>(nums: Array<Int>) :heap<Int>(nums){
//    override fun Array<Int>.compare(index1: Int, index2: Int): Int {
//        return if (nums[index1] > nums[index2]) index1 else index2
//    }
//}

fun main() {
   // val intArray = Array(3, 2, 1, 5, 6, 4)
    var intarray = arrayOf(3, 2, 1, 5, 6, 4)

    println(findKthLargest2(intarray, 2))
  //  println(findKthLargest2(intArray, 2))

}

