package com.example.lolify_android.champion_activity

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.lolify_android.RetrofitInstance
import com.example.lolify_android.auth_activity.Login
import com.example.lolify_android.data.ApiInterface
import com.example.lolify_android.data.SessionManager
import com.example.lolify_android.data.model.Champion
import com.example.lolify_android.data.model.LoginRequest
import com.example.lolify_android.data.model.Role
import com.example.lolify_android.ui.theme.AppFont
import kotlinx.coroutines.launch
import retrofit2.await

@Composable
fun ChampionDetails(navController: NavController, championList: List<Champion>, champion_id: Int, modifier: Modifier = Modifier) {

    val coroutineScope = rememberCoroutineScope()
    val champion = championList[champion_id]
    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ){
        Text(
            text = champion.name,
            modifier = Modifier
                .padding(horizontal = 16.dp),
            fontSize = 17.sp,
            fontWeight = FontWeight.SemiBold
        )

        Text(
            text = champion.likes_count.toString(),
            modifier = Modifier
                .padding(horizontal = 16.dp),
            fontSize = 17.sp,
            fontWeight = FontWeight.SemiBold
        )

        Button(
            onClick = {
                coroutineScope.launch{
                    like(context, champion.id.toString())
                }
            },
            enabled = true,
            shape = RoundedCornerShape(5.dp),
            colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.tertiary),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                "Like",
                fontFamily = AppFont.Montserrat,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

suspend fun like(context: Context, championId: String){
    var sessionManager: SessionManager
    var apiClient: ApiInterface

    apiClient = RetrofitInstance.api
    sessionManager = SessionManager(context)
    val token = sessionManager.fetchAuthToken()!!

    apiClient.like(championId, "Bearer $token")
        .await()
}