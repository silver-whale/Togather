package com.example.togather

import android.os.Bundle
import android.renderscript.ScriptGroup
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_chat.*
import org.w3c.dom.Comment
import java.text.SimpleDateFormat
import java.util.*

class ChatActivity : AppCompatActivity() {
    //private const val TAG = "ChatActivity"
    private lateinit var auth: FirebaseAuth
    lateinit var currentUser: FirebaseUser
    lateinit var emailAddr: String
    lateinit var receiveMsg: String
    lateinit var database: FirebaseDatabase

    //private lateinit var adapter: ChatAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        // 데이터베이스 연결
        database = Firebase.database
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        // data에 채팅 내용 넣기
        //

        auth = Firebase.auth

        btn_send.setOnClickListener {
            sendMessage()
        }
    }

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        currentUser = auth.currentUser!!
    }

    /*fun sendMessage(user: FirebaseUser) {
        // inputMsg : 보내고자하는 메세지 내용
        var inputMsg: String = et_message.getText().toString()
        Toast.makeText(this, inputMsg, Toast.LENGTH_SHORT).show()
    }*/

    fun sendMessage() {
        var inputMsg: String = et_message.getText().toString()
        // 아무것도 입력 안하면 보내기 버튼 비활성화

        // 날짜 데이터 받아오기
        val c: Calendar = Calendar.getInstance()
        val dataformat: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
        var datetime: String = dataformat.format(c.getTime())


        // 시간별로 날짜가 쌓임
        val myRef = database.getReference("message").child(datetime)


        // 데이터베이스에 저장 내용과 형태
        val msg: Hashtable<String, String> = Hashtable<String, String>()
        msg.put("From", currentUser.email.toString()) // 보내는 사람 이메일
        // msg.put("To", emailAddr) // 받는 사람 이메일
        msg.put("text", inputMsg)

        Toast.makeText(this, inputMsg, Toast.LENGTH_SHORT).show()

        myRef.setValue(msg)

    }



}