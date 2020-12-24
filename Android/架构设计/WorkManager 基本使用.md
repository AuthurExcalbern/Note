WorkManager很适合用于处理一些要求定时执行的任务，它可以根据操作系统的版本自动选择底层是使用AlarmManager实现还是JobScheduler实现，从而降低了我们的使用成本。

另外， WorkManager还支持周期性任务、链式任务处理等功能，是一个非常强大的工具。

WorkManager的基本用法其实非常简单，主要分为以下3步：

- 定义一个后台任务，并实现具体的任务逻辑。
- 配置该后台任务的运行条件和约束信息，并构建后台任务请求。
- 将该后台任务请求传入WorkManager的enqueue()方法中，系统会在合适的时间运行。

```kotlin
class SimpleWorker(context: Context, params: WorkerParameters) : Worker(context, params) {
    override fun doWork(): Result {
        Log.d("SimpleWorker", "do work in SimpleWorker")
        return Result.success()
    }
}

//Activity onCreate
        doWorkBtn.setOnClickListener {
            val request = OneTimeWorkRequest.Builder(SimpleWorker::class.java).build()
            WorkManager.getInstance(this).enqueue(request)
        }
```

