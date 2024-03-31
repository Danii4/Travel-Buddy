package com.example.travelbuddy.trips.add_trips.views

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DestinationView() {
    Scaffold(
        content = { innerPadding ->
            AddEditDestinationView(innerPadding, readMode = true)
        }
    )
}
