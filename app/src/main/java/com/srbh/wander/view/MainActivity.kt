package com.srbh.wander.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.srbh.wander.R
import com.srbh.wander.viewmodel.UserViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var mName: TextView
    private lateinit var mLogout: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val name = intent.getStringExtra("Name")

        mName = findViewById(R.id.name)
        mLogout = findViewById(R.id.logout)

        mName.text ="Welcome, $name"
        mLogout.setOnClickListener{
            val viewModel = UserViewModel(application)
            viewModel.logout()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}