package com.example.lolify_android.data.model

data class Champion(
    val id: Int,
    val created_at: String,
    val description: String,
    val image_link: String,
    val name: String,
    val title: String,
    val updated_at: String
)