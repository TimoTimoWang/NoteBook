package chapter4

import kotlinx.coroutines.runBlocking
import java.util.concurrent.atomic.AtomicReference
import kotlin.coroutines.*

sealed class Status {
    class Create(val continuation: Continuation<Unit>) : Status()

    //协程内部调用后挂起
    class Yielded<P>(val continuation: Continuation<P>) : Status()

    //协程外部调用resume后恢复
    class Resumed<R>(val continuation: Continuation<R>) : Status()
    object Dead : Status()
}

interface CoroutinueScope<P, R> {
    val parameter: P?
    suspend fun yeildn(value: R): P
}

class Coroutinue<P, R>(
    override val context: CoroutineContext = EmptyCoroutineContext,
    private val block: suspend CoroutinueScope<P, R>.(P) -> R
) : Continuation<R> {
    override fun resumeWith(result: Result<R>) {
        val previousStatus = status.getAndUpdate {
            when (it) {
                is Status.Create -> throw IllegalStateException("111")
                is Status.Yielded<*> -> throw IllegalStateException("222")
                is Status.Resumed<*> -> Status.Dead
                Status.Dead -> throw IllegalStateException("333")
            }
        }
        (previousStatus as? Status.Resumed<R>)?.continuation?.resumeWith(result)
    }

    companion object {
        fun <P, R> create(
            context: CoroutineContext = EmptyCoroutineContext,
            block: suspend CoroutinueScope<P, R>.(P) -> R
        ): Coroutinue<P, R> {
            return Coroutinue(context, block)
        }
    }

    private val scope = object : CoroutinueScope<P, R> {
        override var parameter: P? = null
        override suspend fun yeildn(value: R): P = suspendCoroutine { continuation ->
            val previousStatus = status.getAndUpdate {
                when (it) {
                    is Status.Create -> throw IllegalStateException("111")
                    is Status.Yielded<*> -> throw IllegalStateException("222")
                    is Status.Resumed<*> -> Status.Yielded(continuation)
                    Status.Dead -> throw IllegalStateException("333")
                }
            }
            (previousStatus as? Status.Resumed<R>)?.continuation?.resume(value)
        }
    }

    private val status: AtomicReference<Status>

    init {
        val coroutinueBlock: suspend CoroutinueScope<P, R>.() -> R = { block(parameter!!) }
        status = AtomicReference(Status.Create(coroutinueBlock.createCoroutine(scope, this)))
    }

    suspend fun resume(value: P): R = suspendCoroutine { continuation ->
        val previousStatus = status.getAndUpdate {
            when (it) {
                is Status.Create -> {
                    scope.parameter = value
                    Status.Resumed(continuation)
                }

                is Status.Yielded<*> -> Status.Resumed(continuation)
                is Status.Resumed<*> -> throw IllegalStateException("555")
                Status.Dead -> throw IllegalStateException("666")
            }
        }
        when (previousStatus) {
            is Status.Create -> previousStatus.continuation.resume(Unit)
            is Status.Yielded<*> -> (previousStatus as Status.Yielded<P>).continuation.resume(value)
        }
    }

    val isActive
        get() = status.get() != Status.Dead

}

val producer = Coroutinue.create<Unit, Int> {
    for (i in 0..3) {
        println("producer $i")
        yeildn(i)
    }
    200
}
val consumer = Coroutinue.create<Int, Unit> {
    for (i in 0..3) {
        val result = yeildn(Unit)
        println("consumer $i")
    }
}

 fun main() {
     runBlocking {
         while (producer.isActive && consumer.isActive) {
             val result = producer.resume(Unit)
             consumer.resume(result)
         }
     }
}


