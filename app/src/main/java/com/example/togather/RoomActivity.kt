package com.example.togather

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_room.*
import kotlinx.android.synthetic.main.room.*
import java.io.*

class RoomActivity : AppCompatActivity() {
    //var fileDir = filesDir.toString() + "friendsList"
    // 파이어베이스 관련 변수
    var database: FirebaseDatabase = Firebase.database
    lateinit var myRef: DatabaseReference
    // 로그인 정보 관련 변수
    private lateinit var auth: FirebaseAuth
    lateinit var currentUser: FirebaseUser
    lateinit var sender: String
    // 채팅방 리사이클러뷰관련 변수
    private lateinit var adapter: RoomAdapter
    private var rooms: ArrayList<Room> = arrayListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room)

        adapter = RoomAdapter(rooms)

        //채팅방 어뎁터 연결
        rc_room.adapter = adapter
        rc_room.setHasFixedSize(true)
        rc_room.layoutManager = LinearLayoutManager(this)

        // 파이어베이스 연결, 로그인 정보 가져오기
        auth = Firebase.auth
        currentUser = auth.currentUser!!
        sender = currentUser.uid

        // 채팅방 목록 가져오기
        readRoomList()
    }

    fun readRoomList() {
        rooms.clear()
        myRef = database.getReference("message").child(sender)//.child(sender)
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // 채팅방 목록 가져오기
                for (d in dataSnapshot.children) {
                    adapter.addRoom(Room(d.key.toString()))
                    //Log.e("countsnap", d.children.count())
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
                Log.e("snap1", "채팅 목록 읽기 실패")
            }
        })
    }

}
