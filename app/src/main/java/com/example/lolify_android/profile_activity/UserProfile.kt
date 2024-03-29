package com.example.lolify_android.profile_activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.example.lolify_android.BottomNavigationBar
import com.example.lolify_android.RetrofitInstance
import com.example.lolify_android.auth_activity.LogoutForm
import com.example.lolify_android.champion_activity.ChampionDetailsActivity
import com.example.lolify_android.champion_activity.Skin
import com.example.lolify_android.data.ApiInterface
import com.example.lolify_android.data.SessionManager
import com.example.lolify_android.data.model.Champion
import com.example.lolify_android.data.model.CurrentUserResponse
import com.example.lolify_android.data.model.Profile
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

var isCurrentUsersProfile = mutableStateOf(false)

@Composable
fun UserProfile(profile: Profile) {

    val context = LocalContext.current
    isCurrentUsersProfile(profile.name, context)

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                selectedScreen = if (isCurrentUsersProfile.value) {
                    "profile"
                } else {
                    "users"
                },
                context = context
            )
        }
    ) { innerPadding ->
        Surface(
            color = MaterialTheme.colorScheme.primary
        ) {
            Column(
                modifier = Modifier
                    .padding(bottom = innerPadding.calculateBottomPadding())
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = profile.name,
                    modifier = Modifier
                        .padding(16.dp),
                    fontSize = 27.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.tertiary
                )

                Divider(
                    color = MaterialTheme.colorScheme.secondary,
                    thickness = 1.dp,
                    modifier = Modifier
                        .padding(vertical = 12.dp)
                )

                Text(
                    text = "Likes: ",
                    modifier = Modifier
                        .padding(bottom = 14.dp),
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.tertiary
                )

                LazyRow(
                    modifier = Modifier
                        .height(130.dp)
                ) {
                    items(profile.likes.size) { id ->
                        Spacer(
                            modifier = Modifier
                                .width(8.dp)
                        )
                        Liked(profile.likes[id])
                    }
                }

                Divider(
                    color = MaterialTheme.colorScheme.secondary,
                    thickness = 1.dp,
                    modifier = Modifier
                        .padding(vertical = 12.dp)
                )

                Text(
                    text = "Logs: ",
                    modifier = Modifier
                        .padding(bottom = 14.dp),
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.tertiary
                )

                LazyColumn(
                    modifier = Modifier
                        .height(200.dp)
                        .background(MaterialTheme.colorScheme.secondary)
                ) {
                    items(profile.logs.size) { id ->
                        Spacer(
                            modifier = Modifier
                                .height(2.dp)
                        )
                        Logs(profile.logs[id])
                    }
                }

                Spacer(Modifier.weight(1f))

                if (isCurrentUsersProfile.value) {
                    Divider(
                        color = MaterialTheme.colorScheme.secondary,
                        thickness = 1.dp,
                        modifier = Modifier
                            .padding(vertical = 12.dp)
                    )
                    LogoutForm()
                }
            }
        }
    }
}

fun isCurrentUsersProfile(name: String, context: Context) {
    val apiClient = RetrofitInstance.api
    val sessionManager = SessionManager(context)
    val token = sessionManager.fetchAuthToken()

    apiClient.getCurrentUser("Bearer $token")
        .enqueue(object : Callback<CurrentUserResponse> {
            override fun onFailure(call: Call<CurrentUserResponse>, T: Throwable) {
                Log.d("Error", T.message.toString())
            }

            override fun onResponse(
                call: Call<CurrentUserResponse>,
                response: Response<CurrentUserResponse>
            ) {
                val currentUserResponse = response.body()
                val username = currentUserResponse?.name
                isCurrentUsersProfile.value = (username == name)
            }
        })
}

@Composable
fun Liked(champion: Champion) {
    val context = LocalContext.current
    val imageState = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current).data(champion.image_link)
            .size(Size.ORIGINAL)
            .build()
    ).state

    var sessionManager: SessionManager
    var apiClient: ApiInterface

    apiClient = RetrofitInstance.api
    sessionManager = SessionManager(context)
    val token = sessionManager.fetchAuthToken()

    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .size(140.dp)
            .background(MaterialTheme.colorScheme.secondary),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (imageState is AsyncImagePainter.State.Error) {
            Box(
                modifier = Modifier
                    .height(110.dp)
            ) {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.tertiary,
                    strokeWidth = 1.dp
                )
            }
        }
        if (imageState is AsyncImagePainter.State.Success) {
            Image(
                modifier = Modifier
                    .height(110.dp),
                painter = imageState.painter,
                contentDescription = champion.name,
                contentScale = ContentScale.Crop
            )
        }

        Spacer(
            modifier = Modifier.height(2.dp)
        )

        Text(
            text = champion.name,
            modifier = Modifier
                .padding(top = 2.dp),
            fontSize = 8.sp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.tertiary,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun Logs(log: com.example.lolify_android.data.model.Log) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = "- " + log.text,
            modifier = Modifier
                .padding(top = 2.dp, start = 8.dp),
            fontSize = 17.sp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.tertiary
        )
    }
}