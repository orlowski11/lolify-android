package com.example.lolify_android.profile_activity

import android.content.Context
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
import com.example.lolify_android.RetrofitInstance
import com.example.lolify_android.champion_activity.ChampionList
import com.example.lolify_android.data.ApiInterface
import com.example.lolify_android.data.ChampionsRepositoryImpl
import com.example.lolify_android.data.SessionManager
import com.example.lolify_android.presentation.championListViewModel
import com.example.lolify_android.ui.theme.LolifyandroidTheme
import kotlinx.coroutines.flow.collectLatest
import retrofit2.Call
import retrofit2.Callback
import android.util.Log
import androidx.compose.runtime.Composable
import com.example.lolify_android.data.ProfileRepositoryImpl
import com.example.lolify_android.presentation.profileViewModel
import retrofit2.Response

class ProfileActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LolifyandroidTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    val username = intent.getStringExtra("user_name")!!
                    val context = LocalContext.current
                    var apiClient: ApiInterface = RetrofitInstance.api
                    var sessionManager: SessionManager = SessionManager(context)
                    val token = sessionManager.fetchAuthToken()!!

                    val viewModel by viewModels<profileViewModel>(factoryProducer = {
                        object : ViewModelProvider.Factory {
                            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                                return profileViewModel(
                                    ProfileRepositoryImpl(
                                        apiClient,
                                        username,
                                        token
                                    )
                                ) as T
                            }
                        }
                    })

                    val profile = viewModel.profile.collectAsState().value

                    UserProfile(profile)
                }
            }
        }
    }
}
