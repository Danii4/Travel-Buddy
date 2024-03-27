package com.example.travelbuddy.trips.add_trips.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Route
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun AddTripNameView() {
    var tripTitle = ""
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(modifier = Modifier.padding(vertical = 90.dp)){
            Icon(
                imageVector = Icons.Default.Route,
                contentDescription = "Info Icon",
                modifier = Modifier
                    .size(100.dp)
            )
        }
        Text(
            text = "Enter Trip Name",
            modifier = Modifier.padding(10.dp)
        )
        ElevatedCard(
            modifier = Modifier.fillMaxWidth(0.75f)
        ) {
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = tripTitle,
                onValueChange = { tripTitle = it },
            )
        }
    }
}
