package kotlins

/*
* 搜索旋转排序数组 二分查找发 因为数据是有一种特殊顺序的
*  二分思想
*  中点落在 反转前后（可以通过和left的值进行比较） ，刚好对称
*
*
* */
fun search(nums: IntArray, target: Int): Int {
    return searchFun(nums, 0, nums.size - 1, target)
}

fun searchFun(nums: IntArray, left: Int, right: Int, target: Int): Int {
    if (left > right) return -1
    if (nums[left] == target) return left
    if (nums[right] == target) return right
    val mid = (left + right) shr 1
    if (nums[mid] == target) return mid
    if(nums[left]<nums[mid]){
        if (target > nums[left] && target < nums[mid]) return searchFun(nums, left, mid - 1, target)
        else return searchFun(nums, mid + 1, right, target)
    }
    else{
        if(target<nums[right]&&target>nums[mid]) return searchFun(nums, mid + 1, right, target)
        else return searchFun(nums, left, mid - 1, target)
    }
}