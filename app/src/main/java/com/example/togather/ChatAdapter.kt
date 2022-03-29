package com.example.togather

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ChatAdapter(private val ChatList: Array<Chat>) :
    RecyclerView.Adapter<ChatAdapter.ViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val from = view.findViewById<TextView>(R.id.tv_from)
        val message = view.findViewById<TextView>(R.id.tv_message)
        val time = view.findViewById<TextView>(R.id.tv_time)
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.list_chat, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.message.text = ChatList.get(position).from
        viewHolder.message.text = ChatList.get(position).message
        viewHolder.time.text = ChatList.get(position).time
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = ChatList.size
        
    fun addChat(chat: Chat) {
        datas.add(chat)
        notifyDataSetChanged()
    }

}
