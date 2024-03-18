package com.example.travelbuddy.auth

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
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.travelbuddy.R
import com.example.travelbuddy.auth.components.BottomLoginComponent
import com.example.travelbuddy.auth.components.OutlinedTextEntry
import com.example.travelbuddy.auth.components.OutlinedTextPasswordEntry
import com.example.travelbuddy.auth.components.StandardText

@Composable
fun LoginScreen(navController: NavHostController) {
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
                StandardText(value = "Howdy, welcome back")
                StandardText(value = "TravelBuddy")
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.travel_explore_24),
                    contentDescription = "App Logo",
                    modifier = Modifier.height(30.dp).width(30.dp)
                )
            }
            Spacer(modifier = Modifier.height(25.dp))
            Column {
                OutlinedTextEntry(label = { Text("Email") }, icon = Icons.Outlined.Email)
                Spacer(modifier = Modifier.height(10.dp))
                OutlinedTextPasswordEntry(label = { Text("Password") }, icon = Icons.Outlined.Lock)
            }
            BottomLoginComponent(navController)
        }
    }
}