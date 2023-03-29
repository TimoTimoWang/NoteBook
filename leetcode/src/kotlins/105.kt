package kotlins

import java.util.*
import kotlin.collections.ArrayList

class TreeNode(var `val`: Int,var left: TreeNode? = null, var right: TreeNode? = null)

/*
* 思路： 建立两个队列，q1，q2  遍历q1层元素时，q1层元素的子节点入队q2层，下次遍历q2层，q2的子元素入队q1层   q1 q2 同时是空终止遍历
* 初始条件：q1 入队 头节点
* 开始便利
*
* */
fun levelOrder(root: TreeNode?): List<List<Int>> {
    val result = arrayListOf<List<Int>>()
    if (root == null) return result
    val q1 = LinkedList<TreeNode>()
    val q2 = LinkedList<TreeNode>()
    q1 += root
    var current = ArrayList<Int>()
    while (!q1.isEmpty() || !q2.isEmpty()) {
        while (!q1.isEmpty()) {
            q1.pop().also {
                current += it.`val`
                it.left?.let { q2 += (it) }
                it.right?.let { q2 += (it) }
            }
        }
        if (!q2.isEmpty()) {
            result += current
            current = ArrayList<Int>()
        } else break
        while (!q2.isEmpty()) {
            q2.pop().also {
                current += (it.`val`)
                it.left?.let { q1 += (it) }
                it.right?.let { q1 += (it) }
            }
        }
        if (!q1.isEmpty()) {
            result += current
            current = ArrayList<Int>()
        } else break
    }
    return result
}