package com.example.lolify_android
import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.core.content.ContextCompat.startActivity
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.lolify_android.data.SessionManager
import org.junit.Assert.*
import com.example.lolify_android.navigation.Navigation
import org.mockito.Mockito.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class NavigationBarTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun testHomeButtonClick(){
        val context = composeTestRule.activity
        val navigation = mock(Navigation::class.java)

        composeTestRule.onNodeWithText("Home").performClick()
        verify(navigation).startMainActivity(context)
    }

    @Test
    fun testChampionsButtonClick(){
        val context = composeTestRule.activity
        val navigation = mock(Navigation::class.java)

        composeTestRule.onNodeWithText("Champions").performClick()
        verify(navigation).startChampionActivity(context)
    }

    @Test
    fun testUsersButtonClick(){
        val context = composeTestRule.activity
        val navigation = mock(Navigation::class.java)

        composeTestRule.onNodeWithText("Users").performClick()
        verify(navigation).startSearchProfileActivity(context)
    }

    @Test
    fun testProfileButtonClick(){
        val context = composeTestRule.activity
        val sessionManager = SessionManager(context)
        val navigation = mock(Navigation::class.java)

        composeTestRule.onNodeWithText("Profile").performClick()
        verify(navigation).startProfileActivity(context, sessionManager)
    }

}