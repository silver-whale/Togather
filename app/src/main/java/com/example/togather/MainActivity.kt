package com.example.togather

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_Togather)
        // if not logined -> activity_login
        // else -> activity_main
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        profile_btn.setOnClickListener{
            startActivity(Intent(this, ProfileActivity::class.java))
        }

        database = Firebase.database.reference

        database.child("user").get().addOnSuccessListener {
            // allUser는 전체 사용자를 uid, information으로 가지는 hashmap
            val allUser : HashMap<String, UserModel>  = it.value as HashMap<String, UserModel>
            Log.i("main", "${allUser}")
        }




    }
}