package com.tharani.symmeaid.viewModel

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.ByteArrayOutputStream

class FaceCaptureViewModel : ViewModel() {
    private val storageReference: StorageReference = FirebaseStorage.getInstance().reference
    private val databaseReference = FirebaseDatabase.getInstance("https://symmeaid-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("users")

    // Get the current user's ID
    private val userId: String?
        get() = FirebaseAuth.getInstance().currentUser?.uid

    // Function to upload a captured face image to Firebase Storage
    fun uploadCapturedFace(bitmap: Bitmap, onComplete: (Boolean, String?) -> Unit) {
        // Ensure the user is authenticated
        userId?.let { id ->
            // Generate a unique filename for each captured face image
            val uniqueFilename = "${System.currentTimeMillis()}.jpg"
            val faceImageRef = storageReference.child("capturedImages/$id/$uniqueFilename")

            // Convert Bitmap to ByteArray
            val data = bitmapToByteArray(bitmap)

            // Upload the image
            val uploadTask = faceImageRef.putBytes(data)
            uploadTask.addOnSuccessListener {
                // Retrieve the download URL after successful upload
                faceImageRef.downloadUrl.addOnSuccessListener { uri ->
                    // Store the new image URL in the database under a unique key
                    saveFaceImageUrlToDatabase(id, uri.toString())
                    onComplete(true, null)
                }.addOnFailureListener { e ->
                    Log.e("FaceCaptureViewModel", "Error retrieving download URL", e)
                    onComplete(false, e.message)
                }
            }.addOnFailureListener { e ->
                Log.e("FaceCaptureViewModel", "Error uploading captured face image", e)
                onComplete(false, e.message)
            }
        } ?: run {
            Log.e("FaceCaptureViewModel", "User ID is null. Cannot upload captured face image.")
            onComplete(false, "User is not authenticated")
        }
    }

    // Helper function to convert Bitmap to ByteArray
    private fun bitmapToByteArray(bitmap: Bitmap): ByteArray {
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        return baos.toByteArray()
    }

    // Function to save the image URL to Firebase Realtime Database
    private fun saveFaceImageUrlToDatabase(userId: String, imageUrl: String) {
        val newImageKey = databaseReference.child(userId).child("capturedImages").push().key
        if (newImageKey != null) {
            val updates = mapOf(newImageKey to imageUrl)
            databaseReference.child(userId).child("capturedImages").updateChildren(updates)
                .addOnSuccessListener {
                    Log.d("FaceCaptureViewModel", "Face image URL saved to database successfully")
                }
                .addOnFailureListener { e ->
                    Log.e("FaceCaptureViewModel", "Error saving face image URL to database", e)
                }
        } else {
            Log.e("FaceCaptureViewModel", "Error generating unique key for the image URL")
        }
    }

    // Function to retrieve the oldest image URL
    fun getOldestImageUrl(onComplete: (String?) -> Unit) {
        userId?.let { id ->
            databaseReference.child(id).child("capturedImages")
                .orderByKey()
                .limitToFirst(1)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val url = snapshot.children.firstOrNull()?.getValue(String::class.java)
                        onComplete(url)
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Log.e("FaceCaptureViewModel", "Error retrieving oldest image URL", error.toException())
                        onComplete(null)
                    }
                })
        }
    }

    // Function to retrieve the most recent image URL
    fun getMostRecentImageUrl(onComplete: (String?) -> Unit) {
        userId?.let { id ->
            databaseReference.child(id).child("capturedImages")
                .orderByKey()
                .limitToLast(1)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val url = snapshot.children.firstOrNull()?.getValue(String::class.java)
                        onComplete(url)
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Log.e("FaceCaptureViewModel", "Error retrieving most recent image URL", error.toException())
                        onComplete(null)
                    }
                })
        }
    }
}

