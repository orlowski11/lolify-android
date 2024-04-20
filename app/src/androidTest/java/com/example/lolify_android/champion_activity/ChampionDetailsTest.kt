package com.example.lolify_android.champion_activity

import android.R
import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.lolify_android.ToastMatcher
import com.example.lolify_android.data.model.Champions
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ChampionDetailsTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun testLikeClick() {
        val champion = com.example.lolify_android.data.model.Champion(
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

        val championList = Champions()
        championList.add(champion)

        composeTestRule.setContent {
            com.example.lolify_android.champion_activity.ChampionDetails(
                championList,
                0,
                false,
                composeTestRule.activity
            )
        }

        composeTestRule.onNodeWithText("Like").performClick()
        onView(withText("Please log in to like a champion"))
            .inRoot(ToastMatcher().apply {
                matches(isDisplayed())
            })
    }
}