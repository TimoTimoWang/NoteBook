package kotlins
class LRUCache(var capacity: Int) {
    //hashtable+queue的结构，hashtable保障o(1)的时间复杂度，list维护最近最久的顺序
    //1. put时：
    //1.1 如果出现过，则将queue中的key移动到对尾，ele改变
    //1.2 如果没出现过,如果没有超出容量，插入到key队尾部和<k,v>->hashtable
    //1.3 如果没出现过，如果超出容量，删除queue中对头的key，删除hashtable中对应<k,v>，插入<k,v>到hashtable和key->对尾
    //2.get时：
    //2.1 如果不存在，返回-1
    //2.2 如果存在，移动key 到对尾

    val hashtable = hashMapOf<Int, Node>()
    val headNode = Node()
    val tailNode = Node()

    init {
        headNode.next = tailNode
        tailNode.prev = headNode
    }


    var count = 0

    fun get(key: Int): Int {
        val node = hashtable[key]
        return node?.let {
            unLink(it)
            addTail(it)
            it.value
        } ?: -1
    }

    fun put(key: Int, value: Int) {
        when {
            key in hashtable -> {
                val node = hashtable[key]
                node!!.value = value
                unLink(node)
                addTail(node)
            }
            key !in hashtable && count < capacity -> {
                Node(key, value).also {
                    hashtable[key] = it
                    addTail(it)
                    count++
                }
            }
            key !in hashtable && count >= capacity -> {
                delHead().also {
                    hashtable.remove(it.key)
                }
                Node(key, value).also {
                    hashtable[key] = it
                    addTail(it)
                }
            }
        }
    }

    class Node(var key: Int = -1, var value: Int = -1, var prev: Node? = null, var next: Node? = null)

    fun unLink(node: Node) {
        node.prev!!.next = node.next
        node.next!!.prev = node.prev
    }

    fun delHead(): Node {
//        node.next = headNode.next
//        node.prev = headNode
//        headNode.next?.prev = node
//        headNode.next = node
        val temp = headNode.next!!
        headNode.next = headNode.next?.next
        headNode.next?.prev = headNode
        return temp
    }

    fun addTail(node: Node) {
        node.prev = tailNode.prev
        node.next = tailNode
        tailNode.prev?.next = node
        tailNode.prev = node
    }

}

/**
 * Your LRUCache object will be instantiated and called as such:
 * var obj = LRUCache(capacity)
 * var param_1 = obj.get(key)
 * obj.put(key,value)
 */