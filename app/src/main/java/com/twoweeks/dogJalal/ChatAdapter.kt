package com.twoweeks.dogJalal

import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView

//class ChatAdapter(private val datas: ArrayList<ChatData>, val sender: String) :
class ChatAdapter(private val datas: ArrayList<Chat>, private val myId: String) :
    RecyclerView.Adapter<ChatAdapter.ViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val from = view.findViewById<TextView>(R.id.tv_from)
        val msg = view.findViewById<TextView>(R.id.tv_message)
        val time = view.findViewById<TextView>(R.id.tv_time)

        fun bind(item: Chat) {
            from.text = item.from
            if(item.image=="1") { msg.text = "이미지 다운하기" }
            else { msg.text = item.msg }
            time.text = item.time
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        lateinit var view: View
        view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.chat_left, viewGroup, false)
        /*if(viewType == 1) { // 전송자가 나 일때
            view = LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.chat_left, viewGroup, false)
        }
        else { // 전송자가 상대일 떄
            view = LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.chat_left, viewGroup, false)
        }*/
        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(datas[position])

        viewHolder.itemView.setOnClickListener {
            var fileUrl = datas[position].msg
            Log.e("이미지 다운", fileUrl)
            val source = Uri.parse(fileUrl)
            var intent = Intent(Intent.ACTION_VIEW, source)
            startActivity(viewHolder.itemView?.context, intent, null)
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        return datas.size
    }

    /*override fun getItemViewType(position: Int): Int {
        //return super.getItemViewType(position)
        if(datas.get(position).from.equals(myId)) return 1
        else return 0
    }*/

    fun addChat(chat: Chat) {
        datas.add(chat)
        notifyDataSetChanged()
    }
}