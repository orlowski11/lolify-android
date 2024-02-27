package com.example.lolify_android.auth_activity

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.lolify_android.MainActivity
import com.example.lolify_android.data.SessionManager
import com.example.lolify_android.ui.theme.AppFont
import kotlinx.coroutines.launch

@Composable
fun LogoutForm(){
    Surface{
        val context = LocalContext.current
        val coroutineScope = rememberCoroutineScope()
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 30.dp)
        ) {
            Text(
                text = "User name"
            )
            Spacer(modifier = Modifier.height(10.dp))
            Button(
                onClick = {
                    coroutineScope.launch{
                        Logout(context)
                    }
                    context.startActivity(Intent(context, MainActivity::class.java))
                },
                enabled = true,
                shape = RoundedCornerShape(5.dp),
                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.tertiary),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    "Logout",
                    fontFamily = AppFont.Montserrat,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

suspend fun Logout(context: Context){
    var sessionManager: SessionManager

    sessionManager = SessionManager(context)
    sessionManager.removeAuthToken()
}