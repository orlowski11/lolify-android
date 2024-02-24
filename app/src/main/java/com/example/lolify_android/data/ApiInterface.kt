package com.example.lolify_android.data

import retrofit2.http.GET
import com.example.lolify_android.data.model.Champions
import com.example.lolify_android.data.model.LoginRequest
import com.example.lolify_android.data.model.LoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiInterface {

    @GET("cached/champion")
    suspend fun getChampionList(): Champions

    @POST("login")
    fun login(@Body request: LoginRequest): Call<LoginResponse>

    companion object{
        const val BASE_URL = "https://lolify.fly.dev/api/"
    }
}