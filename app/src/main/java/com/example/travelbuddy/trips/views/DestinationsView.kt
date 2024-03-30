package com.example.travelbuddy.trips.views

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.travelbuddy.trips.TripsViewModel
import com.example.travelbuddy.trips.add_trips.views.AddEditDestinationView


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavigationRow() {
    val viewModel = hiltViewModel<TripsViewModel>()
    val scope = rememberCoroutineScope()

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
                .clickable {
                    viewModel.navigateToTrips()
                }
        ) {
            Text("Cancel")
        }
        Box(
            modifier = Modifier
                .padding(horizontal = 15.dp, vertical = 10.dp)
                .clickable {
                    viewModel.navigateToTrips()
                }
        ) {
            Text("Done")
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DestinationView() {
    Scaffold(
        topBar = {
            NavigationRow()
        },
        content = { innerPadding ->
            AddEditDestinationView(innerPadding)
        }
    )
}
