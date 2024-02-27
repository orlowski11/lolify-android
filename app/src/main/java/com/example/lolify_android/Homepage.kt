package com.example.lolify_android

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
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.example.lolify_android.champion_activity.ChampionListActivity
import com.example.lolify_android.ui.theme.AppFont

@Composable
fun Homepage(modifier: Modifier = Modifier) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End
    ){
        val context = LocalContext.current

        IconButton(
            onClick = {
                context.startActivity(Intent(context, AuthActivity::class.java))
            },
            modifier = modifier
                .padding(10.dp)
                .border(
                    1.dp,
                    MaterialTheme.colorScheme.tertiary,
                    shape = CircleShape
                )

        ){
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.tertiary
            )
        }
    }

    Column (
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.padding(8.dp)
    ) {
        val context = LocalContext.current
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
            fontSize = 24.sp,
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

        Spacer(modifier = modifier.weight(1f))

        Button(
            onClick = {
                context.startActivity(Intent(context, ChampionListActivity::class.java))
            },
            colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.tertiary)
        ){
            Text(
                "Go to champions",
                fontFamily = AppFont.Montserrat,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}
