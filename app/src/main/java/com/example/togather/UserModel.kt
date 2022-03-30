package com.example.togather

data class UserModel(
    var uid : String = "",
    var nickname : String = "",
    var hashtag : ArrayList<String> = ArrayList(),
    var profileUrl : String = ""
)