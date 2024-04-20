package com.example.lolify_android.navigation

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.example.lolify_android.MainActivity
import com.example.lolify_android.RetrofitInstance
import com.example.lolify_android.auth_activity.AuthActivity
import com.example.lolify_android.champion_activity.ChampionDetailsActivity
import com.example.lolify_android.champion_activity.ChampionListActivity
import com.example.lolify_android.data.ApiInterface
import com.example.lolify_android.data.SessionManager
import com.example.lolify_android.data.model.Champion
import com.example.lolify_android.data.model.CurrentUserResponse
import com.example.lolify_android.profile_activity.ProfileActivity
import com.example.lolify_android.profile_activity.SearchProfileActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

open class Navigation {

    companion object {
        val navigation: Navigation = Navigation()
        fun startMainActivity(context: Context) {
            navigation.startMainActivity(context)
        }

        fun startChampionActivity(context: Context) {
            navigation.startChampionActivity(context)
        }

        fun startSearchProfileActivity(context: Context) {
            navigation.startSearchProfileActivity(context)
        }

        fun startProfileActivity(context: Context, sessionManager: SessionManager) {
            navigation.startProfileActivity(context, sessionManager)
        }

        fun startChampionDetailsActivity(
            context: Context,
            token: String?,
            apiClient: ApiInterface,
            champion: Champion,
            champion_id: Int
        ) {
            navigation.startChampionDetailsActivity(
                context,
                token,
                apiClient,
                champion,
                champion_id
            )
        }
    }

    fun startMainActivity(context: Context) {
        val intent = Intent(context, MainActivity::class.java)
        context.startActivity(intent)
    }

    fun startChampionActivity(context: Context) {
        val intent = Intent(context, ChampionListActivity::class.java)
        context.startActivity(intent)
    }

    fun startSearchProfileActivity(context: Context) {
        val intent = Intent(context, SearchProfileActivity::class.java)
        context.startActivity(intent)
    }

    fun startProfileActivity(context: Context, sessionManager: SessionManager) {
        if (sessionManager.fetchAuthToken() == null) {
            val intent = Intent(context, AuthActivity::class.java)
            context.startActivity(intent)
        } else {
            val apiClient = RetrofitInstance.api
            val token = sessionManager.fetchAuthToken()

            apiClient.getCurrentUser("Bearer $token")
                .enqueue(object : Callback<CurrentUserResponse> {
                    override fun onFailure(call: Call<CurrentUserResponse>, T: Throwable) {
                        Log.d("Error", T.message.toString())
                        Toast.makeText(context, T.message, Toast.LENGTH_SHORT).show()
                    }

                    override fun onResponse(
                        call: Call<CurrentUserResponse>,
                        response: Response<CurrentUserResponse>
                    ) {
                        val currentUserResponse = response.body()
                        val username = currentUserResponse?.name
                        val intent = Intent(context, ProfileActivity::class.java)
                        intent.putExtra("user_name", username)
                        context.startActivity(intent)
                    }
                })
        }
    }

    fun startChampionDetailsActivity(
        context: Context,
        token: String?,
        apiClient: ApiInterface,
        champion: Champion,
        champion_id: Int
    ) {
        if (token != null) {
            apiClient
                .getChampion(champion.id.toString(), "Bearer $token")
                .enqueue(object : Callback<Champion> {
                    override fun onFailure(call: Call<Champion>, t: Throwable) {
                        Log.e("Error", t.message.toString())
                    }

                    override fun onResponse(
                        call: Call<Champion>,
                        response: Response<Champion>
                    ) {
                        val intent = Intent(context, ChampionDetailsActivity::class.java)
                        intent.putExtra("champion_id", champion_id.toString())
                        intent.putExtra(
                            "likes_it",
                            response.body()!!.current_user_likes_it.toString()
                        )
                        context.startActivity(intent)
                    }
                })
        } else {
            val intent = Intent(context, ChampionDetailsActivity::class.java)
            intent.putExtra("champion_id", champion_id.toString())
            intent.putExtra("likes_it", "false")
            context.startActivity(intent)
        }
    }
}