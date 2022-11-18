package com.srbh.wander.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.srbh.wander.R
import com.srbh.wander.viewmodel.UserViewModel

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Handler().postDelayed({
            val viewModel = UserViewModel(application)
            val email = viewModel.getCurrentUser()
            if(email == "") {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                val user = viewModel.getUserWithEmail(email!!)
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("Name",user!!.name)
                startActivity(intent)
                finish()
            }
        },1000)
    }
}