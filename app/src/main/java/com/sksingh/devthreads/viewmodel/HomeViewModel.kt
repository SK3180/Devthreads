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



class HomeViewModel : ViewModel() {


     private val db = FirebaseDatabase.getInstance()
     private val thread = db.getReference("threads")



    private var _threadsAndUsers = MutableLiveData<List<Pair<ThreadModel,UserModel>>>()
    val threadsAndUsers: LiveData<List<Pair<ThreadModel,UserModel>>> = _threadsAndUsers

    init {
        fetchThreadsAndUsers{
            _threadsAndUsers.value =  it
        }
    }

    private fun fetchThreadsAndUsers(onResult: (List<Pair<ThreadModel,UserModel>>)->Unit){
        thread.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                val result = mutableListOf<Pair<ThreadModel,UserModel>>()
                for (threadSnapshot in snapshot.children){
                    val thread  = threadSnapshot.getValue(ThreadModel::class.java)
                    thread.let {
                        fetchUserFromThreads(it!!){
                            user ->
                            result.add(0,it to user)

                            if (result.size == snapshot.childrenCount.toInt()){
                                onResult(result)
                            }
                        }
                    }

                }
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
