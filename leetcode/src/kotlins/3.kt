package kotlins

import sun.nio.cs.Surrogate

fun lengthOfLongestSubstring(s: String): Int {
    var winLeft = 0
    var winRight = 0
    var maxSize = 0
    var set = mutableSetOf<Char>()
    while (winRight < s.length) {
        if (s[winRight] !in set) {
            set.add(s[winRight++])
            maxSize = maxOf(maxSize, winRight - winLeft)
        } else {
            set.remove(s[winLeft++])
        }
    }
    generateSequence(a)
    return maxSize
}