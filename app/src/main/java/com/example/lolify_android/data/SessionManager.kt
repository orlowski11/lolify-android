package com.example.lolify_android.data

import android.content.Context
import android.content.SharedPreferences
import com.example.lolify_android.R

class SessionManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences(
        context.getString(R.string.app_name), Context.MODE_PRIVATE
    )

    companion object {
        const val USER_TOKEN = "user_token"
    }

    fun saveAuthToken(token: String) {
        val currentTime = System.currentTimeMillis()
        val expirationTimestamp = currentTime + 3600000 // 1 hour in milliseconds
        val editor = prefs.edit()
        editor.putString(USER_TOKEN, token)
        editor.putLong("$USER_TOKEN.expiration", expirationTimestamp)
        editor.apply()
    }

    fun removeAuthToken() {
        val editor = prefs.edit()
        editor.remove(USER_TOKEN)
        editor.remove("$USER_TOKEN.expiration")
        editor.apply()
    }

    fun fetchAuthToken(): String? {
        val expirationTimestamp = prefs.getLong("$USER_TOKEN.expiration", -1)
        val currentTime = System.currentTimeMillis()
        return if (expirationTimestamp > currentTime) {
            prefs.getString(USER_TOKEN, null)
        } else {
            removeAuthToken()
            null
        }
    }
}
