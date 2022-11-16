package com.srbh.wander.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.srbh.wander.R
import com.srbh.wander.viewmodel.UserViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var mName: TextView
    private lateinit var mEmail: TextView
    private lateinit var mLogout: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val name = intent.getStringExtra("Name")

        mName = findViewById(R.id.name)
        mEmail = findViewById(R.id.email)
        mLogout = findViewById(R.id.logout)

        mName.text ="Welcome, $name"
        val email = UserViewModel(application).getCurrentUser()
        mEmail.text = email
        mLogout.setOnClickListener{
            val viewModel = UserViewModel(application)
            viewModel.logout()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}