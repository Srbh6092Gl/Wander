package com.srbh.wander.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.srbh.wander.R
import com.srbh.wander.viewmodel.UserViewModel
import java.util.regex.Pattern

class LoginActivity : AppCompatActivity() {

    private lateinit var mEmail: TextInputEditText
    private lateinit var mPassword: TextInputEditText
    private lateinit var mSignUpText: TextView
    private lateinit var mLogin: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mEmail = findViewById(R.id.email)
        mPassword = findViewById(R.id.password)
        mLogin = findViewById(R.id.login_button)
        mSignUpText = findViewById(R.id.sign_up_text)

        mSignUpText.setOnClickListener{

            //Implicit and Explicit
            //OS 12
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
            finish()
        }

        mLogin.setOnClickListener{
            val viewModel = UserViewModel(application)
            val email = mEmail.text.toString()
            if(email=="" || email == null || !Patterns.EMAIL_ADDRESS.matcher(email).matches())
                mEmail.error = "Use a valid email ID"
            else{
                mEmail.error = null
                val password = mPassword.text.toString()
                val user = viewModel.getUserWithEmail(email)
                if(user == null)
                    Toast.makeText(this, "No user exist with this email", Toast.LENGTH_SHORT).show()
                else if(user!!.password.equals(password)){
                    viewModel.login(email)
                    val intent = Intent(this, MainActivity::class.java)
                    intent.putExtra("Name", user.name)
                    startActivity(intent)
                    finish()
                }
                else
                    Toast.makeText(this, "Wrong password! Try again.", Toast.LENGTH_SHORT).show()
            }

        }

    }
}