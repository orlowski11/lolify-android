package com.example.lolify_android.data

import com.example.lolify_android.data.model.Champion
import retrofit2.http.GET
import com.example.lolify_android.data.model.Champions
import com.example.lolify_android.data.model.LoginRequest
import com.example.lolify_android.data.model.LoginResponse
import com.example.lolify_android.data.model.RegisterRequest
import com.example.lolify_android.data.model.User
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiInterface {

    @GET("cached/champion")
    suspend fun getChampionList(): Champions

    @GET("champion/{champion_id}")
    fun getChampion(
        @Path("champion_id") championId: String,
        @Header("Authorization") token: String
    ): Call<Champion>

    @POST("login")
    fun login(@Body request: LoginRequest): Call<LoginResponse>

    @POST("register")
    fun register(@Body request: RegisterRequest): Call<LoginResponse>

    @POST("champion/like/{champion_id}")
    fun like(
        @Path("champion_id") championId: String,
        @Header("Authorization") token: String
    ): Call<ResponseBody>

    @POST("champion/dislike/{champion_id}")
    fun dislike(
        @Path("champion_id") championId: String,
        @Header("Authorization") token: String
    ): Call<ResponseBody>

    companion object{
        const val BASE_URL = "https://lolify.fly.dev/api/"
    }
}