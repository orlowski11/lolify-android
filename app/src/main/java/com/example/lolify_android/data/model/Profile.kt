package com.example.lolify_android.data.model

class Profile(
    var name: String,
    var is_admin: Boolean,
    var email: String,
    var likes: List<Champion>,
    var logs: List<Log>
)