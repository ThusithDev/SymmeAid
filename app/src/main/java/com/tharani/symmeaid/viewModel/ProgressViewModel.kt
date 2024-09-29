package com.tharani.symmeaid.viewModel

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.GenericTypeIndicator
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class ProgressViewModel : ViewModel() {
    private val databaseReference = FirebaseDatabase.getInstance("https://symmeaid-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("users")
    private val userId: String?
        get() = FirebaseAuth.getInstance().currentUser?.uid

    // Save the weekly progress to Firebase
    fun saveProgress(checkedStates: List<Boolean>, onComplete: (Boolean) -> Unit) {
        userId?.let { uid ->
            val currentWeekStartDate = getCurrentWeekStartDate()
            val progressRef = databaseReference.child(uid).child("weeklyProgress").child(currentWeekStartDate)
            progressRef.setValue(checkedStates).addOnCompleteListener { task ->
                onComplete(task.isSuccessful)
            }
        } ?: onComplete(false)
    }

    // Load the weekly progress from Firebase
    fun loadProgress(onResult: (List<Boolean>?) -> Unit) {
        userId?.let { uid ->
            val currentWeekStartDate = getCurrentWeekStartDate()
            val progressRef = databaseReference.child(uid).child("weeklyProgress").child(currentWeekStartDate)
            progressRef.get().addOnSuccessListener { snapshot ->
                val progressList = snapshot.getValue(object : GenericTypeIndicator<List<Boolean>>() {})
                onResult(progressList)
            }.addOnFailureListener {
                onResult(null)
            }
        } ?: onResult(null)
    }

    // Helper function to get the current week's start date as a unique key
    private fun getCurrentWeekStartDate(): String {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.DAY_OF_WEEK, calendar.firstDayOfWeek) // Set to the start of the week
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return dateFormat.format(calendar.time)
    }
}
