package com.example.togather

//class UserModel(val userId : String, val nickname: String, val hashtag : ArrayList<String>)

data class UserModel(
    var uid : String = "",
    var nickname : String = "",
    var hashtag : ArrayList<String> = ArrayList()
){
    fun toMap() : Map<String, Any?>{
        return mapOf(
            "uid" to uid,
            "nickname" to nickname,
            "hashtag" to hashtag
        )
    }
}