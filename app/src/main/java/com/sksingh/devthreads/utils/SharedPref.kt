package com.sksingh.devthreads.utils

import android.content.Context
import android.content.Context.MODE_PRIVATE

object SharedPref {

    fun storedata(name:String, email:String, username:String, imageUrl: String, context: Context, bio:String){

        val sharedPreferences = context.getSharedPreferences("users",MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("name",name)
        editor.putString("email",email)
        editor.putString("username",username)
        editor.putString("imageUrl", imageUrl)
        editor.putString("bio",bio)
        editor.apply()

    }

    fun getUserName(context: Context):String{
        val sharedPreferences = context.getSharedPreferences("users", MODE_PRIVATE)
        return sharedPreferences.getString("username","")!!
    }
    fun getEmail(context: Context):String{
        val sharedPreferences = context.getSharedPreferences("users", MODE_PRIVATE)
        return sharedPreferences.getString("email","")!!
    }
    fun getName(context: Context):String{
        val sharedPreferences = context.getSharedPreferences("users", MODE_PRIVATE)
        return sharedPreferences.getString("name","")!!
    }
    fun getbio(context: Context):String{
        val sharedPreferences = context.getSharedPreferences("users", MODE_PRIVATE)
        return sharedPreferences.getString("bio","")!!
    }
    fun getImageUrl(context: Context):String{
        val sharedPreferences = context.getSharedPreferences("users", MODE_PRIVATE)
        return sharedPreferences.getString("imageUrl","")!!
    }

}