package com.example.lolify_android.data

import com.example.lolify_android.data.model.Champion
import retrofit2.http.GET
import com.example.lolify_android.data.model.Champions
import com.example.lolify_android.data.model.CurrentUserResponse
import com.example.lolify_android.data.model.LoginRequest
import com.example.lolify_android.data.model.LoginResponse
import com.example.lolify_android.data.model.Profile
import com.example.lolify_android.data.model.RegisterRequest
import okhttp3.ResponseBody
import retrofit2.Call
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

    @GET("profile/{user_name}")
    suspend fun getUserProfile(
        @Path("user_name") username: String,
        @Header("Authorization") token: String
    ): Profile

    @GET("me")
    fun getCurrentUser(
        @Header("Authorization") token: String
    ): Call<CurrentUserResponse>

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