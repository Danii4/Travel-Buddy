package com.example.travelbuddy.firebaseauth.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.travelbuddy.R
import com.example.travelbuddy.Screen
import com.example.travelbuddy.components.BottomAuthComponent
import com.example.travelbuddy.components.OutlinedPasswordInput
import com.example.travelbuddy.components.OutlinedTextInput
import com.example.travelbuddy.components.StandardText
import com.example.travelbuddy.firebaseauth.viewmodels.SignupViewModel
import kotlinx.coroutines.launch

@Composable
fun SignupScreen(
    navController: NavController,
    viewModel: SignupViewModel = hiltViewModel()
) {

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var state = viewModel.signUpState.collectAsState(initial = null)
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    Surface(
        color = Color.White,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(28.dp)
    ) {
        // Show success when they occur:
        LaunchedEffect(state.value?.success) {
            scope.launch {
                state.value?.success?.let {
                    val success = state.value?.success
                    Toast.makeText(context, "$success", Toast.LENGTH_SHORT).show()
                    navController.navigate(Screen.Login.route);
                }
            }
        }
        // Show errors when they occur:
        LaunchedEffect(state.value?.error) {
            scope.launch {
                state.value?.error?.let {
                    val error = state.value?.error
                    Toast.makeText(context, "$error", Toast.LENGTH_SHORT).show()
                }
            }
        }


        Row(modifier = Modifier.fillMaxSize(), horizontalArrangement = Arrangement.Center) {
            if (state.value?.loading == true) {
                CircularProgressIndicator()
            }
        }

        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                StandardText(value = "Howdy, welcome back")
                StandardText(value = "TravelBuddy - Sign up")
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.travel_explore_24),
                    contentDescription = "App Logo",
                    modifier = Modifier
                        .height(30.dp)
                        .width(30.dp)
                )
            }
            Spacer(modifier = Modifier.height(25.dp))
            Column {
                OutlinedTextInput(
                    value = name,
                    label = { Text("Name") },
                    icon = Icons.Outlined.Email,
                    onInputChange = { name = it }
                )
                OutlinedTextInput(
                    value = email,
                    label = { Text("Email") },
                    icon = Icons.Outlined.Email,
                    onInputChange = { email = it}
                )
                Spacer(modifier = Modifier.height(10.dp))
                OutlinedPasswordInput(
                    value = password,
                    label = { Text("Password") },
                    icon = Icons.Outlined.Lock,
                    onInputChange = { password = it}
                )
            }
            BottomAuthComponent(
                primaryButtonText = "Signup",
                primaryOnClick = { scope.launch {
                    viewModel.signupUser(name, email, password)
                }},
                googleButtonText = "Signup with Google",
                googleOnClick = { scope.launch {
                    Toast.makeText(context, "TODO", Toast.LENGTH_SHORT)
                }}
            )
        }
    }
}