package binarysearch

import java.lang.Exception

//二分查找一个数据在有序列表中存在
fun binarySearch(list:IntArray,left:Int,right:Int,value: Int):Boolean {
    val mid = (left+right) shr 1
    return  when {
        left>right -> false
        list[mid] == value -> true
        list[mid] > value -> binarySearch(list,left,mid-1,value)
        list[mid] < value -> binarySearch(list,mid+1,right,value)
        else -> throw Exception()
    }
}