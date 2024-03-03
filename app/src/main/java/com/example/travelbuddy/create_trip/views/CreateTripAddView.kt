package com.example.travelbuddy.create_trip.views

import android.annotation.SuppressLint
import android.util.Range
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import androidx.compose.material3.SearchBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.core.util.toRange
import com.example.travelbuddy.data.model.DestinationModel
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.maxkeppeler.sheets.calendar.models.CalendarStyle
import java.time.LocalDate

@Composable
fun GenerateDestinationView(destination: DestinationModel.Destination) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .height(70.dp),
        shape = RoundedCornerShape(15)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Filled.Place,
                contentDescription = null,
                modifier = Modifier.size(24.dp),
            )
            Box(
                modifier = Modifier
                    .weight(0.6f)
                    .fillMaxHeight(),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    text = destination.Name,
                    modifier = Modifier.padding(4.dp)
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Box(
                modifier = Modifier
                    .weight(0.4f)
                    .fillMaxHeight(),
                contentAlignment = Alignment.Center
            ) {
                Column {
                    Text(
                        text = "From: ${destination.startDate}",
                        style = TextStyle(
                            color = MaterialTheme.colorScheme.primary,
                        ),
                        modifier = Modifier.padding(4.dp),
                        color = Color.Gray
                    )
                    Text(
                        text = "To: ${destination.endDate}",
                        style = TextStyle(
                            color = MaterialTheme.colorScheme.primary,
                        ),
                        modifier = Modifier.padding(4.dp),
                        color = Color.Gray
                    )
                }
            }
        }
    }
}

@SuppressLint("NewApi")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateTripAddView() {
    var destBarText by remember {
        mutableStateOf("")
    }
    var destBarActive by remember {
        mutableStateOf(false)
    }

    val dest1 = DestinationModel.Destination(
        Name = "Sintra",
        startDate = LocalDate.now(),
        endDate = LocalDate.now().plusDays(7)
    )
    val dest2 = DestinationModel.Destination(
        Name = "Lisbon",
        startDate = LocalDate.now(),
        endDate = LocalDate.now().plusDays(7)
    )
    val dest3 = DestinationModel.Destination(
        Name = "Porto",
        startDate = LocalDate.now(),
        endDate = LocalDate.now().plusDays(7)
    )
    val dest4 = DestinationModel.Destination(
        Name = "Madeira",
        startDate = LocalDate.now(),
        endDate = LocalDate.now().plusDays(7)
    )

    val destList = remember {
        mutableStateListOf( dest1, dest2, dest3, dest4)
    }

    Scaffold { paddingValues ->
        LazyColumn(
            modifier = Modifier.padding(paddingValues)
        ) {
            items(destList) { destination ->
                GenerateDestinationView(destination = destination)
            }
        }
        val sheetState = rememberModalBottomSheetState()
        var isSheetOpen by rememberSaveable {
            mutableStateOf(false)
        }

        Box (
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
                Text(text = "+")
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

                    // Destination Bar
                    // From: https://www.composables.com/material3/searchbar/examples
                    SearchBar(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 7.dp),
                        query = destBarText,
                        onQueryChange = {
                            destBarText = it
                        },
                        onSearch = {
                            destBarActive = false
                        },
                        active = destBarActive,
                        onActiveChange = {
                            destBarActive = it
                        },
                        placeholder = {
                            Text(text = "Search for a destination ...")
                        },
                        leadingIcon = {
                            Icon(imageVector = Icons.Default.Search, contentDescription = "Search Icon")
                        },
                        trailingIcon = {
                            if (destBarActive) {
                                Icon(
                                    modifier = Modifier.clickable {
                                        if (destBarText.isNotEmpty()) {
                                            destBarText = ""
                                        } else {
                                            destBarActive = false
                                        }
                                    },
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "Search Icon"
                                )
                            }
                        },
                        shape = RoundedCornerShape(15.dp),
                        windowInsets = WindowInsets(top = (-7).dp)
                    ) {
                        destList.forEach {
                            Row(modifier = Modifier.padding(all = 14.dp)) {
                                Icon(
                                    modifier = Modifier.padding(end = 10.dp),
                                    imageVector = Icons.Default.Place,
                                    contentDescription = "Location"
                                )
                                Text(text = it.Name)
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))

                    // Calendar Component
                    // From: https://github.com/maxkeppeler/sheets-compose-dialogs/blob/main/app/src/main/java/com/mk/sheets/compose/samples/CalendarSample3.kt

                    val selectedRange = remember {
                        val default = LocalDate.now().let { time -> time.plusDays(5)..time.plusDays(8) }
                        mutableStateOf(default.toRange())
                    }

                    val calendarState = rememberUseCaseState(visible = false, true)
                    CalendarDialog(
                        state = calendarState,
                        config = CalendarConfig(
                            yearSelection = true,
                            monthSelection = true,
                            style = CalendarStyle.MONTH,
                        ),
                        selection = CalendarSelection.Period(
                            selectedRange = selectedRange.value
                        ) { startDate, endDate ->
                            selectedRange.value = Range(startDate, endDate)
                        },
                    )

                    Button(
                        onClick = {calendarState.show()},
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(7.dp),
                        shape = RoundedCornerShape(10.dp),
                        ) {
                        Text(text = "Select a date range")
                    }

                    Spacer(modifier = Modifier.height(5.dp))

                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 10.dp)
                    ) {

                        // Cancel Destination Button
                        Box(
                            Modifier.weight(1f).padding(end = 8.dp)
                        ) {
                            Button(
                                onClick = {
                                    isSheetOpen = false
                                },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(10.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color.Red
                                )
                            ) {
                                Text("X")
                            }
                        }

                        // Add Destination Button
                        Box(
                            Modifier.weight(1f).padding(start = 8.dp),
                        ) {
                            Button(
                                onClick = {
                                    destBarActive = false
                                    if (destBarText.isNotBlank()) {
                                        val newDestination = DestinationModel.Destination(
                                            Name = destBarText,
                                            startDate = selectedRange.value.lower,
                                            endDate = selectedRange.value.upper
                                        )
                                        destList.add(newDestination)
                                        destBarText = ""
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

//// Trip Notes
//// From: https://developer.android.com/jetpack/compose/text/user-input
//TextField(
//value = destNote,
//onValueChange = { destNote = it },
//label = { Text("Enter Notes") },
//maxLines = 2,
//textStyle = TextStyle(color = Color.Black,),
//modifier = Modifier.padding(20.dp)
//)