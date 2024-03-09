package com.example.lolify_android

import android.content.Context
import android.content.Intent
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
import com.example.lolify_android.champion_activity.ChampionListActivity
import com.example.lolify_android.profile_activity.ProfileActivity
import com.example.lolify_android.ui.theme.LolifyandroidTheme


@Composable
fun BottomNavigationBar(selectedScreen: String, context: Context) {
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
            onClick = { /*TODO*/ },
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
            onClick = { /*TODO*/ },
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
