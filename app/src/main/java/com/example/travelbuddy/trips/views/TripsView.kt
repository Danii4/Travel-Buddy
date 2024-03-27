package com.example.travelbuddy.trips.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Flight
import androidx.compose.material.icons.outlined.AttachMoney
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.travelbuddy.trips.add_trips.AddTripsViewModel
import com.example.travelbuddy.expenses.add_edit_expense.AddEditExpenseViewModel
import com.example.travelbuddy.trips.add_trips.views.AddTripsPagerView

@Composable
fun TripCard(
    trip: String,
    navController: NavController
) {
    return Card(modifier = Modifier
        .padding(4.dp),) {
        val expenseViewModel = AddEditExpenseViewModel()
        val addTripViewModel = hiltViewModel<AddTripsViewModel>()


        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(
                text = trip,
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
            ) {
                AssistChip(
                    onClick = { addTripViewModel.navigateToCreateTripAdd(navController) },
                    colors = AssistChipDefaults.assistChipColors(
                        leadingIconContentColor = MaterialTheme.colorScheme.onSurfaceVariant
                    ),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Flight,
                            contentDescription = null
                        )
                    },
                    label = {
                        Text(text = "Destinations")
                    }
                )
                Spacer(modifier = Modifier.width(100.dp))
                AssistChip(
                    onClick = { expenseViewModel.navigatetoExpenses(navController) },
                    colors = AssistChipDefaults.assistChipColors(
                        leadingIconContentColor = MaterialTheme.colorScheme.onSurfaceVariant
                    ),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Outlined.AttachMoney,
                            contentDescription = null
                        )
                    },
                    label = {
                        Text(text = "Budget")
                    }
                )
            }
        }
    }
}

@Composable
fun TripsView(
    navController: NavController
) {

    val trip1 = "Grad Trip"
    val trip2 = "Month in Europe"

    val tripList = remember {
        mutableStateListOf(trip1, trip2)
    }

    Scaffold { paddingValues ->
        LazyColumn(
            modifier = Modifier.padding(paddingValues),
            userScrollEnabled = true
        ) {
            items(tripList) { trip: String ->
                TripCard(trip = trip, navController)
                Spacer(modifier = Modifier.height(5.dp))
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
                    containerColor = Color(0xff5cb85c)
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