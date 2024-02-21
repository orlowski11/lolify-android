package com.example.lolify_android.data.model

data class Champion(
    val id: Int,
    val created_at: String,
    val current_user_likes_it: Boolean,
    val description: String,
    val image_link: String,
    val likes_count: Int,
    val name: String,
    val roles: List<Role>,
    val skills: List<Skill>,
    val skins: List<Skin>,
    val title: String,
    val updated_at: String,
    val users_that_liked: List<UsersThatLiked>
)