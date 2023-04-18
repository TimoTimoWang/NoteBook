package binarysearch

//122222333578888999999 找大于等于2最左侧的位
// 形式化表达：
fun findNearLeft(list: IntArray, value: Int): Int {
    var left = 0
    var right = list.size - 1
    var index = -1
    while (left <= right) {
        val mid = (left + right) shr 1
        when {
            list[mid] < value -> {
                left = mid + 1
            }

            else -> {
                index = mid
                right = mid - 1
            }
        }
    }

    return index
}

//