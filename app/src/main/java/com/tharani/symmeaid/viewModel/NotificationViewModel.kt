package com.tharani.symmeaid.viewModel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.tharani.symmeaid.NotificationWorker
import java.util.concurrent.TimeUnit

class NotificationViewModel : ViewModel() {

    fun scheduleNotification(context: Context, timeInMillis: Long, title: String, message: String) {
        val currentTime = System.currentTimeMillis()
        val delayInMillis = timeInMillis - currentTime

        if (delayInMillis > 0) {
            scheduleNotificationWithWorkManager(context, delayInMillis, title, message)
        } else {
            // Handle invalid time selection (e.g., in the past)
            Log.e("NotificationViewModel", "Selected time is in the past.")
        }
    }

    private fun scheduleNotificationWithWorkManager(context: Context, delayInMillis: Long, title: String, message: String) {
        val workManager = WorkManager.getInstance(context)

        val data = Data.Builder()
            .putString("title", title)
            .putString("message", message)
            .build()

        val notificationWork = OneTimeWorkRequestBuilder<NotificationWorker>()
            .setInitialDelay(delayInMillis, TimeUnit.MILLISECONDS)
            .setInputData(data)
            .build()

        workManager.enqueue(notificationWork)
    }
}
