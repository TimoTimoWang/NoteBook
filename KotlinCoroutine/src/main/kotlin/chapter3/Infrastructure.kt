package chapter3

import kotlin.coroutines.*

val continuation = suspend {
    println("Hello")
    10
}.createCoroutine(object : Continuation<Int> {
    override val context: CoroutineContext
        get() = EmptyCoroutineContext

    override fun resumeWith(result: Result<Int>) {
        result.onSuccess {
            println(it)
        }
        println("World")
    }
})

suspend fun sunspendFun() = suspendCoroutine<Int> { continuation ->
//    thread {
    println(Thread.currentThread().name + " 1")
    continuation.resumeWith(Result.success(10))
//    }
}

val continuation2 = suspend {
    sunspendFun()
}.createCoroutine(object : Continuation<Int> {
    override val context: CoroutineContext
        get() = EmptyCoroutineContext

    override fun resumeWith(result: Result<Int>) {
        result.onSuccess {
            println(Thread.currentThread().name + " 2")
            println(it)
        }
    }
})


suspend fun main() {
//    continuation2.resumeWith(Result.success(Unit))
    sunspendFun()
    println(Thread.currentThread().name + " 3")
    continuation.resume(Unit)
}