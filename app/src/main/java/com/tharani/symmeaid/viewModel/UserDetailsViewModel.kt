package com.tharani.symmeaid.viewModel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.FirebaseApp
import com.google.firebase.appcheck.FirebaseAppCheck
import com.google.firebase.appcheck.playintegrity.PlayIntegrityAppCheckProviderFactory
import com.google.firebase.storage.FirebaseStorage

class UserDetailsViewModel : ViewModel() {

    // Initialize Firebase Database reference with the correct URL
    private val databaseReference = FirebaseDatabase.getInstance("https://symmeaid-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("users")
    private val storageReference = FirebaseStorage.getInstance().reference
    // Get the current user's ID
    private val userId: String?
        get() = FirebaseAuth.getInstance().currentUser?.uid

    private val _profileData = mutableStateOf<ProfileData?>(null)
    val profileData: State<ProfileData?> = _profileData

    // Function to save user details to Firebase Realtime Database
    fun saveUserDetails(name: String, age: String, gender: String, onResult: (Boolean) -> Unit) {
        // Ensure userId is not null
        userId?.let { id ->
            // Create a user map with the name and age
            val user = mapOf(
                "name" to name,
                "age" to age,
                "gender" to gender
            )

            // Save the user details to the database
            databaseReference.child(id).setValue(user)
                .addOnSuccessListener {
                    Log.d("UserDetailsViewModel", "User details saved successfully")
                    onResult(true)
                }
                .addOnFailureListener { e ->
                    Log.e("UserDetailsViewModel", "Error saving user details", e)
                    onResult(false)
                }
        } ?: run {
            // If userId is null, return false and log the error
            Log.e("UserDetailsViewModel", "User ID is null")
            onResult(false)
        }
    }

    fun fetchUserProfile() {
        val id = userId
        if (id != null) {
            databaseReference.child(id).get()
                .addOnSuccessListener { dataSnapshot ->
                    val name = dataSnapshot.child("name").getValue(String::class.java) ?: ""
                    val age = dataSnapshot.child("age").getValue(String::class.java) ?: ""
                    val gender = dataSnapshot.child("gender").getValue(String::class.java) ?: ""

                    val imageRef = storageReference.child("profileImages/$id.jpg")
                    imageRef.downloadUrl.addOnSuccessListener { uri ->
                        _profileData.value = ProfileData(name = name, age = age, gender = gender, profileImageUrl = uri.toString())
                        Log.d("UserDetailsViewModel", "Profile data fetched successfully: $name, $age, $uri")
                    }.addOnFailureListener { e ->
                        _profileData.value = ProfileData(name = name, age = age, gender = gender, profileImageUrl = null)
                        Log.e("UserDetailsViewModel", "Error fetching profile image", e)
                    }
                }.addOnFailureListener { e ->
                    Log.e("UserDetailsViewModel", "Error fetching user data", e)
                }
        } else {
            Log.e("UserDetailsViewModel", "User ID is null")
        }
    }

    fun clearProfileData() {
        _profileData.value = null
    }

    data class ProfileData(
        val name: String,
        val age: String,
        val gender: String,
        val profileImageUrl: String?
    )
}

