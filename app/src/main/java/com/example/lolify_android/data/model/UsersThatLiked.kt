package com.example.lolify_android.data.model

data class UsersThatLiked(
    val created_at: String,
    val email: String,
    val email_verified_at: Any,
    val id: Int,
    val is_admin: String,
    val name: String,
    val pivot: LikePivot,
    val updated_at: String
)