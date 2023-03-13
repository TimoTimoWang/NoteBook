package kotlins

import kotlin.math.max

/**
 *  输入：nums = [-2,1,-3,4,-1,2,1,-5,4]
输出：6
解释：连续子数组 [4,-1,2,1] 的和最大，为 6 。
 *  动态规划
 *   求解以i 为结尾的最大最大连续子数组
 *   f[0] = nums[0]
 *   f[i] = max{n[i],f[i-1]+n[i]}
 *   max = max{f[0..i]}
 */
fun maxSubArray(nums: IntArray): Int {
    var maxValue = nums[0]
    var f = nums[0]
    for (i in 1 until nums.size) {
        f = max(nums[i], f + nums[i])
        maxValue = max(f, maxValue)
    }
    return maxValue
}


fun main(){
    val nums = intArrayOf(-2,1,-3,4,-1,2,1,-5,4)
    val ans = maxSubArray(nums)
    println(ans)
}