package sort

fun quickSort(nums: IntArray, left: Int, right: Int) {
    if (left >= right) return
    val pivot = partition2(nums, left = left, right = right)
    quickSort(nums, left, pivot - 1)
    quickSort(nums, pivot + 1, right)
}

fun partition2(array: IntArray, left: Int, right: Int): Int {
    val pivot = array[left]
    var left = left
    var right = right
    while (left < right) {
        while (left < right && array[right] > pivot) {
            right--
        }
        if (left < right) {
            array[left] = array[right]
            left++
        }
        while (left < right && array[left] <= pivot) {
            left++
        }
        if (left < right) {
            array[right] = array[left]
            right--
        }
    }
    array[left] = pivot
    return left
}

//快速排序优化
fun partition(array: IntArray, left: Int, right: Int): Int {
    val pivot = array[left]
    var swapIndex = left - 1
    for (index in left..right - 1) {
        if (array[swapIndex] <= pivot) {
            swapIndex++
            if (swapIndex != index) {
                array[swapIndex] = array[index].also { array[index] = array[swapIndex] }
            }
        }
    }
    array[swapIndex++] = array[right].also { array[right] = array[swapIndex] }
    return swapIndex
}


//快速排序的优化
//优化策略1：主元（Pivot）的选取 针对快速排序 pivot 如果越靠近中位数，快速排序的效果越好

//优化策略2：阈值的选取
//数组长度大于阈值的，使用归并排序策略。
//数组长度小于阈值的，使用直接插入排序。
//通过这种方式，归并排序避免了针对小数组时候的递归（递归层次增加最多的场景，就是大量的小数组），从而减轻了JVM的负担。

//优化策略3：三路划分







