package com.example.travelbuddy.itinerary.views

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.travelbuddy.itinerary.ItineraryViewModel
import com.example.travelbuddy.languageTranslation.CustomColors
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.date_time.DateTimeDialog
import com.maxkeppeler.sheets.date_time.models.DateTimeSelection
import com.pushpal.jetlime.ItemsList
import com.pushpal.jetlime.JetLimeColumn
import com.pushpal.jetlime.JetLimeEvent
import com.pushpal.jetlime.JetLimeEventDefaults
import java.text.SimpleDateFormat
import java.time.LocalDateTime


@Composable
fun NavigationRow() {
    val viewModel = hiltViewModel<ItineraryViewModel>()

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
                    viewModel.navigateBack()
                }
        ) {
            Text("Cancel")
        }
        Box(
            modifier = Modifier
                .padding(horizontal = 15.dp, vertical = 10.dp)
                .clickable {
                    viewModel.submitItinerary()
                }
        ) {
            Text("Done")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "NewApi")
fun ItineraryView() {
    val viewModel = hiltViewModel<ItineraryViewModel>()
    val state by viewModel.state.collectAsState()

    val sheetState = rememberModalBottomSheetState()
    var isSheetOpen by rememberSaveable {
        mutableStateOf(false)
    }
    var selectedDateTime by remember { mutableStateOf(LocalDateTime.now()) }
    val formatter = SimpleDateFormat("EEE MMM dd HH:mm")

    Scaffold(
        topBar = {
            NavigationRow()
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
            Surface(modifier = Modifier.padding(top = innerPadding.calculateTopPadding())) {
                if (state.itineraryList.isNotEmpty()) {
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
                                modifier = Modifier.fillMaxWidth().background(CustomColors.Indigo, shape = RoundedCornerShape(8.dp)),
                            ) {
                                Column(
                                    modifier = Modifier
                                        .background(CustomColors.Indigo)
                                        .padding(16.dp)
                                        .fillMaxWidth()
                                ) {
                                    Text(
                                        text = item.name,
                                        style = TextStyle(
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 20.sp,
                                            color = Color.White
                                        )
                                    )
                                    Spacer(modifier = Modifier.height(8.dp).background(CustomColors.Indigo))
                                    Text(
                                        text = "${formatter.format(item.time)}",
                                        style = TextStyle(
                                            fontWeight = FontWeight.Normal,
                                            fontSize = 14.sp,
                                            color = Color.White
                                        )
                                    )
                                }
                            }
                        }
                    }
                } else {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Button(
                            onClick = { viewModel.generateItinerary(state.destinationId.toString()) },
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text("Generate Itinerary")
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

            Column(
                modifier = Modifier
                    .fillMaxWidth(),
            ) {

                TextField(
                    value = state.name,
                    onValueChange = {viewModel.setItineraryName(it)},
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    shape = RoundedCornerShape(15.dp),
                )

                // Date Time Component
                // From: https://github.com/maxkeppeler/sheets-compose-dialogs/blob/main/app/src/main/java/com/mk/sheets/compose/samples/DateTimeSample1.kt
                val datetimeState = rememberUseCaseState(visible = false)
                DateTimeDialog(
                    state = datetimeState,
                    selection = DateTimeSelection.DateTime(
                        selectedDate = selectedDateTime.toLocalDate(),
                        selectedTime = selectedDateTime.toLocalTime(),
                    ) { newDateTime ->
                        selectedDateTime = newDateTime
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
                            viewModel.addItinerary(
                                name = state.name,
                                time = selectedDateTime
                            )
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
