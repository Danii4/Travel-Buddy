package com.example.travelbuddy.auth.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.travelbuddy.R
import com.example.travelbuddy.Screen

@Composable
fun BottomLoginComponent(navController: NavController) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(50.dp),
                colors = ButtonDefaults.buttonColors(Color.Black),
                onClick = { navController.navigate(Screen.Home.route) },
            ) {
                Text(
                    text = "Login",
                    color = Color.White,
                    modifier = Modifier.padding(5.dp),
                    fontSize = 20.sp
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    thickness = 1.dp,
                    color = Color.Gray
                )
                Text(
                    text = "Or",
                    modifier = Modifier.padding(10.dp),
                    fontSize = 20.sp
                )
                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    thickness = 1.dp,
                    color = Color.Gray
                )
            }

            Button(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(50.dp),
                colors = ButtonDefaults.buttonColors(Color.Transparent),
                border = BorderStroke(width = 1.dp, color = Color.Black),
                onClick = { navController.navigate(Screen.Home.route) },
            ) {
                Icon(
                    modifier = Modifier.padding(horizontal = 5.dp),
                    imageVector = ImageVector.vectorResource(R.drawable.google_24),
                    contentDescription = "Google Icon",
                    tint = Color.Black,
                )
                Text(
                    text = "Login with Google",
                    color = Color.Black,
                    modifier = Modifier.padding(5.dp),
                    fontSize = 20.sp
                )
            }
        }
    }
    Spacer(modifier = Modifier.height(15.dp))
}
