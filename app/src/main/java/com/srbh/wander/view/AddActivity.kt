package com.srbh.wander.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.button.MaterialButton
import com.srbh.wander.R
import com.srbh.wander.database.NoteDB
import com.srbh.wander.databinding.ActivityAddBinding
import com.srbh.wander.databinding.ActivityMainBinding
import com.srbh.wander.model.Note
import com.srbh.wander.repo.NoteRepo
import com.srbh.wander.viewmodel.NoteViewModel
import com.srbh.wander.viewmodel.NoteViewModelFactory

class AddActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddBinding
    private lateinit var noteViewModel: NoteViewModel
    private lateinit var note: Note
    private var isUpdate = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_add)
        val noteDao = NoteDB.getDB(application).noteDao
        val noteRepo = NoteRepo(noteDao)
        val factory = NoteViewModelFactory(application,noteRepo)
        noteViewModel = ViewModelProvider(this,factory).get(NoteViewModel::class.java)
        binding.noteViewModel = noteViewModel
        binding.lifecycleOwner = this

        try{
            val noteId = intent.getLongExtra("noteId",-1L)
            if(noteId == -1L)
                throw Exception("Not an Update call")
            var noteTopic = intent.getStringExtra("noteTopic")
            val noteDescription = intent.getStringExtra("noteDescription")
            val noteSender = intent.getStringExtra("noteSender")
            setUpdateLayout()
            noteViewModel.topic.value = noteTopic
            noteViewModel.description.value = noteDescription
            note = Note(id = noteId, topic = noteTopic.toString(), description = noteDescription.toString(), sender = noteSender.toString())
            Log.i("UpdateNote", note.toString())
        } catch (exception : Exception){
            setAddLayout()
        }

        binding.addNoteButton.setOnClickListener{
            if(isUpdate) {
                note.topic = noteViewModel.topic.value!!
                note.description = noteViewModel.description.value!!
                noteViewModel.update(note)
            }
            else
                noteViewModel.addNote()
            Log.i("AddActivity", "Add Button Clicked: "+noteViewModel.allNotes)
            setAddLayout()
            goBackToMainActivity()
        }
        binding.backButton.setOnClickListener{
            goBackToMainActivity()
        }
    }

    private fun setUpdateLayout() {
        isUpdate = true
        binding.addNoteButton.text = "Update"
        binding.headerText.text = "Update note"
        binding.addNoteButton.icon = getDrawable(R.drawable.ic_baseline_update_24)
    }

    private fun setAddLayout() {
        isUpdate = false
        binding.addNoteButton.text = getString(R.string.add)
        binding.headerText.text = getString(R.string.add_a_note)
        binding.addNoteButton.icon = getDrawable(R.drawable.ic_baseline_add_24)
    }

    private fun goBackToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
        finish()
    }
}