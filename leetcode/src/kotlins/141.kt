package kotlins

//快慢指针，如果有环则一定相遇
fun hasCycle(head: ListNode?): Boolean {
    if (head == null || head.next == null) return false
    var quick = head!!.next!!.next
    var slow = head
    while (quick != null && quick != slow) {
        quick = quick!!.next?.next
        slow = slow!!.next
    }
    return if (quick == slow) return true else false
}

fun hasCycle2(head: ListNode?): ListNode? {
    if (head == null || head.next == null) return null
    var quick = head!!.next!!.next
    var slow = head
    while (quick != null && quick != slow) {
        quick = quick!!.next?.next
        slow = slow!!.next
    }
    return if (quick == slow) quick else null

}

fun cycleLength(head: ListNode?): Int {
    var count = 1
    var cur = head!!.next
    while (cur != head) {
        cur = cur!!.next
        count++
    }
    return count
}

fun detectCycle(head: ListNode?): ListNode? {
    return hasCycle2(head)?.let { cycleLength(it) }?.let {
        var cur1 = head
        repeat(it) {
            cur1 = cur1!!.next
        }
        var cur2 = head
        while (cur1 != cur2) {
            cur1 = cur1!!.next
            cur2 = cur2!!.next
        }
        cur1
    }
}


fun main() {
    var head = ListNode(3)
    var node1 = ListNode(2).also { head.next = it }
    var node2 = ListNode(0).also { node1.next = it }
    var node3 = ListNode(-4).also { node2.next = it }
    node3.next = node1
    println(detectCycle(head)?.`val`)
}