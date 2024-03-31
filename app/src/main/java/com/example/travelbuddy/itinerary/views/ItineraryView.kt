package com.example.travelbuddy.itinerary.views

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
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
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.travelbuddy.data.model.ItineraryModel
import com.example.travelbuddy.itinerary.ItineraryViewModel
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.date_time.DateTimeDialog
import com.maxkeppeler.sheets.date_time.models.DateTimeSelection
import com.pushpal.jetlime.ItemsList
import com.pushpal.jetlime.JetLimeColumn
import com.pushpal.jetlime.JetLimeEvent
import com.pushpal.jetlime.JetLimeEventDefaults
import java.time.LocalDate
import java.time.LocalDateTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "NewApi")
fun ItineraryView() {
    val sheetState = rememberModalBottomSheetState()
    var isSheetOpen by rememberSaveable {
        mutableStateOf(false)
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Destination's Itinerary",
                        style = MaterialTheme.typography.headlineSmall,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                },
            )
        },
        bottomBar = {
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
                        .padding(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xff5cb85c)
                    )
                ) {
                    Text(text = "+")
                }
            }
        },
        content = { innerPadding ->
            val viewModel = hiltViewModel<ItineraryViewModel>()
            val state by viewModel.state.collectAsState()
            val item1 = ItineraryModel.Itinerary(
                title = "item1",
                time = LocalDate.now(),
            )
            val item2 = ItineraryModel.Itinerary(
                title = "item2",
                time = LocalDate.now(),
            )
            val item3 = ItineraryModel.Itinerary(
                title = "item3",
                time = LocalDate.now(),
            )
            val itemList = mutableListOf(item1, item2, item3)
            state.itineraryList = itemList
            Surface(modifier = Modifier.padding(top = innerPadding.calculateTopPadding())) {
                JetLimeColumn(
                    modifier = Modifier.padding(16.dp),
                    itemsList = ItemsList(state.itineraryList)
                ) { _, item, position ->
                    JetLimeEvent(
                        style = JetLimeEventDefaults.eventStyle(
                            position = position
                        ),
                    ) {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .padding(16.dp)
                                    .fillMaxWidth()
                            ) {
                                Text(
                                    text = item.title,
                                    style = TextStyle(
                                        fontWeight = FontWeight.Bold,
                                    )
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "Time: ${item.time}",
                                    style = TextStyle(
                                        fontWeight = FontWeight.Normal,
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }
    )
    if (isSheetOpen) {
        ModalBottomSheet(
            sheetState = sheetState,
            onDismissRequest = {
                isSheetOpen = false
            }
        ) {
            val itineraryName: String = ""

            Column(
                modifier = Modifier
                    .fillMaxWidth(),
            ) {

                TextField(
                    value = itineraryName,
                    onValueChange = {},
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth().padding(10.dp),
                    shape = RoundedCornerShape(15.dp),
                )

                // Date Time Component
                // From: https://github.com/maxkeppeler/sheets-compose-dialogs/blob/main/app/src/main/java/com/mk/sheets/compose/samples/DateTimeSample1.kt

                val selectedDateTime = remember {
                    mutableStateOf<LocalDateTime?>(LocalDateTime.now().plusDays(1))
                }

                val datetimeState = rememberUseCaseState(visible = false)
                DateTimeDialog(
                    state = datetimeState,
                    selection = DateTimeSelection.DateTime(
                        selectedDate = selectedDateTime.value!!.toLocalDate(),
                        selectedTime = selectedDateTime.value!!.toLocalTime(),
                    ) { newDateTime ->
                        selectedDateTime.value = newDateTime
                    },
                )
                Button(
                    onClick = { datetimeState.show() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(7.dp),
                    shape = RoundedCornerShape(10.dp),
                ) {
                    Text(text = "Choose Time")
                }
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
                            if ( itineraryName != "") {
//                                val newDestination = DestinationModel.Destination(
//                                    name = destBarText,
//                                    startDate = Date.from(selectedRange.value.lower.atStartOfDay(
//                                        ZoneId.systemDefault()).toInstant()),
//                                    endDate = Date.from(selectedRange.value.upper.atStartOfDay(
//                                        ZoneId.systemDefault()).toInstant()),
//                                )
//                                viewModel.addDestination(newDestination)
//                                destBarText = ""
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
