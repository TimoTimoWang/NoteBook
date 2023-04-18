package binarysearch

//无序数组，任意两个相邻的数不相等，返回一个局部最小值  或者边界最小值
//局部最小 ：  a<lefta and a<righta  or
//每次二分  局部最小一定位于每次二分有一边小于的那一边 如果两边都小 则 说明局部最小
fun bsawesome(list: IntArray): Int {
    var left= 0
    var right = list.size-1
    var index = 0
    while (left<right){
        val mid = (left+right) shr 1
        when{
            list[mid]>list[mid-1]  -> {
                right = mid-1
            }
             list[mid]>list[mid+1] ->{
                left = mid+1
            }
            else->{
                return mid
            }
        }
    }
    return left
}