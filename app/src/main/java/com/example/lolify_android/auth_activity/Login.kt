package com.example.lolify_android.auth_activity

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.navigation.NavController
import com.example.lolify_android.MainActivity
import com.example.lolify_android.RetrofitInstance
import com.example.lolify_android.data.ApiInterface
import com.example.lolify_android.data.SessionManager
import com.example.lolify_android.data.model.LoginRequest
import com.example.lolify_android.data.model.LoginResponse
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun LoginForm(navController: NavController) {
    Surface {
        var credentials by remember { mutableStateOf(LoginRequest()) }
        val context = LocalContext.current
        val coroutineScope = rememberCoroutineScope()
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 30.dp)
        ) {
            EmailField(
                value = credentials.email,
                onChange = { data -> credentials = credentials.copy(email = data)},
                modifier = Modifier.fillMaxWidth()
            )
            PasswordField(
                value = credentials.password,
                onChange = { data -> credentials = credentials.copy(password = data)},
                submit = { },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(10.dp))
            Button(
                onClick = {
                    coroutineScope.launch{
                        Login(credentials.email, credentials.password, context)
                    }
                },
                enabled = true,
                shape = RoundedCornerShape(5.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Login")
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 10.dp)
            ){
                Text(
                    text = "Don't have an account yet?",
                    fontSize = 12.sp,
                    textAlign = TextAlign.Justify,
                    modifier = Modifier
                        .padding(10.dp)
                )
                Text(
                    text = "Register",
                    fontSize = 12.sp,
                    textAlign = TextAlign.Justify,
                    modifier = Modifier
                        .padding(10.dp)
                        .clickable {
                            navController.navigate("register")
                        }
                )
            }
        }
    }
}

suspend fun Login(email: String, password: String, context: Context){
    var sessionManager: SessionManager
    var apiClient: ApiInterface

    apiClient = RetrofitInstance.api
    sessionManager = SessionManager(context)

    apiClient.login(LoginRequest(email = email, password = password))
        .enqueue(object: Callback<LoginResponse> {
            override fun onFailure(call: Call<LoginResponse>, T: Throwable){
                Log.d("Error","Login error")
            }
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>){
                val loginResponse = response.body()

                if(loginResponse?.access_token != null) {
                    sessionManager.saveAuthToken(loginResponse.access_token)
                    context.startActivity(Intent(context, MainActivity::class.java))
                } else{
                    Log.d("Error","Login error")
                }
            }
        })
}