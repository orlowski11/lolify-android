package com.example.lolify_android.auth_activity

import android.annotation.SuppressLint
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
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import com.example.lolify_android.BottomNavigationBar
import com.example.lolify_android.MainActivity
import com.example.lolify_android.RetrofitInstance
import com.example.lolify_android.data.ApiInterface
import com.example.lolify_android.data.SessionManager
import com.example.lolify_android.data.model.LoginRequest
import com.example.lolify_android.data.model.LoginResponse
import com.example.lolify_android.ui.theme.AppFont
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

val showError = mutableStateOf(false)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun LoginForm(navController: NavController) {

    val context = LocalContext.current

    Scaffold(
        bottomBar = { BottomNavigationBar(selectedScreen = "profile", context = context) }
    ) {
        Surface(
            color = MaterialTheme.colorScheme.primary
        ) {
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
                    onChange = { data -> credentials = credentials.copy(email = data) },
                    modifier = Modifier.fillMaxWidth()
                )
                PasswordField(
                    value = credentials.password,
                    onChange = { data -> credentials = credentials.copy(password = data) },
                    submit = { },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(10.dp))
                Button(
                    onClick = {
                        coroutineScope.launch {
                            Login(credentials.email, credentials.password, context)
                        }
                    },
                    enabled = true,
                    shape = RoundedCornerShape(5.dp),
                    colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.tertiary),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        "Login",
                        fontFamily = AppFont.Montserrat,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                if (showError.value) {
                    Text(
                        text = "These credentials don't match our records",
                        fontSize = 12.sp,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier
                            .padding(10.dp)
                    )
                }
                Text(
                    text = "Don't have an account yet?",
                    fontSize = 12.sp,
                    textAlign = TextAlign.Justify,
                    color = MaterialTheme.colorScheme.tertiary,
                    modifier = Modifier
                        .padding(10.dp)
                )
                Button(
                    onClick = {
                        navController.navigate("register")
                    },
                    enabled = true,
                    shape = RoundedCornerShape(5.dp),
                    colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.secondary),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        "Register",
                        fontFamily = AppFont.Montserrat,
                        color = MaterialTheme.colorScheme.tertiary
                    )
                }
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
                Log.d("Error", T.message.toString())
                showError.value = true
            }
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>){
                val loginResponse = response.body()

                if(loginResponse?.access_token != null) {
                    sessionManager.saveAuthToken(loginResponse.access_token)
                    context.startActivity(Intent(context, MainActivity::class.java))

                    TODO("save current user")
                } else{
                    Log.d("Error","Login error")
                    showError.value = true
                }
            }
        })
}