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
import com.srbh.wander.model.User
import com.srbh.wander.viewmodel.UserViewModel

class SignUpActivity : AppCompatActivity() {

    private lateinit var mName: TextInputEditText
    private lateinit var mEmail: TextInputEditText
    private lateinit var mPassword: TextInputEditText
    private lateinit var mConfirmPassword: TextInputEditText
    private lateinit var mSignup: Button
    private lateinit var mLoginText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        mEmail = findViewById(R.id.email)
        mName = findViewById(R.id.name)
        mPassword = findViewById(R.id.password)
        mConfirmPassword = findViewById(R.id.confirm_password)
        mSignup = findViewById(R.id.signup_button)
        mLoginText = findViewById(R.id.login_text)

        mLoginText.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        mSignup.setOnClickListener{
            val email = mEmail.text.toString()
            val name = mName.text.toString()
            val password = mPassword.text.toString()
            val confirmPassword = mConfirmPassword.text.toString()
            val viewModel = UserViewModel(application)
            mEmail.error=null
            if(email=="" || email==null || !Patterns.EMAIL_ADDRESS.matcher(email).matches())
                mEmail.error = "Use a valid email ID"
            else if(viewModel.getUserWithEmail(email)!=null) {
                mEmail.error = "Email ID already in use"
                Toast.makeText(this, "Email already exist", Toast.LENGTH_SHORT).show()
            }
            else if(!password.equals(confirmPassword))
                Toast.makeText(this, "Passwords don't match", Toast.LENGTH_SHORT).show()
            else {
                val user = User(email = email, name = name, password = password)
                viewModel.insert(user)
                //Goto login page
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
        }

    }
}