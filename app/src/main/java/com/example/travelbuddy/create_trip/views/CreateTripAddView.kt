package com.example.travelbuddy.create_trip.views

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateTripAddView() {
    var value by remember { mutableStateOf("Type Here") }

    TextField(
        value = value,
        onValueChange = { value = it },
        label = { Text("Enter Notes") },
        maxLines = 2,
        textStyle = TextStyle(color = Color.Black,),
        modifier = Modifier.padding(20.dp)
    )
}