package com.example.togather

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.firestore.auth.User
import com.google.firebase.ktx.Firebase
import com.google.protobuf.Value
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
        var usersList = ArrayList<UserModel>()
        addUserListener(database, usersList)
        Log.i("UsersList", usersList.toString())
        val userAdapter = ListAdapter(this, usersList)
        search_lv.adapter = userAdapter
    }

    private fun addUserListener(userReference : DatabaseReference, usersList : ArrayList<UserModel>){
        val userListener = object : ValueEventListener{
            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                val user = dataSnapshot.getValue<UserModel>()


                val temp = dataSnapshot.getValue() as HashMap<String, HashMap<String, HashMap<String, String>>>
                val gotUser = temp.get("user")

                for (key in gotUser!!.keys){
                    val getValues = gotUser!!.get(key)
                    Log.i("tempUser", getValues.toString())
                    val tuid = getValues!!.get("uid")
                    val tname = getValues!!.get("nickname")
                    val ttag : ArrayList<String> = getValues!!.get("hashtag") as ArrayList<String>
                    val tempUser = UserModel(tuid!!, tname!!, ttag)
                    // 객체는 제대로 생성되는 것을 확인함
                    usersList.add(tempUser!!)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("userAdd", "Error")
            }

        }
        userReference.addValueEventListener(userListener)
    }

}