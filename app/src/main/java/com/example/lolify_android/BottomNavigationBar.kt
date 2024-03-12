package com.example.lolify_android

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.lolify_android.auth_activity.AuthActivity
import com.example.lolify_android.auth_activity.showError
import com.example.lolify_android.champion_activity.ChampionListActivity
import com.example.lolify_android.data.SessionManager
import com.example.lolify_android.data.model.CurrentUserResponse
import com.example.lolify_android.data.model.LoginRequest
import com.example.lolify_android.data.model.LoginResponse
import com.example.lolify_android.profile_activity.ProfileActivity
import com.example.lolify_android.profile_activity.SearchProfileActivity
import com.example.lolify_android.ui.theme.LolifyandroidTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


@Composable
fun BottomNavigationBar(selectedScreen: String, context: Context) {

    val sessionManager = SessionManager(context)

    NavigationBar(
        modifier = Modifier
            .fillMaxWidth(),
        containerColor = MaterialTheme.colorScheme.secondary,
    ) {
        NavigationBarItem(
            selected = selectedScreen == "home",
            colors = NavigationBarItemDefaults.colors(
                indicatorColor = MaterialTheme.colorScheme.onPrimaryContainer
            ),
            onClick = {
                val intent = Intent(context, MainActivity::class.java)
                context.startActivity(intent)
            },
            label = {
                Text(
                    "Home",
                    color = MaterialTheme.colorScheme.tertiary
                )
            },
            icon = {
                Icon(
                    Icons.Default.Home,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.tertiary
                )
            }
        )
        NavigationBarItem(
            selected = selectedScreen == "champions",
            colors = NavigationBarItemDefaults.colors(
                indicatorColor = MaterialTheme.colorScheme.onPrimaryContainer
            ),
            onClick = {
                val intent = Intent(context, ChampionListActivity::class.java)
                context.startActivity(intent)
            },
            label = {
                Text(
                    "Champions",
                    color = MaterialTheme.colorScheme.tertiary
                )
            },
            icon = {
                Icon(
                    Icons.Default.Star,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.tertiary
                )
            }
        )
        NavigationBarItem(
            selected = selectedScreen == "users",
            colors = NavigationBarItemDefaults.colors(
                indicatorColor = MaterialTheme.colorScheme.onPrimaryContainer
            ),
            onClick = {
                val intent = Intent(context, SearchProfileActivity::class.java)
                context.startActivity(intent)
            },
            label = {
                Text(
                    "Users",
                    color = MaterialTheme.colorScheme.tertiary
                )
            },
            icon = {
                Icon(
                    Icons.Default.Search,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.tertiary
                )
            }
        )
        NavigationBarItem(
            selected = selectedScreen == "profile",
            colors = NavigationBarItemDefaults.colors(
                indicatorColor = MaterialTheme.colorScheme.onPrimaryContainer
            ),
            onClick = {
                if(sessionManager.fetchAuthToken() == null) {
                    val intent = Intent(context, AuthActivity::class.java)
                    context.startActivity(intent)
                } else {
                    val apiClient = RetrofitInstance.api
                    val token = sessionManager.fetchAuthToken()

                    apiClient.getCurrentUser("Bearer $token")
                        .enqueue(object: Callback<CurrentUserResponse> {
                            override fun onFailure(call: Call<CurrentUserResponse>, T: Throwable){
                                Log.d("Error", T.message.toString())
                                Toast.makeText(context, T.message, Toast.LENGTH_SHORT).show()
                            }
                            override fun onResponse(call: Call<CurrentUserResponse>, response: Response<CurrentUserResponse>){
                                val currentUserResponse = response.body()
                                val username = currentUserResponse?.name
                                val intent = Intent(context, ProfileActivity::class.java)
                                intent.putExtra("user_name", username)
                                context.startActivity(intent)
                            }
                        })
                }
            },
            label = {
                Text(
                    "Profile",
                    color = MaterialTheme.colorScheme.tertiary
                )
            },
            icon = {
                Icon(
                    Icons.Default.Person,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.tertiary
                )
            }
        )
    }
}
