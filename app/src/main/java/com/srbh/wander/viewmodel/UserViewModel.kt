package com.srbh.wander.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.srbh.wander.model.User
import com.srbh.wander.repo.UserRepo

class UserViewModel(application: Application): AndroidViewModel(application) {

    private val repo : UserRepo

    init{
        repo = UserRepo(application)
    }

    fun insert (user: User){
        repo.insert(user)
    }
    fun getUserWithEmail(email: String) = repo.getUserWithEmail(email)

    fun login (email:String){
        repo.login(email)
    }
    fun logout (){
        repo.logout()
    }

    fun getCurrentUser() = repo.getCurrentUser()

}