package com.example.lolify_android.data.model

data class RegisterRequest(
    var email: String = "",
    var name: String = "",
    var password: String = "",
    var password_confirmation: String = "",
)