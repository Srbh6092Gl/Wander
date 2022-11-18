package com.srbh.wander.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import com.google.android.material.button.MaterialButton
import com.srbh.wander.R

class AddActivity : AppCompatActivity() {

    private lateinit var mBack: ImageButton
    private lateinit var mAdd: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        mBack = findViewById(R.id.back_button)
        mAdd = findViewById(R.id.add_note_button)

        mAdd.setOnClickListener{
            goBackToMainActivity()
        }
        mBack.setOnClickListener{
            goBackToMainActivity()
        }
    }

    private fun goBackToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}