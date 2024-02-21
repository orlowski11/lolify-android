package com.example.lolify_android

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.lolify_android.data.ChampionsRepositoryImpl
import com.example.lolify_android.presentation.championListViewModel
import com.example.lolify_android.ui.theme.LolifyandroidTheme
import kotlinx.coroutines.flow.collectLatest

class ChampionListActivity : ComponentActivity() {

    private val viewModel by viewModels<championListViewModel>(factoryProducer = {
        object: ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return championListViewModel(ChampionsRepositoryImpl(RetrofitInstance.api))
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
                    color = MaterialTheme.colorScheme.background
                ) {

                    val championList = viewModel.champions.collectAsState().value
                    val context = LocalContext.current

                    LaunchedEffect(key1 = viewModel.showErrorToastChannel){
                        viewModel.showErrorToastChannel.collectLatest { show ->
                            if(show){
                                Toast.makeText(
                                    context, "Error", Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }

                    ChampionNavigation(championList)
                }
            }
        }
    }
}