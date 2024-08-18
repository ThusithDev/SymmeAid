package com.tharani.symmeaid.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore

class HomeViewModel : ViewModel() {
    private val _userProfile = MutableLiveData<UserProfile?>()
    val userProfile: LiveData<UserProfile?> = _userProfile

    private val _isLoading = MutableLiveData(true)
    val isLoading: LiveData<Boolean> = _isLoading

    private val db = FirebaseFirestore.getInstance()

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
}

data class UserProfile(
    val name: String = "",
    val age: Int = 0,
    val profileImageUrl: String = ""
)
