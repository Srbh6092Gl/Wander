package com.srbh.wander.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.srbh.wander.repo.NoteRepo

class NoteViewModelFactory(
    private val application: Application,
    private val noteRepo: NoteRepo
    ): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(NoteViewModel::class.java)){
            val key = "NoteViewModel"
            if(hashMapViewModel.containsKey(key))
                hashMapViewModel.remove(key)
            addViewModel(key, NoteViewModel(application,noteRepo))
            return getViewModel(key) as T
        }
        throw IllegalArgumentException("Couldn't recognize ViewModel class")
    }

    companion object{
        val hashMapViewModel = HashMap<String,ViewModel>()
        fun addViewModel(key: String, viewModel: ViewModel) = hashMapViewModel.put(key,viewModel)
        fun getViewModel(key: String): ViewModel? = hashMapViewModel.get(key)
    }

}