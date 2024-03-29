package com.example.lolify_android.champion_activity

import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onParent
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import com.example.lolify_android.MainActivity
import com.example.lolify_android.RetrofitInstance
import com.example.lolify_android.data.ApiInterface
import com.example.lolify_android.data.model.Champion
import com.example.lolify_android.navigation.Navigation
import com.example.lolify_android.ui.theme.LolifyandroidTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.runBlocking
import okhttp3.internal.wait
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ChampionListTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun testChampionDetailsClick() {
        val champion = Champion(
            1,
            "test",
            false,
            "test",
            "test",
            0,
            "test",
            emptyList(),
            emptyList(),
            emptyList(),
            "test",
            "test",
            emptyList()
        )
        composeTestRule.setContent {
            com.example.lolify_android.champion_activity.Champion(
                champion = champion,
                champion_id = 1
            )
        }
        val context = composeTestRule.activity
        val navigation = mock(Navigation::class.java)
        val apiClient = RetrofitInstance.api

        composeTestRule.onNodeWithText("test").onParent().performClick()
        Mockito.verify(navigation).startChampionDetailsActivity(
            context,
            null,
            apiClient,
            champion,
            1
        )
    }
}