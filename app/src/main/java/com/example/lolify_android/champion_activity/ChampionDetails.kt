package com.example.lolify_android.champion_activity

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
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
import com.example.lolify_android.data.model.Skill
import com.example.lolify_android.data.model.Skin
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
import java.util.Locale
import kotlin.properties.Delegates


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
    ) { innerPadding ->
        Surface(
            color = MaterialTheme.colorScheme.primary
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

                val imageState = rememberAsyncImagePainter(
                    model = ImageRequest.Builder(LocalContext.current).data(champion.image_link)
                        .size(Size.ORIGINAL)
                        .build()
                ).state

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(8.dp, 8.dp, 8.dp, innerPadding.calculateBottomPadding()),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    if(imageState is AsyncImagePainter.State.Error){
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(220.dp)
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                    if(imageState is AsyncImagePainter.State.Success){
                        Image(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(220.dp),
                            painter = imageState.painter,
                            contentDescription = champion.name,
                            contentScale = ContentScale.Crop
                        )
                    }

                    Text(
                        text = champion.name,
                        modifier = Modifier
                            .padding(top = 8.dp),
                        fontSize = 25.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.tertiary
                    )

                    Text(
                        text = "'"+champion.title+"'",
                        modifier = Modifier
                            .padding(0.dp,0.dp,0.dp,8.dp),
                        fontSize = 17.sp,
                        fontWeight = FontWeight.ExtraLight,
                        fontStyle = FontStyle.Italic,
                        color = MaterialTheme.colorScheme.tertiary
                    )

                    Row(
                        horizontalArrangement = Arrangement.Start,
                        modifier = Modifier
                            .padding(bottom = 8.dp, start = 8.dp)
                    ) {
                        Text(
                            text = "Roles: ",
                            modifier = Modifier
                                .padding(horizontal = 2.dp),
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.tertiary
                        )
                        LazyRow(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Start,
                        ) {
                            items(champion.roles.size) { id ->
                                Text(
                                    text = champion.roles[id].name,
                                    modifier = Modifier
                                        .padding(0.dp,0.dp,6.dp,0.dp),
                                    fontSize = 15.sp,
                                    fontFamily = AppFont.Montserrat,
                                    fontWeight = FontWeight.Light,
                                    color = MaterialTheme.colorScheme.tertiary
                                )
                            }
                        }
                    }

                    Row(
                        horizontalArrangement = Arrangement.Start,
                        modifier = Modifier
                            .padding(bottom = 8.dp, start = 8.dp)
                            .fillMaxWidth()
                    ){
                        Text(
                            text = "Likes: ",
                            modifier = Modifier
                                .padding(horizontal = 2.dp),
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.tertiary
                        )

                        Text(
                            text = champion.likes_count.toString(),
                            modifier = Modifier,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Light,
                            color = MaterialTheme.colorScheme.tertiary
                        )
                    }

                    Row(
                        horizontalArrangement = Arrangement.Start,
                        modifier = Modifier
                            .padding(bottom = 8.dp, start = 8.dp)
                            .fillMaxWidth()
                    ) {
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
                            colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.tertiary)
                        ) {
                            if (likesIt) {
                                Text(
                                    "Dislike",
                                    fontFamily = AppFont.Montserrat,
                                    modifier = Modifier
                                        .padding(horizontal = 4.dp),
                                    color = MaterialTheme.colorScheme.primary
                                )
                                Icon(
                                    imageVector = Icons.Default.Favorite,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            } else {
                                Text(
                                    "Like",
                                    fontFamily = AppFont.Montserrat,
                                    modifier = Modifier
                                        .padding(horizontal = 4.dp),
                                    color = MaterialTheme.colorScheme.primary
                                )
                                Icon(
                                    imageVector = Icons.Default.FavoriteBorder,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                    }

                    Text(
                        text = champion.description,
                        modifier = Modifier
                            .padding(8.dp),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.tertiary,
                        textAlign = TextAlign.Justify
                    )

                    Divider(
                        color = MaterialTheme.colorScheme.secondary,
                        thickness = 1.dp,
                        modifier = Modifier
                            .padding(vertical = 12.dp)
                    )

                    Text(
                        text = "Skills:",
                        modifier = Modifier
                            .padding(top = 4.dp, bottom = 12.dp),
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.tertiary,
                    )

                    for(skill in champion.skills){
                        Skill(skill)
                        Spacer(modifier = Modifier.height(4.dp))
                    }

                    Text(
                        text = "Skins:",
                        modifier = Modifier
                            .padding(top = 4.dp, bottom = 12.dp),
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.tertiary,
                    )

                    LazyRow(
                        modifier = Modifier
                            .height(190.dp)
                            .padding(bottom = 10.dp)
                    ){
                        items(champion.skins.size) { id ->
                            Skin(champion.skins[id])
                        }
                    }

                }
            }
        }
    }
}

@Composable
fun Skill(skill: Skill) {

    val imageState = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current).data(skill.image_link)
            .size(Size.ORIGINAL)
            .build()
    ).state

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp)
        ) {
            if (imageState is AsyncImagePainter.State.Error) {
                Box(
                    modifier = Modifier
                        .width(55.dp)
                        .height(55.dp)
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
                        .width(55.dp)
                        .height(55.dp),
                    painter = imageState.painter,
                    contentDescription = skill.letter,
                    contentScale = ContentScale.Crop
                )
            }

            Text(
                text = skill.letter.uppercase(),
                modifier = Modifier
                    .padding(start = 8.dp, top = 24.dp),
                fontSize = 21.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.tertiary
            )
        }

        Text(
            text = skill.name,
            modifier = Modifier
                .padding(8.dp),
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.tertiary,
            textAlign = TextAlign.Justify
        )

        Divider(
            color = MaterialTheme.colorScheme.secondary,
            thickness = 1.dp,
            modifier = Modifier
                .padding(vertical = 8.dp)
        )
    }
}

@Composable
fun Skin(skin: Skin) {

    val imageState = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current).data(skin.image_link)
            .size(Size.ORIGINAL)
            .build()
    ).state

    if (imageState is AsyncImagePainter.State.Error) {
        Box(
            modifier = Modifier
                .size(240.dp)
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
                .padding(start = 8.dp, end = 8.dp),
            painter = imageState.painter,
            contentDescription = skin.id.toString(),
            contentScale = ContentScale.FillHeight
        )
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
        Toast.makeText(context, "Please log in to like a champion", Toast.LENGTH_SHORT).show()
    }
}