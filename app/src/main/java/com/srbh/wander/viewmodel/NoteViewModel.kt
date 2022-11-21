package com.srbh.wander.viewmodel


import android.app.Application
import android.content.Intent
import android.util.Log
import androidx.core.content.ContextCompat.startActivity
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.srbh.wander.model.Note
import com.srbh.wander.repo.NoteRepo
import com.srbh.wander.view.AddActivity
import com.srbh.wander.view.MainActivity
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.internal.artificialFrame
import kotlinx.coroutines.launch

class NoteViewModel(application: Application, private val noteRepo: NoteRepo): ViewModel(), Observable {

    @Bindable
    val topic = MutableLiveData<String>()

    @Bindable
    val description = MutableLiveData<String>()

    lateinit var allNotes: LiveData<List<Note>>
    private var currentUserEmail: String

    init {
        currentUserEmail = UserViewModel(application).getCurrentUser().toString()
        getNotes(application)
    }

    fun add(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        noteRepo.add(note)
    }

    fun update(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        noteRepo.update(note)
    }

    fun deleteAll() = viewModelScope.launch(Dispatchers.IO) {
        noteRepo.deleteAllNote(currentUserEmail)
    }

    fun delete(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        noteRepo.delete(note)
    }

    fun addNote() = add(Note(0, currentUserEmail, topic.value!!, description.value!!))

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }

    fun getNotes(application: Application){
        currentUserEmail = UserViewModel(application).getCurrentUser().toString()
        viewModelScope.launch(Dispatchers.IO) {
            allNotes=noteRepo.getAllNote(currentUserEmail)
            Log.i("VM", "getNotes: $currentUserEmail")
        }
    }

}