package com.sksingh.devthreads.viewmodel


import android.icu.text.SimpleDateFormat
import android.net.Uri

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import com.google.firebase.database.FirebaseDatabase

import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.sksingh.devthreads.models.ThreadModel
import java.util.Date
import java.util.Locale

import java.util.UUID

class AddThreadViewModel : ViewModel() {


    private val db = FirebaseDatabase.getInstance()
    private val userRef = db.getReference("threads")
    private val storageRef = Firebase.storage.reference
    private val imageRef = storageRef.child("threads/${UUID.randomUUID()}.jpg")
    private val _isPosted = MutableLiveData<Boolean>()
    val isPosted: LiveData<Boolean> = _isPosted




     fun saveImage(
        thread: String,
        userId: String,
        imageUri: Uri,
    ) {

        val uploadTask = imageRef.putFile(imageUri)
        uploadTask.addOnSuccessListener {
            imageRef.downloadUrl.addOnSuccessListener {
                saveData(thread,userId,it.toString())
            }
        }

    }

    fun saveData(
        thread: String,
        userId: String,
        imageUri: String,
    ) {
        val currentTime = getCurrentTime()
        val threadData = ThreadModel(userId,imageUri,thread,currentTime)
        userRef.child(userRef.push().key!!).setValue(threadData)
            .addOnSuccessListener {

                _isPosted.postValue(true)
            }.addOnFailureListener {
                _isPosted.postValue(false)
            }
    }





}


fun getCurrentTime(): String {
    val currentTimeMillis = System.currentTimeMillis()
    val dateFormat = SimpleDateFormat("hh:mm:ss a", Locale.getDefault())
    val currentTime = Date(currentTimeMillis)
    return dateFormat.format(currentTime)
}