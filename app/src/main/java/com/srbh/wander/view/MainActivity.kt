package com.srbh.wander.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.srbh.wander.R
import com.srbh.wander.adapter.NoteAdapter
import com.srbh.wander.database.NoteDB
import com.srbh.wander.databinding.ActivityMainBinding
import com.srbh.wander.repo.NoteRepo
import com.srbh.wander.viewmodel.NoteViewModel
import com.srbh.wander.viewmodel.NoteViewModelFactory
import com.srbh.wander.viewmodel.UserViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var noteViewModel: NoteViewModel
    private lateinit var noteAdapter: NoteAdapter
    private lateinit var factory: NoteViewModelFactory
    private lateinit var name: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)

        val noteDao = NoteDB.getDB(application).noteDao
        val noteRepo = NoteRepo(noteDao)
        factory = NoteViewModelFactory(application,noteRepo)

        noteViewModel = ViewModelProvider(this,factory).get(NoteViewModel::class.java)
        binding.noteVM = noteViewModel
        binding.lifecycleOwner = this
        binding.notesRv.layoutManager = LinearLayoutManager(this)


        noteAdapter = NoteAdapter()
        binding.notesRv.adapter = noteAdapter

    }

    private fun displayNotes(){
        noteViewModel.getNotes(application)
        noteViewModel.allNotes.observe(this, Observer {
            noteAdapter.setList(it)
            noteAdapter.notifyDataSetChanged()
            Log.i("MainActivity: Notes", it.toString())
        })
    }

    override fun onResume() {
        super.onResume()

        val email = UserViewModel(application).getCurrentUser()
        name = UserViewModel(application).getUserWithEmail(email!!)!!.name!!
        binding.welcomeText.text ="Welcome, $name"
        binding.email.text = email

        displayNotes()

        binding.logoutButton.setOnClickListener{
            val viewModel = UserViewModel(application)
            viewModel.logout()
            val intent = Intent(this, LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
            finish()
        }

        binding.add.setOnClickListener{
            val intent = Intent(this, AddActivity::class.java)
            startActivity(intent)
        }

    }

}