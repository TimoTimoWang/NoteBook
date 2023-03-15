package kotlins

/*
* 最长回文串
* */
//动态规划 p[i,j]==true  = p[i-1,j+1]==ture && s[i]==s[j]
fun longestPalindrome(s: String): String {
    var left = 0
    var maxDiv = 0
    var pMatrix = Array(s.length) { BooleanArray(s.length) { false } }
    //0..1 <=> 0,1
    for (dValue in 0..s.length - 1) {
        for (start in 0..s.length - dValue - 1) {
            pMatrix[start][start + dValue] = when (dValue) {
                0 -> true
                1 -> (s[start] == s[start + dValue]).also {
                    if (it) {
                        left = start
                        maxDiv = dValue
                    }
                }
                else -> (pMatrix[start + 1][start + dValue - 1] && s[start] == s[start + dValue]).also {
                    if (it) {
                        left = start
                        maxDiv = dValue
                    }
                }
            }
        }
    }
    return s.substring(left, left + maxDiv + 1)
}
//中心扩展法
