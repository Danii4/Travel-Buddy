package com.example.travelbuddy.create_trip.views

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateTripAddView() {

    Column(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        // Calendar Component
        // From: https://www.youtube.com/watch?v=uAw87DdUnxg
        val calendarState = rememberSheetState()

        CalendarDialog(
            state = calendarState,
            config = CalendarConfig(
                monthSelection = true,
                yearSelection = true,
            ),
            selection = Dates{dates ->
                Log.d("SelectedDate", "$dates")
            }
        )

        Button (onClick = {
            calendarState.show()
        }) {
            Text (text = "Pick Date Range")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Trip Notes
        // From: https://developer.android.com/jetpack/compose/text/user-input
        var note by remember { mutableStateOf("Type Here") }

        TextField(
            value = note,
            onValueChange = { note = it },
            label = { Text("Enter Notes") },
            maxLines = 2,
            textStyle = TextStyle(color = Color.Black,),
            modifier = Modifier.padding(20.dp)
        )
    }
}