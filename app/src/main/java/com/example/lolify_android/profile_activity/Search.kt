package com.example.lolify_android.profile_activity

import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.example.lolify_android.RetrofitInstance
import com.example.lolify_android.data.ApiInterface
import com.example.lolify_android.data.SessionManager

open class Search {
    companion object {
        suspend fun SearchUser(name: String, context: Context) {
            var sessionManager: SessionManager
            var apiClient: ApiInterface

            apiClient = RetrofitInstance.api
            sessionManager = SessionManager(context)

            val token = sessionManager.fetchAuthToken()

            if (token != null) {
                val Profile = try {
                    apiClient.getUserProfile(name, "Bearer $token")
                } catch (E: Exception) {
                    null
                }
                if (Profile != null) {
                    val username = Profile.name
                    val intent = Intent(context, ProfileActivity::class.java)
                    intent.putExtra("user_name", username)
                    context.startActivity(intent)
                } else {
                    Toast.makeText(context, "User not found", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(context, "Please log in to search other users", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    suspend fun SearchUser(name: String, context: Context) {
        var sessionManager: SessionManager
        var apiClient: ApiInterface

        apiClient = RetrofitInstance.api
        sessionManager = SessionManager(context)

        val token = sessionManager.fetchAuthToken()

        if (token != null) {
            val Profile = try {
                apiClient.getUserProfile(name, "Bearer $token")
            } catch (E: Exception) {
                null
            }
            if (Profile != null) {
                val username = Profile.name
                val intent = Intent(context, ProfileActivity::class.java)
                intent.putExtra("user_name", username)
                context.startActivity(intent)
            } else {
                Toast.makeText(context, "User not found", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(context, "Please log in to search other users", Toast.LENGTH_SHORT)
                .show()
        }
    }
}