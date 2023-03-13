package kotlins
//进行拆分
//1. reverseKGroup 递归 k个值为一组 让当前组的next 指向 reverseKGroup（next）处理好的新head  然后在处理当前组链表
//2. 当前组链表的处理，就是反转当前组的元素 及前 k 个元素
//反转前 k 个元素  递归 reverseK（k--）元素  后面链表的 头为 新头 后面链表的尾部（当前值的next） 指向当前值 当前值的next 指向后面链表的尾部（当前值的next）next
/**
 * Example:
 * var li = ListNode(5)
 * var v = li.`val`
 * Definition for singly-linked list.
 * class ListNode(var `val`: Int) {
 *     var next: ListNode? = null
 * }
 */
class ListNode(var `val`: Int) {
    var next: ListNode? = null
}

fun reverseKGroup(head: ListNode?, k: Int): ListNode? {
    if (head == null) return null
    var count = k
    var cur = head!!
    var last: ListNode? = null
    while (count > 1) {
        if (cur.next != null) {
            count--
            cur = cur.next!!
        } else {
            return head
        }
    }
    /**
     * 1->2->3->4->5  last = 5
     * */
    last = reverseKGroup(cur.next, k)
    /**
     * 1->2->3<-4 3->5  last = 4
     * */
    cur.next = last
    /**
     * 1->2 3<-4 2->4 3->5
     * */
    val headNew = reverseK(head, k)
    /**
     * 1<-2  3<-4  1->4 3->5 headNew = 2
     * */

    return headNew
}

fun reverseK(head: ListNode, k: Int): ListNode {
    var k = k
    if (k == 1) {
        return head
    }
    val last = reverseK(head.next!!, --k)
    val tmp = head.next!!.next
    head.next!!.next = head
    head.next = tmp
    return last
}
