package com.example.lolify_android

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.lolify_android.data.model.Champion
import com.example.lolify_android.data.model.Role

@Composable
fun ChampionDetails(navController: NavController, championList: List<Champion>, champion_id: Int, modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ){
        Text(
            text = championList[champion_id].roles[0].name,
            modifier = Modifier
                .padding(horizontal = 16.dp),
            fontSize = 17.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}