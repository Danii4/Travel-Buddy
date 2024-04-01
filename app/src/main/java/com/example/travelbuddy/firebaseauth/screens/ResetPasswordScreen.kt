package com.example.travelbuddy.firebaseauth.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
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
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.travelbuddy.R
import com.example.travelbuddy.Screen
import com.example.travelbuddy.components.OutlinedTextInput
import com.example.travelbuddy.components.PrimaryButton
import com.example.travelbuddy.components.StandardText
import com.example.travelbuddy.firebaseauth.viewmodels.ResetPasswordViewModel
import kotlinx.coroutines.launch

@Composable
fun ResetPasswordScreen(
    navController: NavController,
    viewModel: ResetPasswordViewModel = hiltViewModel()
) {

    var email by remember { mutableStateOf("") }
    var state = viewModel.resetPasswordState.collectAsState(initial = null)
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    Surface(
        color = Color.White,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(28.dp)
    ) {
        LaunchedEffect(state.value?.success) {
            scope.launch {
                state.value?.success?.let {
                    val success = state.value?.success
                    Toast.makeText(context, "Sent password reset email $success", Toast.LENGTH_SHORT).show()
                    navController.navigate(Screen.Login.route)
                }
            }
        }
        // Show errors when they occur:
        LaunchedEffect(state.value?.error) {
            scope.launch {
                state.value?.error?.let {
                    val error = state.value?.error
                    Toast.makeText(context, "Error sending password reset email: $error", Toast.LENGTH_SHORT).show()
                }
            }
        }

        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                StandardText(value = "Looks like you forgot something")
                StandardText(value = "TravelBuddy - Forgot Password")
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
                    value = email,
                    label = { Text("Email") },
                    icon = Icons.Outlined.Email,
                    onInputChange = { email = it}
                )
                Spacer(modifier = Modifier.height(25.dp))
                PrimaryButton(
                    text = "Send password reset email", textSize=17.sp,
                    onClick = { viewModel.sendPasswordResetEmail(email) }
                )
            }
        }
    }
}