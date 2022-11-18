package com.srbh.wander.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.srbh.wander.R
import com.srbh.wander.viewmodel.UserViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var mWelcome: TextView
    private lateinit var mEmail: TextView
    private lateinit var mAdd: FloatingActionButton
    private lateinit var mLogout: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val name = intent.getStringExtra("Name")
        mWelcome = findViewById(R.id.welcome_text)
        mEmail = findViewById(R.id.email)
        mAdd = findViewById(R.id.add)
        mLogout = findViewById(R.id.logout_button)

        mWelcome.text ="Welcome, $name"
        val email = UserViewModel(application).getCurrentUser()
        mEmail.text = email

        mLogout.setOnClickListener{
            val viewModel = UserViewModel(application)
            viewModel.logout()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        mAdd.setOnClickListener{
            val intent = Intent(this, AddActivity::class.java)
            startActivity(intent)
        }

    }

}