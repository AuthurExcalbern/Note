package util

import android.util.Log
import android.widget.Button
import kotlinx.coroutines.*

class LearnLambda {
    companion object{
        const val TAG = "LearnLambda"
    }

    /* twoAndThree 返回 Unit ，可以省略
     * twoAndThree(operation: (Int, Int) -> Int) : Unit
     *
     * 但函数类型声明需要有显示返回值，返回 Unit 不可省略
     * (Int, String) -> Unit
     */
    fun twoAndThree(operation: (Int, Int) -> Int){
        val result = operation(2, 3)
        Log.d(TAG, "result: $result")

        /* >>> twoAndThree { a, b -> a + b }
         * result:5
         * >>> twoAndThree { a, b -> a * b }
         * result:6
         */

        /* lambda函数参数 twoAndThree { a, b -> a + b } 的类型已经在函数变量声明中指定了
         * 不需要在lambda中重复声明
         */
    }

    /* runBlocking 方法会阻塞当前线程来等待 -> runBlocking 是常规函数
     * coroutineScope 只是挂起，会释放底层线程用于其他用途 -> coroutineScope 是挂起函数
     */
    private fun updateUI(btn: Button) = runBlocking {

        val job = GlobalScope.launch { // launch a new coroutine and keep a reference to its Job
            delay(1000L)
            println("World!")
        }
        println("Hello,")
        job.join() // wait until child coroutine completes

        //在 Dispatchers.Main 更新UI
        launch(Dispatchers.Main) {

        }

        // 创建一个协程作用域：在所有已启动子协程执行完毕之前不会结束
        coroutineScope {
            launch {
                delay(500L)
                println("Task from runBlocking")//内嵌 launch 输出
            }

            delay(100L)
        println("Task from coroutine scope") // 这一行会在 coroutineScope 内嵌 launch 之前输出
    }

    println("Coroutine scope is over") // 这一行在 runBlocking 内嵌 launch 执行完毕后才输出
    }

}