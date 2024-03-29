package com.example.lolify_android.profile_activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.lolify_android.BottomNavigationBar
import com.example.lolify_android.RetrofitInstance
import com.example.lolify_android.auth_activity.LolifyTextFieldColors
import com.example.lolify_android.auth_activity.NameField
import com.example.lolify_android.data.ApiInterface
import com.example.lolify_android.data.SessionManager
import com.example.lolify_android.data.model.CurrentUserResponse
import com.example.lolify_android.data.model.LoginRequest
import com.example.lolify_android.ui.theme.AppFont
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SearchProfile() {

    val context = LocalContext.current

    Scaffold(
        bottomBar = { BottomNavigationBar(selectedScreen = "users", context = context) }
    ) {
        Surface(
            color = MaterialTheme.colorScheme.primary
        ) {
            var user by remember { mutableStateOf(CurrentUserResponse("")) }
            val coroutineScope = rememberCoroutineScope()
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 30.dp)
            ) {
                SearchField(
                    value = user.name,
                    onChange = { data -> user = user.copy(name = data) },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(10.dp))
                Button(
                    onClick = {
                        coroutineScope.launch {
                            Search.SearchUser(user.name, context)
                        }
                    },
                    enabled = true,
                    shape = RoundedCornerShape(5.dp),
                    colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.tertiary),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        "Search",
                        fontFamily = AppFont.Montserrat,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}

@Composable
fun SearchField(
    value: String,
    onChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String = "Name",
    placeholder: String = "Enter user name"
) {
    val focusManager = LocalFocusManager.current
    val trailingIcon = @Composable {
        Icon(
            Icons.Default.Person,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.tertiary
        )
    }

    TextField(
        value = value,
        onValueChange = onChange,
        modifier = modifier,
        trailingIcon = trailingIcon,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        keyboardActions = KeyboardActions(
            onNext = { focusManager.moveFocus(FocusDirection.Down) }
        ),
        placeholder = { Text(placeholder) },
        label = { Text(label) },
        singleLine = true,
        visualTransformation = VisualTransformation.None,
        colors = LolifyTextFieldColors()
    )
}