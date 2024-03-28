package com.example.lolify_android.auth_activity

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.example.lolify_android.MainActivity
import com.example.lolify_android.RetrofitInstance
import com.example.lolify_android.data.ApiInterface
import com.example.lolify_android.data.SessionManager
import com.example.lolify_android.data.model.LoginRequest
import com.example.lolify_android.data.model.LoginResponse
import com.example.lolify_android.data.model.RegisterRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

open class AuthFunctions {
    companion object {
        fun Login(email: String, password: String, context: Context) {
            var sessionManager: SessionManager
            var apiClient: ApiInterface

            apiClient = RetrofitInstance.api
            sessionManager = SessionManager(context)

            apiClient.login(LoginRequest(email = email, password = password))
                .enqueue(object : Callback<LoginResponse> {
                    override fun onFailure(call: Call<LoginResponse>, T: Throwable) {
                        Log.d("Error", T.message.toString())
                        showError.value = true
                    }

                    override fun onResponse(
                        call: Call<LoginResponse>,
                        response: Response<LoginResponse>
                    ) {
                        val loginResponse = response.body()

                        if (loginResponse?.access_token != null) {
                            sessionManager.saveAuthToken(loginResponse.access_token)
                            context.startActivity(Intent(context, MainActivity::class.java))
                        } else {
                            Log.d("Error", "Login error")
                            showError.value = true
                        }
                    }
                })
        }

        fun Logout(context: Context) {
            var sessionManager: SessionManager

            sessionManager = SessionManager(context)
            sessionManager.removeAuthToken()
        }

        fun Register(
            email: String,
            name: String,
            password: String,
            password_confirmation: String,
            context: Context
        ) {
            var sessionManager: SessionManager
            var apiClient: ApiInterface

            apiClient = RetrofitInstance.api
            sessionManager = SessionManager(context)

            apiClient.register(
                RegisterRequest(
                    email = email,
                    name = name,
                    password = password,
                    password_confirmation = password_confirmation
                )
            ).enqueue(object : Callback<LoginResponse> {
                override fun onFailure(call: Call<LoginResponse>, T: Throwable) {
                    Log.d("Error", "Register error")
                    Toast.makeText(context, T.message, Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    val loginResponse = response.body()

                    if (loginResponse?.access_token != null) {
                        sessionManager.saveAuthToken(loginResponse.access_token!!)
                        context.startActivity(Intent(context, MainActivity::class.java))
                    } else {
                        Toast.makeText(
                            context,
                            "Email or Username are already taken",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            })
        }
    }

    fun Login(email: String, password: String, context: Context) {
        var sessionManager: SessionManager
        var apiClient: ApiInterface

        apiClient = RetrofitInstance.api
        sessionManager = SessionManager(context)

        apiClient.login(LoginRequest(email = email, password = password))
            .enqueue(object : Callback<LoginResponse> {
                override fun onFailure(call: Call<LoginResponse>, T: Throwable) {
                    Log.d("Error", T.message.toString())
                    showError.value = true
                }

                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    val loginResponse = response.body()

                    if (loginResponse?.access_token != null) {
                        sessionManager.saveAuthToken(loginResponse.access_token)
                        context.startActivity(Intent(context, MainActivity::class.java))
                    } else {
                        Log.d("Error", "Login error")
                        showError.value = true
                    }
                }
            })
    }

    fun Logout(context: Context) {
        var sessionManager: SessionManager

        sessionManager = SessionManager(context)
        sessionManager.removeAuthToken()
    }

    fun Register(
        email: String,
        name: String,
        password: String,
        password_confirmation: String,
        context: Context
    ) {
        var sessionManager: SessionManager
        var apiClient: ApiInterface

        apiClient = RetrofitInstance.api
        sessionManager = SessionManager(context)

        apiClient.register(
            RegisterRequest(
                email = email,
                name = name,
                password = password,
                password_confirmation = password_confirmation
            )
        ).enqueue(object : Callback<LoginResponse> {
            override fun onFailure(call: Call<LoginResponse>, T: Throwable) {
                Log.d("Error", "Register error")
                Toast.makeText(context, T.message, Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(
                call: Call<LoginResponse>,
                response: Response<LoginResponse>
            ) {
                val loginResponse = response.body()

                if (loginResponse?.access_token != null) {
                    sessionManager.saveAuthToken(loginResponse.access_token!!)
                    context.startActivity(Intent(context, MainActivity::class.java))
                } else {
                    Toast.makeText(
                        context,
                        "Email or Username are already taken",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })
    }
}
