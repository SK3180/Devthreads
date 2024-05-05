package com.sksingh.devthreads.viewmodel

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.sksingh.devthreads.models.UserModel
import com.sksingh.devthreads.utils.SharedPref
import java.util.UUID

class AuthViewModel : ViewModel() {

    val auth = FirebaseAuth.getInstance()
    private val db = FirebaseDatabase.getInstance()
    val userRef = db.getReference("users")

    private val storageRef = Firebase.storage.reference
    private val imageRef = storageRef.child("users/${UUID.randomUUID()}.jpg")

    private val _firebaseUser = MutableLiveData<FirebaseUser?>()
    val firebaseUser: MutableLiveData<FirebaseUser?> = _firebaseUser

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    init {
        _firebaseUser.value = auth.currentUser
    }

    fun login(email: String, password: String, context: Context) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {

                    _firebaseUser.postValue(auth.currentUser)
                    getData(auth.currentUser!!.uid,context)
                } else {
                    _error.postValue(it.exception!!.message)
                    Log.d("Error", it.exception!!.message.toString())

                }
            }
    }

    private fun getData(uid: String,context: Context) {

        userRef.child(uid).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val userData = snapshot.getValue(UserModel::class.java)
                if (userData != null) {
                    SharedPref.storedata(
                        userData.name,
                        userData.email,
                        userData.username,
                        userData.imageUrl,
                        context,
                        userData.bio
                    )
                } else {
                    Toast.makeText(context, "Error Has Been found", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle onCancelled event
            }
        })


    }

    fun register(
        email: String,
        password: String,
        name: String,
        username: String,
        bio: String,
        imageUri: Uri,
        context:Context
    ) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    _firebaseUser.postValue(auth.currentUser)
                    saveImage(email, password, name, bio, username, imageUri, auth.currentUser?.uid,context)


                } else {
                    _error.postValue(it.exception!!.message)
                }
            }
    }

    private fun saveImage(
        email: String,
        password: String,
        name: String,
        bio: String,
        username: String,
        imageUri: Uri,
        uid: String?,
        context:Context
    ) {

        val uploadTask = imageRef.putFile(imageUri)
        uploadTask.addOnSuccessListener {
            imageRef.downloadUrl.addOnSuccessListener {
                saveData(email, password, name, bio, username, it.toString(), uid, context)
            }
        }

    }

    private fun saveData(
        email: String,
        password: String,
        name: String,
        bio: String,
        username: String,
        imageUrl: String,
        uid: String?,
        context:Context
    ) {
        val firestoredb = Firebase.firestore
        val followerref = firestoredb.collection("followers").document(uid!!)
        val followingref = firestoredb.collection("following").document(uid!!)
        followingref.set(mapOf("followingIds" to listOf<String>()))
        followerref.set(mapOf("followersIds" to listOf<String>()))

        val userData = UserModel(email,password,name,bio,username,imageUrl,uid!!)

        userRef.child(uid!!).setValue(userData)
            .addOnSuccessListener {
                SharedPref.storedata(name,email,username,imageUrl, context,bio)
            }.addOnFailureListener {

            }
    }
     fun logout()
    {
        auth.signOut()
        _firebaseUser.postValue(null)
    }


}