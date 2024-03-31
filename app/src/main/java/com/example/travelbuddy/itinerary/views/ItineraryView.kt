package com.example.travelbuddy.itinerary.views

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.travelbuddy.data.model.ItineraryModel
import com.example.travelbuddy.itinerary.ItineraryViewModel
import com.pushpal.jetlime.ItemsList
import com.pushpal.jetlime.JetLimeColumn
import com.pushpal.jetlime.JetLimeEvent
import com.pushpal.jetlime.JetLimeEventDefaults
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "NewApi")
fun ItineraryView() {
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
}
