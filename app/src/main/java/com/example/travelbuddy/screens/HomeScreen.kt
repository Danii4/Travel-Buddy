package com.example.travelbuddy.screens

import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun HomeScreen() {
    return Surface {
        Text(modifier = modifier.padding(20.dp),
             text = "Home Screen",
             fontSize = 18.sp,
             fontWeight = FontWeight.Medium
            )
        Column{
            Text(
                Modifier = Modifier.padding(20.dp),
                text = "Welcome to TravelBuddy",
                fontSize = 25.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

class Verifylogin{
    val response = OkHttpClient().newCall(request).execute()
    val json = response.body.string()
}
