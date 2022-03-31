package com.twoweeks.togather

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

class ListAdapter (val context : Context, val userList : ArrayList<UserModel>) : BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = LayoutInflater.from(context).inflate(R.layout.user_item, null)

//        val photo = view.findViewById<ImageView>(R.id.photo)
        val nickname = view.findViewById<TextView>(R.id.show_nickname)
        val hashtag = view.findViewById<TextView>(R.id.show_hashtag)

        val user = userList[position]
//        val resourceId = context.resources.getIdentifier(user.photo, "drawable", context.packageName)
//        photo.setImageResource(resourceId)
        nickname.text = user.nickname
        hashtag.text = user.hashtag.toString()

        return view
    }

    override fun getItem(position: Int): Any {
        return userList[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return userList.size
    }

}