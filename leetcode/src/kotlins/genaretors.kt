package kotlins

import kotlin.coroutines.*

interface Generator<T> {
    operator fun iterator(): Iterator<T>
}

sealed class State {
    class NotReady(val continuation: Continuation<Unit>) : State()
    class Ready<T>(val continuation: Continuation<Unit>, val nextValue: T) : State()
    object Done : State()
}

//生成器协程作用域 提供在协程体内中可以调用的方法或者状态
abstract class GeneratorScope<T> {
    abstract suspend fun yeildn(value: T)
}

class GeneratorIterator<T>(
    private val block: suspend GeneratorScope<T>.(T) -> Unit,
    private val parameter: T
) : GeneratorScope<T>(), Iterator<T>, Continuation<Any?> {
    override val context: CoroutineContext = EmptyCoroutineContext
    private var state: State

    override fun hasNext(): Boolean {
        resume()
        return state != State.Done
    }

    override fun next(): T {
        return when (val cur = state) {
            is State.NotReady -> {
                resume()
                return next()
            }

            is State.Ready<*> -> {
                state = State.NotReady(cur.continuation)
                (cur as State.Ready<T>).nextValue
            }

            State.Done -> throw IllegalStateException("333")
        }
    }

    override fun resumeWith(result: Result<Any?>) {
        state = State.Done
        result.getOrThrow()
    }

    init {
        val coroutineBlock: suspend GeneratorScope<T>.() -> Unit = { block(parameter) }
        val start = coroutineBlock.createCoroutine(this, this)
        state = State.NotReady(start)

    }

    override suspend fun yeildn(value: T) = suspendCoroutine<Unit> { continuation ->
        state = when (state) {
            is State.NotReady -> State.Ready(continuation, value)
            is State.Ready<*> -> throw IllegalStateException("111")
            State.Done -> throw IllegalStateException("222")
        }
    }

    private fun resume() {
        when (val cur = state) {
            is State.NotReady -> cur.continuation.resume(Unit)
            else -> throw IllegalStateException("111")
        }
    }
}

fun<T> generator(block:suspend GeneratorScope<T>.(T)->Unit): (T) -> GeneratorIterator<T> {
    return {
        paramter:T -> GeneratorIterator(block,paramter)
    }
}

fun  main(){
    val nums = generator { start: Int ->
        for (i in 0.. 5) {
            yeildn(start + i)
        }
    }
    val gen = nums(10)
    for (i in gen){
        println(i)
    }
}