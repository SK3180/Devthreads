package com.sksingh.devthreads.viewmodel




import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError

import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue

import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.sksingh.devthreads.models.ThreadModel
import com.sksingh.devthreads.models.UserModel



class SearchViewModel : ViewModel() {


     private val db = FirebaseDatabase.getInstance()
    private val users = db.getReference("users")



    private var _Users = MutableLiveData<List<UserModel>>()
    val UsersList: LiveData<List<UserModel>> = _Users

    init {
        fetchUsers{
            _Users.value =  it
        }
    }

    private fun fetchUsers(onResult: (List<UserModel>)->Unit){
        users.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                val result = mutableListOf<UserModel>()
                for (threadSnapshot in snapshot.children){
                    val thread  = threadSnapshot.getValue(UserModel::class.java)
                    result.add(thread!!)

                }
                onResult(result)
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    //val db = FirebaseDatabase.getInstance()
    fun fetchUserFromThreads(thread: ThreadModel,onResult:(UserModel)->Unit){
        db.getReference("users").child(thread.userId)
            .addListenerForSingleValueEvent(object :ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {

                    val user = snapshot.getValue(UserModel::class.java)
                    user?.let(onResult)
                }

                override fun onCancelled(error: DatabaseError) {

                }

            })

    }
}
