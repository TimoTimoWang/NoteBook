package kotlins


data class Node(val value: Int) {
    var left: Node? = null
    var right: Node? = null
    override fun toString(): String {
        return "Node(value=${value},left=${left},right=${right})"
    }
}

object randomTree {
    val container = arrayListOf<Node>()

    fun genRandowTree(nums: Int): Node {
        var count = 1
        val root = Node((0..100).random())
        container += root
        while (count < nums) {
            container[(0..container.size - 1).random()].also {
                val a = Node((0..100).random())
                when {
                    it.left == null && it.right == null -> {
                        if ((0..1).random() == 0) {
                            it.left = a
                        } else {
                            it.right = a
                        }
                        container.add(a)
                    }
                    it.left == null -> {
                        it.left = a
                        container.add(a)
                        container.remove(it)
                    }
                    it.right == null -> {
                        it.right = a
                        container.add(a)
                        container.remove(it)
                    }
                }
            }
            count++
        }
        return root
    }
}

fun main() {
    println(randomTree.genRandowTree(10).toString())
}