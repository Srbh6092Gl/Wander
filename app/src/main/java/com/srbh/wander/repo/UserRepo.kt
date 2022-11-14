package com.srbh.wander.repo

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.content.res.Resources
import android.util.Log
import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.srbh.wander.R
import com.srbh.wander.model.User

class UserRepo(application: Application) {

    var userSharedPref = application?.getSharedPreferences(
        "user_details",
        Context.MODE_PRIVATE
    )

    fun insert(user: User) {
        val editor: SharedPreferences.Editor = userSharedPref!!.edit()
        val json = Gson().toJson(user)
        editor.putString(user.email,json)
        editor.commit()
    }

    fun getUserWithEmail(email: String): User? {
        val json = userSharedPref!!.getString(email,"")
        if(json == "") {
            Log.e("Repo", "getUserWithEmail: Not Found")
            return null
        }
        val user = GsonBuilder().create().fromJson(json,User::class.java)
        return user
    }

    fun login(email: String) {
        val editor: SharedPreferences.Editor = userSharedPref!!.edit()
        editor.putString("currentUser",email)
        editor.commit()
    }

    fun logout() {
        val editor: SharedPreferences.Editor = userSharedPref!!.edit()
        editor.remove("currentUser")
        editor.commit()
    }

    fun getCurrentUser() = userSharedPref!!.getString("currentUser","")

}