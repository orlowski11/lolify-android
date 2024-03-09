package com.example.lolify_android.profile_activity

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lolify_android.BottomNavigationBar
import com.example.lolify_android.data.model.Profile

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun UserProfile(profile: Profile) {

    val context = LocalContext.current

    Scaffold(
        bottomBar = { BottomNavigationBar(selectedScreen = "champions", context = context) }
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = profile.email,
                modifier = Modifier
                    .padding(horizontal = 16.dp),
                fontSize = 17.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}