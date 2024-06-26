package com.example.lolify_android

import android.content.Intent
import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import coil.size.Size
import com.example.lolify_android.data.ChampionsRepositoryImpl
import com.example.lolify_android.data.Top3ChampionsRepositoryImpl
import com.example.lolify_android.presentation.championListViewModel
import com.example.lolify_android.presentation.top3ChampionsViewModel
import com.example.lolify_android.ui.theme.LolifyandroidTheme

class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<top3ChampionsViewModel>(factoryProducer = {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return top3ChampionsViewModel(Top3ChampionsRepositoryImpl(RetrofitInstance.api))
                        as T
            }
        }
    })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LolifyandroidTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.primary
                ) {

                    val top3champions = viewModel.champions.collectAsState().value

                    Homepage(top3champions)
                }
            }
        }
    }
}

