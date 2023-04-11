package chapter4

import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.coroutines.startCoroutine

interface AsyncScope

interface Call<T>

fun async(
    context: CoroutineContext = EmptyCoroutineContext,
    block: suspend AsyncScope.() -> Unit
) {
    val completion = AsyncCoroutinue(context)
    block.startCoroutine(completion, completion)
}


class AsyncCoroutinue(override val context: CoroutineContext = EmptyCoroutineContext) : Continuation<Unit>, AsyncScope {
    override fun resumeWith(result: Result<Unit>) {
        result.getOrThrow()
    }
}

suspend fun <T> AsyncScope.awaitn(block: () -> Call<T>) {

}



