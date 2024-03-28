package com.example.lolify_android.auth_activity

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.lolify_android.data.SessionManager
import com.example.lolify_android.profile_activity.ProfileActivity
import org.junit.Before
import org.junit.FixMethodOrder
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
@FixMethodOrder( MethodSorters.NAME_ASCENDING )
class AuthorizationTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<AuthActivity>()

    @Test
    fun stage1_testLoginButtonClick(){
        val context = composeTestRule.activity
        val auth = Mockito.mock(AuthFunctions::class.java)

        composeTestRule.onNodeWithText("Login").performClick()
        Mockito.verify(auth).Login("", "", context)
    }

    @Test
    fun stage1_testRegisterButtonClick(){
        val context = composeTestRule.activity
        val auth = Mockito.mock(AuthFunctions::class.java)

        composeTestRule.onNodeWithText("Register").performClick()
        composeTestRule.onNodeWithText("Register").performClick()
        Mockito.verify(auth).Register("","","","",context)
    }

    @Test
    fun stage2_testLogin(){
        val context = composeTestRule.activity
        val auth = Mockito.mock(AuthFunctions::class.java)
        val sessionManager = SessionManager(context)

        auth.Login("test@gmail.com","test123456", context)
        sessionManager.fetchAuthToken()?.let { assert(it.isNotEmpty()) }
    }

    @Test
    fun stage3_testLogout(){
        val context = composeTestRule.activity
        val auth = Mockito.mock(AuthFunctions::class.java)
        val sessionManager = SessionManager(context)

        auth.Logout(context)
        sessionManager.fetchAuthToken()?.let { assert(it.isEmpty()) }
    }

}