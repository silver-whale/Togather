package com.example.togather

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.auth.User
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_profile.*
import java.io.File
import java.util.*
import kotlin.collections.ArrayList

class ProfileActivity : AppCompatActivity() {

    lateinit var database: FirebaseDatabase
    lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        // Initialize Authentication, Database, Storage
        database = FirebaseDatabase.getInstance()
        databaseReference = database.getReference()

        var hashtags = ArrayList<String>()

        hashtag_add_btn.setOnClickListener(View.OnClickListener(){
            hashtags.add(hashtag_et.getText().toString())
            hashtag_et.setText(null)

            hashtag_show.setText(hashtags.joinToString(separator = " "))

        })


        profile_okay_btn.setOnClickListener(View.OnClickListener() {
            // 이미지 url 전송 필요
            addProfile("", name_et.getText().toString(), hashtags)
            startActivity(Intent(this, MainActivity::class.java))
        })

    }

    private fun addProfile(profileImageUrl:String, nickname: String, hashtags: ArrayList<String>) {
        val user = Firebase.auth.currentUser
        if(user != null){
                val uid = user.uid
                var umodel: UserModel = UserModel(profileImageUrl, nickname, hashtags)
                databaseReference.child("user").child(uid).setValue(umodel)
        }
    }
}