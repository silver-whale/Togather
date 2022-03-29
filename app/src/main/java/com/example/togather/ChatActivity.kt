package com.example.togather

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_chat.*
import java.io.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap

class ChatActivity : AppCompatActivity() {
    //recyclerView 관련 변수
    private lateinit var adapter: ChatAdapter
    var datas: ArrayList<Chat> = arrayListOf()

    // 로그인 정보 관련 변수
    private lateinit var auth: FirebaseAuth
    lateinit var currentUser: FirebaseUser
    lateinit var sender: String
    lateinit var  receiver: String
    // 파이어베이스 연결 관련 변수
    lateinit var database: FirebaseDatabase
    lateinit var myRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        // 데이터베이스 연결
        database = Firebase.database
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        // 현재 사용자의 정보 가져오기
        auth = Firebase.auth
        currentUser = auth.currentUser!!
        sender = currentUser.uid
        // 채팅 상재 정보 가져오기
        receiver = intent.getStringExtra("receiver")?:""
        if(receiver == "") finish()
        // 리사이클러뷰 연결
        adapter = ChatAdapter(datas, sender)
        rc_chat.adapter = adapter
        rc_chat.setHasFixedSize(true)
        rc_chat.layoutManager = LinearLayoutManager(this)
        // 이전 채팅 내역 가져오기
        readChatList()

        myRef = database.getReference("message")
        // 실시간 메세지 받기
        receiveMessage()

        // 메세지 전송하기
        btn_send.setOnClickListener {
            sendMessage()
        }
    }

    // 실시간 메세지 전송 함수
    fun sendMessage() {
        var inputMsg: String = et_message.getText().toString()
        // 아무것도 입력 안하면 보내기 버튼 비활성화
        if (inputMsg.isNotEmpty()) {
            // 날짜 데이터 받아오기
            val c: Calendar = Calendar.getInstance()
            val dataformat: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
            var datetime: String = dataformat.format(c.getTime())

            // 시간별로 날짜가 쌓임
            myRef = database.getReference("message").child(receiver).child(sender).child(datetime)

            // 보내는 형식에 메세지 담기
            val sendMsg: HashMap<String, String> = hashMapOf()
            sendMsg.put("msg", inputMsg)
            
            // 데이터베이스에 저장
            myRef.setValue(sendMsg)
                .addOnSuccessListener {
                    // 채팅 내용 파일에 저장
                    var fos: FileOutputStream = openFileOutput(receiver, MODE_APPEND)
                    fos.write("${sender}\n".toByteArray())
                    fos.write("${inputMsg}\n".toByteArray())
                    fos.write("${datetime}\n".toByteArray())
                    fos.close()
                    // 채팅 보이기
                    adapter.addChat(Chat(sender, inputMsg, datetime))
                    // 메세지 입력 초기화
                    et_message.setText("")
                    inputMsg = ""

                }
                .addOnFailureListener {
                    Toast.makeText(this, "전송에 실패하였습니다.", Toast.LENGTH_SHORT).show()
                }
        }
    }

    // 이전 채팅 내역 불러오기 함수
    fun readChatList() {
        try {
            // 파일명이 receiver인 파일 열기
            var fis: FileInputStream = openFileInput(receiver)
            var buffer: BufferedReader = BufferedReader(InputStreamReader(fis))
            var line: String? = buffer.readLine(); // 파일에서 한줄을 읽어옴
            while (line != null) {
                var from: String? = line
                line = buffer.readLine()
                var msg: String? = line
                line = buffer.readLine()
                var time: String? = line
                Log.e("snap", "${from}, ${msg}, ${time}")
                // 화면에 채팅 보이기
                adapter.addChat(Chat(from?:"", msg?:"", time?:""))
                line = buffer.readLine()
            }
        }
        catch (e: FileNotFoundException) {
            //Toast.makeText(this, "채팅 내역이 없습니다.", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(this, "채팅 불러오기 실패", Toast.LENGTH_SHORT).show()
        }
    }

    // 실시간 메세지 받기 함수
    fun receiveMessage() {
        myRef = database.getReference("message").child(sender).child(receiver)
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // 채팅 내용 읽기
                for (d in dataSnapshot.children) {
                    var time = d.key.toString()
                    var msg = d.child("msg").value.toString()

                    // 채팅 내용 파일에 저장
                    var fos: FileOutputStream = openFileOutput(receiver, MODE_APPEND)
                    fos.write("${receiver}\n".toByteArray())
                    fos.write("${msg}\n".toByteArray())
                    fos.write("${time}\n".toByteArray())
                    fos.close()
                    // 화면에 채팅 보이기
                    adapter.addChat(Chat(receiver, msg, time))
                    d.ref.removeValue()
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
                Log.e("snap1", "수신 실패")
            }
        })
    }
}
