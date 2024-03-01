package com.example.travelbuddy.create_trip.views

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.maxkeppeker.sheets.core.models.base.rememberSheetState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection.Dates
import androidx.compose.material3.SearchBar
import androidx.compose.runtime.mutableStateListOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateTripAddView() {
    var destBarText by remember {
        mutableStateOf("")
    }
    var destBarActive by remember {
        mutableStateOf(false)
    }
    val calendarState = rememberSheetState()
    var destNote by remember { mutableStateOf("Type Here") }
    var destList = remember {
        mutableStateListOf(
            "Sintra",
            "Lisbon",
            "Porto",
            "Madeira"
        )
    }

    Scaffold (){paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // Destination Bar
            SearchBar(
                modifier = Modifier.fillMaxWidth(),
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
                    Text(text = "Search for destination ...")
                },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "Search Icon")
                },
                trailingIcon = {
                    if (destBarActive){
                        Icon(
                            modifier = Modifier.clickable {
                                if (destBarText.isNotEmpty()){
                                    destBarText = ""
                                } else {
                                    destBarActive = false
                                }
                            },
                            imageVector = Icons.Default.Close,
                            contentDescription = "Search Icon"
                        )
                    }
                }
            ) {
                destList.forEach {
                    Row (modifier = Modifier.padding(all = 14.dp)){
                        Icon(
                            modifier = Modifier.padding(end = 10.dp),
                            imageVector = Icons.Default.Place,
                            contentDescription = "Location"
                        )
                        Text(text = it)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Calendar Component
            // From: https://www.youtube.com/watch?v=uAw87DdUnxg

            CalendarDialog(
                state = calendarState,
                config = CalendarConfig(
                    monthSelection = true,
                    yearSelection = true,
                ),
                selection = Dates { dates ->
                    Log.d("SelectedDate", "$dates")
                }
            )

            Button(onClick = {
                calendarState.show()
            }) {
                Text(text = "Pick Date Range")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Trip Notes
            // From: https://developer.android.com/jetpack/compose/text/user-input

            TextField(
                value = destNote,
                onValueChange = { destNote = it },
                label = { Text("Enter Notes") },
                maxLines = 2,
                textStyle = TextStyle(color = Color.Black,),
                modifier = Modifier.padding(20.dp)
            )
        }
    }
}