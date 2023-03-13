package kotlins
//三数之和
/* 假设 a<=b<=c
* 先进行排序，通过 排序后的手段防止 防止 a b c 三个位置上的数据重复去相同的值
* 采用控制变量法  固定 a  寻找 b c
* 由于 b+c 为一个定值   所以问题转化为 寻找 b+c 等于 -a 的所有  b，c
* 所以假设  b+c = -a 成立时 if b'>b  ->   c'<c  所以 b 和 c 可以同时变化
*
* * */

fun threeSum(nums: IntArray): List<List<Int>> {
    nums.sort()
    val ret = mutableListOf<List<Int>>()
    val lastIndex = nums.size - 1
    for (i in 0..lastIndex - 2) {
        val a = nums[i]
        if (i > 0 && nums[i] == nums[i - 1]) continue
        var bIndex = i + 1
        var cIndex = lastIndex
        while (bIndex < cIndex) {
            val bc = nums[bIndex] + nums[cIndex]
            when {
                bc + a == 0 -> {
                    ret += listOf(a, nums[bIndex], nums[cIndex])
                    while (bIndex < cIndex && nums[bIndex + 1] == nums[bIndex]) {
                        bIndex++
                    }
                    if (bIndex < cIndex) bIndex++
                    while (bIndex < cIndex && nums[cIndex - 1] == nums[cIndex]) {
                        cIndex--
                    }
                    if (bIndex < cIndex) cIndex--
                }
                //bc 大了 让c减小
                bc + a > 0 -> {
                    while (bIndex < cIndex && nums[cIndex - 1] == nums[cIndex]) {
                        cIndex--
                    }
                    if (bIndex < cIndex) cIndex--
                }
                //bc 小了 让b增大
                bc + a < 0 -> {
                    while (bIndex < cIndex && nums[bIndex + 1] == nums[bIndex]) {
                        bIndex++
                    }
                    if (bIndex < cIndex) bIndex++
                }
            }
        }
    }
    return ret
}

fun main() {
    val a : IntArray = intArrayOf(-1,0,1,2,-1,-4)
    println(threeSum(a))
}