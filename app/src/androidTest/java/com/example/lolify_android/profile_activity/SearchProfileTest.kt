package com.example.lolify_android.profile_activity

import android.os.Looper
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.example.lolify_android.ToastMatcher
import com.example.lolify_android.auth_activity.AuthActivity
import com.example.lolify_android.auth_activity.AuthFunctions
import com.example.lolify_android.data.SessionManager
import kotlinx.coroutines.runBlocking
import org.junit.FixMethodOrder
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
@FixMethodOrder( MethodSorters.NAME_ASCENDING )
class SearchProfileTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<SearchProfileActivity>()

    @Test
    fun testSearchButtonClick(){
        val context = composeTestRule.activity
        val search = Mockito.mock(Search::class.java)

        composeTestRule.onNodeWithText("Search").performClick()
        runBlocking {
            Looper.prepare()
            Mockito.verify(search).SearchUser("test", context)
        }
    }
}