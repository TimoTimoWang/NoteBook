package kotlins

/*
* 输入：nums = [2,7,11,15], target = 9
* 输出：[0,1]
* 解释：因为 nums[0] + nums[1] == 9 ，返回 [0, 1]
* */
fun twoSum(nums: IntArray, target: Int): IntArray {
    val hashMap = HashMap<Int, Int>()
    for ((index, num) in nums.withIndex()) {
        (target - num).also {
            if(it in hashMap){
                return intArrayOf(hashMap[it]!!,index)
            }
            else hashMap[num] = index
        }
    }
    return intArrayOf()
}