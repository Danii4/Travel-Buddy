package com.example.travelbuddy.trips.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.travelbuddy.trips.add_trips.views.AddEditDestinationView


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationRow(sheetState: SheetState) {
    Row(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .padding(bottom = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Box(
            modifier = Modifier
                .padding(horizontal = 15.dp, vertical = 10.dp)
                .clickable {}
        ) {
            Text("Cancel")
        }
        Box(
            modifier = Modifier
                .padding(horizontal = 15.dp, vertical = 10.dp)
                .clickable {}
        ) {
            Text("Done")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DestinationView(sheetState: SheetState) {
    Scaffold(
        topBar = {
            NavigationRow(sheetState)
        },
        content = { innerPadding ->
            AddEditDestinationView(innerPadding)
        }
    )
}
