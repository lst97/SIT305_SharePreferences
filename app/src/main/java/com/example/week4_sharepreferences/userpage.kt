package com.example.week4_sharepreferences

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import com.example.week4_sharepreferences.MainActivity.Companion.PASSWORD
import com.example.week4_sharepreferences.MainActivity.Companion.USERNAME

class userpage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_userpage)

        val username = intent.getStringExtra(USERNAME)
        val password = intent.getStringExtra(PASSWORD)

        val usernameText = findViewById<TextView>(R.id.sub_username)
        usernameText.text = username
    }
}