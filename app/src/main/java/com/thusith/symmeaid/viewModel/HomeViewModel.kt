package com.thusith.symmeaid.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.thusith.symmeaid.Exercise

class HomeViewModel : ViewModel() {

    // Holds the current exercise
    private val _selectedExercise = MutableLiveData<Exercise>()
    val selectedExercise: LiveData<Exercise> = _selectedExercise

    private val _userProfile = MutableLiveData<UserProfile?>()
    val userProfile: LiveData<UserProfile?> = _userProfile

    private val _isLoading = MutableLiveData(true)
    val isLoading: LiveData<Boolean> = _isLoading

    private val db = FirebaseFirestore.getInstance()

    // Define the list of exercises
    val exercises = listOf(
        Exercise("pikaso_image", "ExerciseOne"),
        Exercise("exercise_two", "ExerciseTwo"),
        Exercise("exercise_three", "ExerciseThree"),
        Exercise("exercise_four", "ExerciseFour"),
        Exercise("exercise_one", "ExerciseFive")
    )

    fun loadUserProfile(userId: String) {
        _isLoading.value = true
        db.collection("users").document(userId)
            .get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    _userProfile.value = document.toObject(UserProfile::class.java)
                }
                _isLoading.value = false
            }
            .addOnFailureListener {
                _isLoading.value = false
            }
    }

    // Function to set a new random exercise (called when "Done" is pressed)
    fun setRandomExercise() {
        _selectedExercise.value = exercises.random()
    }
}

data class UserProfile(
    val name: String = "",
    val age: Int = 0,
    val profileImageUrl: String = ""
)
