package kotlins

import kotlin.math.max
import kotlin.math.min

/*
* f(n)min = min{f(n-1)min ,cur}
* g(n) = max{g(n-1),cur-f(n)min}
* */
fun maxProfit(prices: IntArray): Int {
    if (prices.size == 0) return 0
    return g(prices, prices.size - 1)

}

fun g(prices: IntArray, lastIndex: Int): Int {
    return if (lastIndex == 0) 0 else max(g(prices, lastIndex - 1), prices[lastIndex] - f(prices, lastIndex))
}

fun f(prices: IntArray, lastIndex: Int): Int {
    return if (lastIndex == 0) 0 else min(f(prices, lastIndex - 1), prices[lastIndex])
}


fun maxProfit2(prices: IntArray): Int {
    if (prices.size == 0) return 0
    var curMin = prices[0]!!
    var curMaxProfit:Int  =0
    for(price in prices){
        curMin = Math.min(curMin,price)
        curMaxProfit= Math.max(curMaxProfit,price-curMin)
    }
    return curMaxProfit
}

fun main (){
    println(maxProfit2(intArrayOf(7,1,5,3,6,4)))
}
