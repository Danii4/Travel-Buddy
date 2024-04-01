package com.example.travelbuddy.trips.add_trips.views

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.travelbuddy.CustomColors


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DestinationView() {
    Scaffold(
        topBar = {
            Box(modifier = Modifier.padding(2.dp)){
                Text(
                    modifier = Modifier.padding(10.dp),
                    text = "Destinations",
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Medium,
                    color = CustomColors.Indigo
                )
            }
        },
        content = { innerPadding ->
            AddEditDestinationView(innerPadding, readMode = true)
        }
    )
}
