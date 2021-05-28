package ski.gagar.demo

import io.vertx.core.Vertx
import io.vertx.kotlin.coroutines.dispatcher
import kotlinx.coroutines.*

fun main() {
    val vertx = Vertx.vertx()

//    coro1(vertx)
//    coro2(vertx)
//    coro3(vertx)
}


fun coro1(vertx: Vertx) {
    // This is a delicate API and its use requires care.
    // Make sure you fully read and understand documentation of the declaration that
    // is marked as a delicate API.
    // +
    // Type mismatch.
    // Required: CoroutineContext
    // Found: CoroutineDispatcher
    GlobalScope.launch(vertx.dispatcher()) {
        println("Hello World!")
    }
}

fun coro2(vertx: Vertx) {
    // Type mismatch.
    // Required: CoroutineContext
    // Found: CoroutineDispatcher
    //
    // BTW, is this correct way to replace coro1?
    runBlocking {
        launch(vertx.dispatcher()) {
            println("Hello World!")
        }
    }
}

fun coro3(vertx: Vertx) {
    // OK even though Dispatchers.Unconfined has the same type as vertx.dispatcher() (kotlinx.coroutines.CoroutineDispatcher)
    runBlocking {
        launch(Dispatchers.Unconfined) {
            println("Hello World")
        }
    }
}

// Bonus!
fun Vertx.dispatcherWithEx() =
    // Type mismatch.
    // Required: CoroutineDispatcher
    // Found: CoroutineExceptionHandler
    // +
    // Using 'plus(CoroutineDispatcher): CoroutineDispatcher' is an error.
    // Operator '+' on two CoroutineDispatcher objects is meaningless.
    // CoroutineDispatcher is a coroutine context element and `+` is a set-sum operator for coroutine contexts.
    // The dispatcher to the right of `+` just replaces the dispatcher to the left.
    dispatcher() + CoroutineExceptionHandler { _, ex ->
        println("Oopsie")
    }
