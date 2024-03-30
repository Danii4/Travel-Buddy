package com.example.travelbuddy.firebaseauth.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.travelbuddy.R
import com.example.travelbuddy.Screen
import com.example.travelbuddy.components.PrimaryButton
import com.example.travelbuddy.components.SecondaryButton
import com.example.travelbuddy.components.StandardText

@Composable
fun InitialAuthScreen(
    navController: NavController,
) {
    Surface(
        color = Color.White,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(28.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                StandardText(value = "Howdy, Welcome")
                StandardText(value = "TravelBuddy")
                StandardText(value = "The best travel tool")
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
                PrimaryButton(
                    text = "Login",
                    onClick = { navController.navigate(Screen.Login.route)}
                )
                Spacer(modifier = Modifier.height(25.dp))
                SecondaryButton(
                    text = "Signup",
                    onClick = { navController.navigate(Screen.Signup.route)}
                )
            }
        }
    }
}