@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.travelbuddy.trips.views

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Flight
import androidx.compose.material.icons.outlined.AttachMoney
import androidx.compose.material.icons.outlined.List
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.travelbuddy.create_trip.CreateTripAddViewModel
import com.example.travelbuddy.expenses.ExpensesViewModel
import com.example.travelbuddy.expenses.add_edit_expense.AddEditExpenseViewModel
import java.time.LocalDate

class TripsViewModel : ViewModel() {
    var trips = mutableStateListOf<String>()
}

@Composable
fun TripCard(
    trip: String,
//    title: String,
//    budget: String,
//    itinerary: String
    navController: NavController
) {
    return Card(modifier = Modifier
        .padding(4.dp),) {
        val expenseViewModel = AddEditExpenseViewModel()
        val addTripViewModel = CreateTripAddViewModel()


        Column(
            modifier = Modifier
                .padding(16.dp)
//                .background(MaterialTheme.colorScheme.primary)
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

    var tripTitle by remember {
        mutableStateOf("")
    }
    var tripTitleActive by remember {
        mutableStateOf(false)
    }

    val trip1 = "Grad Trip"
    val trip2 = "Month in Europe"

    val tripList = remember {
        mutableStateListOf(trip1, trip2)
    }

    fun deleteTrip(trip: String) {
        tripList.remove(trip)
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
        val sheetState = rememberModalBottomSheetState()
        var isSheetOpen by rememberSaveable {
            mutableStateOf(false)
        }

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            Button(
                onClick = {
                    isSheetOpen = true
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
        if (isSheetOpen) {
            ModalBottomSheet(
                sheetState = sheetState,
                onDismissRequest = {
                    isSheetOpen = false
                }
            )
            {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(paddingValues),
                ) {
                    Text(
//            text = stringResource(id = R.string.medication_name),
                        text = "Title",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    TextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = tripTitle,
                        onValueChange = { tripTitle = it },
//            placeholder = { Text(text = "e.g. Hexamine") },
                    )
                    Spacer(modifier = Modifier.height(20.dp))


                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 10.dp)
                    ) {

                        Box(
                            Modifier
                                .weight(1f)
                                .padding(start = 8.dp),
                        ) {
                            Button(
                                onClick = {
                                    tripTitleActive = false
                                    if (tripTitle.isNotBlank()) {
                                        tripList.add(tripTitle)
                                        tripTitle = ""
                                        isSheetOpen=false
                                    }
                                },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(10.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(0xff5cb85c)
                                )
                            ) {
                                Text("Confirm")
                            }
                        }
                    }
                }
            }
        }
    }


}