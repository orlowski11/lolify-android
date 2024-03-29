package com.example.lolify_android

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import coil.size.Size
import com.example.lolify_android.auth_activity.AuthActivity
import com.example.lolify_android.champion_activity.Champion
import com.example.lolify_android.champion_activity.ChampionDetailsActivity
import com.example.lolify_android.champion_activity.ChampionListActivity
import com.example.lolify_android.champion_activity.Skill
import com.example.lolify_android.data.model.Champion
import com.example.lolify_android.profile_activity.ProfileActivity
import com.example.lolify_android.ui.theme.AppFont


@Composable
fun Homepage(top3champions: List<Champion>, modifier: Modifier = Modifier) {
    val context = LocalContext.current

    Scaffold(
        bottomBar = { BottomNavigationBar(selectedScreen = "home", context = context) },
    ) { innerPadding ->
        Surface(
            color = MaterialTheme.colorScheme.primary
        ) {
            Column(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = modifier
                    .padding(8.dp, 8.dp, 8.dp, innerPadding.calculateBottomPadding() + 6.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                val imageLoader = ImageLoader.Builder(context)
                    .components {
                        if (Build.VERSION.SDK_INT >= 28) {
                            add(ImageDecoderDecoder.Factory())
                        } else {
                            add(GifDecoder.Factory())
                        }
                    }.build()

                Image(
                    painter = rememberAsyncImagePainter(
                        ImageRequest.Builder(context).data(data = R.drawable.soraka).apply(block = {
                            size(Size.ORIGINAL)
                        }).build(), imageLoader = imageLoader
                    ),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = modifier.size(300.dp)
                )

                Text(
                    text = "Champions in League",
                    fontSize = 27.sp,
                    lineHeight = 40.sp,
                    textAlign = TextAlign.Center,
                    fontFamily = AppFont.Montserrat,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.tertiary,
                    modifier = modifier.padding(12.dp)
                )

                Text(
                    text = "Greetings, summoners! I'm Soraka, and I'm here to guide you through the intricate world " +
                            "of League of Legends. Whether you're planning your next build or seeking insights into " +
                            "diverse array of champions, this is the hub where your League of Legends journey takes " +
                            "a decisive turn",
                    fontSize = 12.sp,
                    textAlign = TextAlign.Justify,
                    fontFamily = AppFont.Montserrat,
                    color = MaterialTheme.colorScheme.tertiary,
                    modifier = modifier.padding(12.dp)
                )

                Divider(
                    color = MaterialTheme.colorScheme.secondary,
                    thickness = 1.dp,
                    modifier = Modifier
                        .padding(vertical = 12.dp)
                )

                if (top3champions.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center,
                    ) {
                        CircularProgressIndicator(
                            color = MaterialTheme.colorScheme.tertiary,
                            strokeWidth = 1.dp
                        )
                    }
                } else {
                    Column(
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = modifier
                            .padding(8.dp, 8.dp, 8.dp, 0.dp)
                    ) {
                        Text(
                            text = "Top 3 Champions: ",
                            fontSize = 17.sp,
                            lineHeight = 20.sp,
                            textAlign = TextAlign.Center,
                            fontFamily = AppFont.Montserrat,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.tertiary,
                            modifier = modifier.padding(12.dp)
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        var id = 0
                        for (champion in top3champions) {
                            Champion(champion, id)
                            Spacer(modifier = Modifier.height(16.dp))
                            id++
                        }
                    }
                }
            }
        }
    }
}
