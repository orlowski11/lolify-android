package com.example.lolify_android.champion_activity

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.example.lolify_android.data.model.Champion

@Composable
fun ChampionNavigation(championList: List<Champion>){
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "champion_list"){
        composable("champion_list"){
            ChampionList(navController = navController, championList = championList)
        }
        composable("champion_details/{champion_id}"){ backStackEntry ->
            val champion_id = backStackEntry.arguments!!.getString("champion_id")!!.toInt()
            ChampionDetails(navController = navController, championList = championList ,champion_id = champion_id)
        }
    }
}
@Composable
fun ChampionList(navController: NavController, championList: List<Champion>, modifier: Modifier = Modifier) {

    if(championList.isEmpty()){
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            CircularProgressIndicator()
        }
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = PaddingValues(16.dp)
        ){
            items(championList.size){id ->
                Champion(navController, championList[id], id)
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun Champion(navController: NavController, champion: Champion, champion_id: Int){
    val context = LocalContext.current
    val imageState = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current).data(champion.image_link)
            .size(Size.ORIGINAL)
            .build()
    ).state

    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primaryContainer)
            .clickable {
                navController.navigate("champion_details/${champion_id}")
            }
    ){
        if(imageState is AsyncImagePainter.State.Error){
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            ) {
                CircularProgressIndicator()
            }
        }
        if(imageState is AsyncImagePainter.State.Success){
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                painter = imageState.painter,
                contentDescription = champion.name,
                contentScale = ContentScale.Crop
            )
        }

        Spacer(
            modifier = Modifier.height(6.dp)
        )

        Text(
            text = champion.name,
            modifier = Modifier
                .padding(horizontal = 16.dp),
            fontSize = 17.sp,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(
            modifier = Modifier.height(6.dp)
        )

        Text(
            text = champion.title,
            modifier = Modifier
                .padding(horizontal = 16.dp),
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium
        )
    }
}
