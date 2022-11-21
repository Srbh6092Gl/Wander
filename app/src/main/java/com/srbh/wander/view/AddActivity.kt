package com.srbh.wander.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.button.MaterialButton
import com.srbh.wander.R
import com.srbh.wander.database.NoteDB
import com.srbh.wander.databinding.ActivityAddBinding
import com.srbh.wander.databinding.ActivityMainBinding
import com.srbh.wander.repo.NoteRepo
import com.srbh.wander.viewmodel.NoteViewModel
import com.srbh.wander.viewmodel.NoteViewModelFactory

class AddActivity : AppCompatActivity() {

    private lateinit var mBack: ImageButton
    private lateinit var mAdd: MaterialButton

    private lateinit var binding: ActivityAddBinding
    private lateinit var noteViewModel: NoteViewModel

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

        binding.addNoteButton.setOnClickListener{
            noteViewModel.addNote()
            Log.i("AddActivity", "Add Button Clicked: "+noteViewModel.allNotes)
            goBackToMainActivity()
        }
        binding.backButton.setOnClickListener{
            goBackToMainActivity()
        }
    }

    private fun goBackToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
        finish()
    }
}