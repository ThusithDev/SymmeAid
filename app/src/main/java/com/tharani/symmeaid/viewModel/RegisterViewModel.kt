package com.tharani.symmeaid.viewModel

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Matrix
import android.media.Image
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class RegisterViewModel : ViewModel() {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val storage: FirebaseStorage = FirebaseStorage.getInstance()
    private val storageReference: StorageReference = storage.reference

    fun registerUser(email: String, password: String, imageUri: Uri?, onComplete: (Boolean, String?) -> Unit) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user: FirebaseUser? = auth.currentUser
                    if (user != null) {
                        if (imageUri != null) {
                            uploadProfileImage(user.uid, imageUri, onComplete)
                        } else {
                            onComplete(true, null)
                        }
                    }
                } else {
                    onComplete(false, task.exception?.message)
                }
            }
    }

    private fun uploadProfileImage(userId: String, imageUri: Uri, onComplete: (Boolean, String?) -> Unit) {
        val imageRef = storageReference.child("profileImages/$userId.jpg")
        imageRef.putFile(imageUri)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onComplete(true, null)
                } else {
                    onComplete(false, task.exception?.message)
                }
            }
    }

    fun loginUser(email: String, password: String, onComplete: (Boolean, String?) -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onComplete(true, null)
                    val userDetailsViewModel = UserDetailsViewModel()
                    userDetailsViewModel.fetchUserProfile()
                } else {
                    onComplete(false, task.exception?.message)
                }
            }
    }

    fun logout(userDetailsViewModel: UserDetailsViewModel,onComplete: () -> Unit) {
        auth.signOut()
        userDetailsViewModel.clearProfileData()
        onComplete()
    }

    fun sendPasswordResetEmail(email: String, onComplete: (Boolean, String?) -> Unit) {
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onComplete(true, "Password reset email sent.")
                } else {
                    onComplete(false, task.exception?.message)
                }
            }
    }

}
