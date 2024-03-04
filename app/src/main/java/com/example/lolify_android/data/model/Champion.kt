package com.example.lolify_android.data.model

data class Champion(
    var id: Int,
    var created_at: String,
    var current_user_likes_it: Boolean,
    var description: String,
    var image_link: String,
    var likes_count: Int,
    var name: String,
    var roles: List<Role>,
    var skills: List<Skill>,
    var skins: List<Skin>,
    var title: String,
    var updated_at: String,
    var users_that_liked: List<UsersThatLiked>
)