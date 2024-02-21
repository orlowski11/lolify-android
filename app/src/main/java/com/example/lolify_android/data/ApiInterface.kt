package com.example.lolify_android.data

import retrofit2.http.GET
import com.example.lolify_android.data.model.Champions

interface ApiInterface {

    @GET("cached/champion")
    suspend fun getChampionList(): Champions

    companion object{
        const val BASE_URL = "https://lolify.fly.dev/api/"
    }
}