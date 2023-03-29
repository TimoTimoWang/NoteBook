package kotlins

import java.util.*
import kotlin.collections.ArrayList

/*
*  层次遍历  队列 换成栈
*   两个栈 一层 存 一个栈中， 当前栈空了 切换另外一个栈
* */
fun zigzagLevelOrder(root: TreeNode?): List<List<Int>> {
    val result = ArrayList<List<Int>>()
    root?.let {
        val stack1 = Stack<TreeNode>()
        val stack2 = Stack<TreeNode>()
        stack1.push(root)
        while (stack1.isNotEmpty() || stack2.isNotEmpty()) {
            ArrayList<Int>().also { cur ->
                while (stack1.isNotEmpty()) {
                    stack1.pop().apply {
                        left?.let { stack2.push(it) }
                        right?.let { stack2.push(it) }
                        cur.add(this.`val`)
                    }
                }
            }
            ArrayList<Int>().also { cur ->
                while (stack2.isNotEmpty()) {
                    stack2.pop().apply {
                        right?.let { stack1.push(it) }
                        left?.let { stack1.push(it) }
                        cur.add(this.`val`)
                    }
                }
                if (cur.isNotEmpty()) {
                    result.add(cur)
                }
            }
        }
    }
    return result
}

 fun main() {
     val tree = TreeNode(3, TreeNode(9), TreeNode(20, TreeNode(15), TreeNode(7)))
     val aaa = zigzagLevelOrder(tree)
     println(aaa)
 }