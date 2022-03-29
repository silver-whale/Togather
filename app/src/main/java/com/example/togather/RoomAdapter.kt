package com.example.togather

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.room.*

class RoomAdapter(private val rooms: ArrayList<Room>) :
    RecyclerView.Adapter<RoomAdapter.ViewHolder>() {

    // 뷰와 위젯 연결
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val roomName = view.findViewById<TextView>(R.id.tv_room)
        fun bind(item: Room) {
            roomName.text = item.name
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.room, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(rooms[position])

        viewHolder.itemView.setOnClickListener {
            val intent = Intent(viewHolder.itemView?.context, ChatActivity::class.java)
            intent.putExtra("receiver", rooms[position].name)
            ContextCompat.startActivity(viewHolder.itemView.context, intent, null)
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        return rooms.size
    }

    fun addRoom(room: Room) {
        rooms.add(room)
        notifyDataSetChanged()
    }

}
