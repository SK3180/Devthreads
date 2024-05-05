package com.sksingh.devthreads.viewmodel




import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError

import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore

import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.sksingh.devthreads.models.ThreadModel
import com.sksingh.devthreads.models.UserModel



class UserViewModel : ViewModel() {


    private val db = FirebaseDatabase.getInstance()
    private val threadRef = db.getReference("threads")
    private val userRef = db.getReference("users")

    private val _threads = MutableLiveData(listOf<ThreadModel>())

    val threads: LiveData<List<ThreadModel>> get() = _threads


    private val _followerList = MutableLiveData(listOf<String>())

    val followerList: LiveData<List<String>> get() = _followerList

    private val _followingList = MutableLiveData(listOf<String>())

    val followingList: LiveData<List<String>> get() = _followingList


    private val _users = MutableLiveData(UserModel())
    val users: LiveData<UserModel> get() = _users


    fun fetchUser(uid: String) {
        userRef.child(uid).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val user = snapshot.getValue(UserModel::class.java)
                _users.postValue(user)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("Testing", "Database operation cancelled: $error")

            }

        })


    }

    fun fetchThreads(uid: String) {
        threadRef.orderByChild("userId").equalTo(uid)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val threadList = snapshot.children.mapNotNull {
                        it.getValue(ThreadModel::class.java)
                    }
                    _threads.postValue(threadList)
                }

                override fun onCancelled(error: DatabaseError) {

                }

            })


    }

    val firestoredb = Firebase.firestore
    fun followUsers(userId: String, currentUserId: String) {

        val ref = firestoredb.collection("following").document(currentUserId)
        val follwerRef = firestoredb.collection("followers").document(userId)


        ref.update("followingIds", FieldValue.arrayUnion(userId))
        follwerRef.update("followersIds", FieldValue.arrayUnion(currentUserId))


    }

    fun getFollower(userId: String) {

        firestoredb.collection("followers").document(userId)
            .addSnapshotListener { value, error ->

                val followerIds = value?.get("followersIds") as? List<String> ?: listOf()
                _followerList.postValue(followerIds)
            }
    }

    fun getFollowing(userId: String) {

        firestoredb.collection("following").document(userId)
            .addSnapshotListener { value, error ->

                val followingIds = value?.get("followingIds") as? List<String> ?: listOf()
                _followingList.postValue(followingIds)

            }


    }

}
