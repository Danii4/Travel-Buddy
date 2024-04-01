package com.example.travelbuddy.trips.add_trips.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.travelbuddy.CustomColors
import com.example.travelbuddy.trips.add_trips.AddTripsViewModel


@Composable
fun AddTripNameView() {
    val viewModel = hiltViewModel<AddTripsViewModel>()
    val state by viewModel.state.collectAsState()

    Column(
        modifier = Modifier
            .background(color = CustomColors.Indigo)
            .fillMaxSize()
            .padding(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom,
    ) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            AsyncImage(
                model = "https://i.ibb.co/WsdSCcS/pngwing-com.png",
                contentDescription = "Airplane"
            )
        }
        Column(
            modifier = Modifier
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(30.dp, 30.dp, 0.dp, 0.dp)
                )
                .padding(50.dp),
            verticalArrangement = Arrangement.spacedBy(25.dp)
        ) {
            Text(
                textAlign = TextAlign.Left,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                text = "Name your Adventure",
                lineHeight = 35.sp,
            )
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = state.tripName,
                onValueChange = { viewModel.setTripName(it) },
                singleLine = true,
                shape = RoundedCornerShape(15.dp)
            )
            Spacer(modifier = Modifier.height(25.dp))
        }
    }
}
