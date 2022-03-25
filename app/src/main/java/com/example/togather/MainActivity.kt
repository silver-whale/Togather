package com.example.togather

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_Togather)
        // if not logined -> activity_login
        // else -> activity_main
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        profile_btn.setOnClickListener{
            startActivity(Intent(this, ProfileActivity::class.java))
        }
    }
}