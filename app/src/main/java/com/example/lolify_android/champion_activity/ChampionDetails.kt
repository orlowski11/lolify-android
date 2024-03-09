package com.example.lolify_android.champion_activity

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
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
import androidx.compose.material3.Scaffold
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
import com.example.lolify_android.BottomNavigationBar
import com.example.lolify_android.MainActivity
import com.example.lolify_android.RetrofitInstance
import com.example.lolify_android.auth_activity.Login
import com.example.lolify_android.auth_activity.showError
import com.example.lolify_android.data.ApiInterface
import com.example.lolify_android.data.SessionManager
import com.example.lolify_android.data.model.Champion
import com.example.lolify_android.data.model.LikePivot
import com.example.lolify_android.data.model.LoginRequest
import com.example.lolify_android.data.model.LoginResponse
import com.example.lolify_android.data.model.Role
import com.example.lolify_android.data.model.User
import com.example.lolify_android.ui.theme.AppFont
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.internal.wait
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.await
import retrofit2.awaitResponse
import kotlin.properties.Delegates

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ChampionDetails(
    championList: List<Champion>,
    champion_id: Int,
    likesIt: Boolean,
    activity: Activity,
    modifier: Modifier = Modifier
) {

    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    Scaffold(
        bottomBar = { BottomNavigationBar(selectedScreen = "champions", context = context) }
    ) {
        if (championList.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                CircularProgressIndicator()
            }
        } else {
            val champion = championList[champion_id]
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
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

                Text(
                    text = likesIt.toString(),
                    modifier = Modifier
                        .padding(horizontal = 16.dp),
                    fontSize = 17.sp,
                    fontWeight = FontWeight.SemiBold
                )

                Button(
                    onClick = {
                        coroutineScope.launch {
                            like(
                                context,
                                champion.id.toString(),
                                champion_id.toString(),
                                likesIt,
                                activity
                            )
                        }
                    },
                    enabled = true,
                    shape = RoundedCornerShape(5.dp),
                    colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.tertiary),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    if (likesIt) {
                        Text(
                            "Dislike",
                            fontFamily = AppFont.Montserrat,
                            color = MaterialTheme.colorScheme.primary
                        )
                    } else {
                        Text(
                            "Like",
                            fontFamily = AppFont.Montserrat,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        }
    }
}


suspend fun like(
    context: Context,
    championId: String,
    championListId: String,
    likesIt: Boolean,
    activity: Activity
){
    var sessionManager: SessionManager
    var apiClient: ApiInterface

    apiClient = RetrofitInstance.api
    sessionManager = SessionManager(context)
    val token = sessionManager.fetchAuthToken()

    if (token != null) {
        if (likesIt) {
            apiClient.dislike(championId, "Bearer $token")
                .await()
        } else {
            apiClient.like(championId, "Bearer $token")
                .await()
        }

        var likesIt = !likesIt

        val intent = Intent(context, ChampionDetailsActivity::class.java)
        intent.putExtra("champion_id", championListId)
        intent.putExtra("likes_it", likesIt.toString())
        context.startActivity(intent)
        activity.finish()
    } else {

    }
}