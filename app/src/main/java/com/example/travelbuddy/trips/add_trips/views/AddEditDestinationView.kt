package com.example.travelbuddy.trips.add_trips.views

import android.annotation.SuppressLint
import android.util.Range
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
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
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Place
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.util.toRange
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.travelbuddy.CustomColors
import com.example.travelbuddy.data.model.DestinationModel
import com.example.travelbuddy.trips.add_trips.AddTripsViewModel
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.maxkeppeler.sheets.calendar.models.CalendarStyle
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneId
import java.util.Date

@Composable
fun GenerateDestinationView(
    destination: DestinationModel.Destination,
    readMode: Boolean,
    onDeleteClicked: () -> Unit,
) {
    val viewModel = hiltViewModel<AddTripsViewModel>()
    val formatter = SimpleDateFormat("MMM dd")
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .border(
                width = 2.dp,
                color = CustomColors.Indigo,
                shape = RoundedCornerShape(16.dp)
            )
            .height(70.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Outlined.Place,
                contentDescription = null,
                tint = CustomColors.Indigo,
                modifier = Modifier.size(30.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column(
                modifier = Modifier.weight(0.6f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = destination.name,
                    fontWeight = FontWeight.Bold,
                    color = CustomColors.Indigo,
                    fontSize = 20.sp
                )
                Text(
                    text = "${formatter.format(destination.startDate)} - ${formatter.format(destination.endDate)}",
                    style = TextStyle(
                        color = CustomColors.Indigo,
                        fontSize = 16.sp
                    ),
                )
            }
            IconButton(
                onClick = onDeleteClicked,
                modifier = Modifier.align(Alignment.CenterVertically)
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete",
                    tint = CustomColors.LightRed
                )
            }
            if (readMode) {
                // Itinerary View
                IconButton(
                    onClick = { viewModel.navigateToItinerary(destination.id) },
                    modifier = Modifier.align(Alignment.CenterVertically)
                ) {
                    Icon(
                        imageVector = Icons.Default.ChevronRight,
                        contentDescription = "Itinerary View",
                        tint = Color.Black
                    )
                }
            }
        }
    }
}


@SuppressLint("NewApi")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditDestinationView(
    innerPadding: PaddingValues = PaddingValues(10.dp),
    readMode: Boolean = false
) {
    val viewModel = hiltViewModel<AddTripsViewModel>()
    val state by viewModel.state.collectAsState()

    var destBarText by remember {
        mutableStateOf("")
    }
    var destBarActive by remember {
        mutableStateOf(false)
    }
    LazyColumn(
        modifier = Modifier.padding(innerPadding),
        userScrollEnabled = true
    ) {
        items(state.destinationList) { destination ->
            GenerateDestinationView(destination = destination, readMode = readMode) {
                viewModel.deleteDestination(destination, readMode=readMode)
            }
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
                .padding(innerPadding),
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
                    .padding(innerPadding),
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
                    state.destinationList.forEach {
                        Row(modifier = Modifier.padding(all = 14.dp)) {
                            Icon(
                                modifier = Modifier.padding(end = 10.dp),
                                imageVector = Icons.Default.Place,
                                contentDescription = "Location"
                            )
                            Text(text = it.name)
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
                    onClick = { calendarState.show() },
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
                        Modifier
                            .weight(1f)
                            .padding(end = 8.dp)
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
                        Modifier
                            .weight(1f)
                            .padding(start = 8.dp),
                    ) {
                        Button(
                            onClick = {
                                destBarActive = false
                                if (destBarText.isNotBlank()) {
                                    viewModel.addDestination(
                                        name = destBarText,
                                        startDate = Date.from(
                                            selectedRange.value.lower.atStartOfDay(
                                                ZoneId.systemDefault()
                                            ).toInstant()
                                        ),
                                        endDate = Date.from(
                                            selectedRange.value.upper.atStartOfDay(
                                                ZoneId.systemDefault()
                                            ).toInstant()
                                        ),
                                        readMode = readMode
                                    )
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
