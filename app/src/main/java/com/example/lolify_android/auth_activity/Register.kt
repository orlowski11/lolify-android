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
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
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
import androidx.navigation.NavController
import com.example.lolify_android.MainActivity
import com.example.lolify_android.RetrofitInstance
import com.example.lolify_android.data.ApiInterface
import com.example.lolify_android.data.SessionManager
import com.example.lolify_android.data.model.LoginRequest
import com.example.lolify_android.data.model.LoginResponse
import com.example.lolify_android.data.model.RegisterRequest
import com.example.lolify_android.ui.theme.AppFont
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

val showEmailError = mutableStateOf(false)
val showNameError = mutableStateOf(false)
val showPasswordError = mutableStateOf(false)
val showPasswordConfirmError = mutableStateOf(false)

@Composable
fun RegisterForm(navController: NavController) {
    Surface(
        color = MaterialTheme.colorScheme.primary
    ){
        var credentials by remember { mutableStateOf(RegisterRequest()) }
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
            NameField(
                value = credentials.name,
                onChange = { data -> credentials = credentials.copy(name = data)},
                modifier = Modifier.fillMaxWidth()
            )
            PasswordField(
                value = credentials.password,
                onChange = { data -> credentials = credentials.copy(password = data)},
                submit = { },
                modifier = Modifier.fillMaxWidth()
            )
            PasswordConfirmationField(
                value = credentials.password_confirmation,
                onChange = { data -> credentials = credentials.copy(password_confirmation = data)},
                submit = { },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(10.dp))
            Button(
                onClick = {
                    showEmailError.value = !isValidEmail(credentials.email)
                    showNameError.value = credentials.name.isEmpty()
                    showPasswordError.value = credentials.password.length <= 8
                    showPasswordConfirmError.value = credentials.password != credentials.password_confirmation

                    if(
                        showEmailError.value == false
                        && showNameError.value == false
                        && showPasswordError.value == false
                        && showPasswordConfirmError.value == false
                    ) {
                        coroutineScope.launch {
                            Register(
                                credentials.email,
                                credentials.name,
                                credentials.password,
                                credentials.password_confirmation,
                                context
                            )
                        }
                    }
                },
                enabled = true,
                shape = RoundedCornerShape(5.dp),
                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.tertiary),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    "Register",
                    fontFamily = AppFont.Montserrat,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            if(showEmailError.value){
                Text(
                    text = "Email is not valid",
                    fontSize = 12.sp,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier
                        .padding(10.dp)
                )
            }
            if(showNameError.value){
                Text(
                    text = "Name cannot be empty",
                    fontSize = 12.sp,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier
                        .padding(10.dp)
                )
            }
            if(showPasswordError.value){
                Text(
                    text = "Password must be at least 8 characters long",
                    fontSize = 12.sp,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier
                        .padding(10.dp)
                )
            }
            if(showPasswordConfirmError.value){
                Text(
                    text = "Passwords don't match",
                    fontSize = 12.sp,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier
                        .padding(10.dp)
                )
            }

            Text(
                text = "Already have an account?",
                fontSize = 12.sp,
                textAlign = TextAlign.Justify,
                color = MaterialTheme.colorScheme.tertiary,
                modifier = Modifier
                    .padding(10.dp)
            )
            Button(
                onClick = {
                    navController.navigate("login")
                },
                enabled = true,
                shape = RoundedCornerShape(5.dp),
                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.secondary),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    "Login",
                    fontFamily = AppFont.Montserrat,
                    color = MaterialTheme.colorScheme.tertiary
                )
            }

        }
    }
}

suspend fun Register(
    email: String,
    name: String,
    password: String,
    password_confirmation: String,
    context: Context
){
    var sessionManager: SessionManager
    var apiClient: ApiInterface

    apiClient = RetrofitInstance.api
    sessionManager = SessionManager(context)

    apiClient.register(RegisterRequest(
        email = email,
        name = name,
        password = password,
        password_confirmation = password_confirmation
    )).enqueue(object: Callback<LoginResponse> {
            override fun onFailure(call: Call<LoginResponse>, T: Throwable){
                Log.d("Error","Register error")
            }
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>){
                val loginResponse = response.body()

                if(loginResponse?.access_token != null) {
                    sessionManager.saveAuthToken(loginResponse.access_token)
                    context.startActivity(Intent(context, MainActivity::class.java))
                } else{
                    Log.d("Error","Register error")
                }
            }
        })
}

fun isValidEmail(email: String): Boolean {
    val emailRegex = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+".toRegex()
    return email.matches(emailRegex)
}