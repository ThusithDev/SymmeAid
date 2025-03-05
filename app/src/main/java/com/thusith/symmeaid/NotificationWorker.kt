package com.thusith.symmeaid

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class NotificationWorker(ctx: Context, params: WorkerParameters) : Worker(ctx, params) {
    override fun doWork(): Result {
        val title = inputData.getString("title") ?: "Scheduled Notification"
        val message = inputData.getString("message") ?: "Your notification message"
        showNotification(applicationContext, title, message)
        return Result.success()
    }
}
