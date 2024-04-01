package com.example.travelbuddy.trips.views

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Flight
import androidx.compose.material.icons.outlined.AttachMoney
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.travelbuddy.data.model.TripModel
import com.example.travelbuddy.languageTranslation.CustomColors
import com.example.travelbuddy.trips.TripsViewModel
import com.example.travelbuddy.trips.add_trips.AddTripsViewModel
import com.example.travelbuddy.trips.add_trips.views.AddTripsPagerView
import com.example.travelbuddy.trips.add_trips.views.DestinationView

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TripCard(
    trip: TripModel.Trip,
    navController: NavController,
    onExpenseClick: () -> Unit,
    onDeleteClick: () -> Unit,
) {
    return Card(
        modifier = Modifier
            .padding(10.dp)
    ) {
        val addTripsViewModel = hiltViewModel<AddTripsViewModel>()

        val sheetState = rememberModalBottomSheetState(
            skipPartiallyExpanded = true
        )
        var destSheetOpen by rememberSaveable {
            mutableStateOf(false)
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = CustomColors.Indigo)
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = trip.name,
                style = MaterialTheme.typography.headlineLarge,
                color = Color.White,
                modifier = Modifier.weight(1f),
                overflow = TextOverflow.Ellipsis
            )

            IconButton(
                onClick = onDeleteClick
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete Trip",
                    tint = Color.White
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .background(color = CustomColors.Indigo),
        ) {
            Spacer(modifier = Modifier.width(30.dp))
            AssistChip(
                onClick = {
                    destSheetOpen = true
                    addTripsViewModel.setTripId(trip.id)
                },
                colors = AssistChipDefaults.assistChipColors(
                    leadingIconContentColor = MaterialTheme.colorScheme.onTertiary,
                ),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Flight,
                        contentDescription = null
                    )
                },
                label = {
                    Text(text = "Destinations", color = Color.White)
                }
            )
            Spacer(modifier = Modifier.width(75.dp))
            AssistChip(
                onClick = onExpenseClick,
                colors = AssistChipDefaults.assistChipColors(
                    leadingIconContentColor = MaterialTheme.colorScheme.onTertiary
                ),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.AttachMoney,
                        contentDescription = null
                    )
                },
                label = {
                    Text(text = "Budget", color = Color.White)
                }
            )
        }
        if (destSheetOpen){
            ModalBottomSheet(
                sheetState = sheetState,
                onDismissRequest = {
                    destSheetOpen = false
                },
                content = {
                    DestinationView()
                }
            )
        }
    }

}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TripsView(
    navController: NavController
) {

    val viewModel = hiltViewModel<TripsViewModel>()
    val state by viewModel.state.collectAsState()
    var isLoading by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        isLoading = true
        viewModel.getData()
        isLoading = false
    }
    if (isLoading) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
    Scaffold {
        LazyColumn(
            modifier = Modifier.padding(bottom = 25.dp),
            userScrollEnabled = true
        ) {
            viewModel.getData()
            items(state.tripsList) { trip: TripModel.Trip ->
                TripCard(trip = trip, navController, { viewModel.navigateToExpense(trip.id) },
                    {
                        viewModel.deleteTrip(trip.id)
                        viewModel.navigateToTrips()
                    })
                Spacer(modifier = Modifier.height(3.dp))
            }
        }
        var isSheetOpen by rememberSaveable {
            mutableStateOf(false)
        }
        var showPager by remember { mutableStateOf(false) }
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            Button(
                onClick = {
                    isSheetOpen = true
                    showPager = true
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(92,184,92),
                    contentColor = MaterialTheme.colorScheme.onTertiary
                )
            ) {
                Text(text = "New Trip")
            }
        }
        if (showPager) {
            AddTripsPagerView(navController)
        }
    }
    }
}