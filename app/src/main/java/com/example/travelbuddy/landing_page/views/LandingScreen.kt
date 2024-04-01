package com.example.travelbuddy.landing_page.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.CurrencyExchange
import androidx.compose.material.icons.filled.FlightTakeoff
import androidx.compose.material.icons.filled.PrivateConnectivity
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material3.Icon
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
import com.example.travelbuddy.languageTranslation.CustomColors

@Composable
fun LandingScreen() {
    val viewModel = hiltViewModel<LandingScreenViewModel>()
    val state by viewModel.state.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp)
    ) {
        // Welcome Message
        Text(
            text = "Welcome ${state.userName}!",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
        )

        Spacer(modifier = Modifier.height(15.dp))

        Column(
            modifier = Modifier
                .background(CustomColors.Indigo, shape = RoundedCornerShape(16.dp))
                .height(225.dp)
                .fillMaxWidth()
                .padding(15.dp),
        ) {
            Text(
                text = "Upcoming Trip",
                fontSize = 22.sp,
                modifier = Modifier.padding(10.dp),
                textAlign = TextAlign.Left,
                color = Color.White,
            )
            Spacer(modifier = Modifier.weight(1f, fill = true))
            Row {
                Text(
                    text = state.lastTripName,
                    fontSize = 36.sp,
                    modifier = Modifier
                        .padding(15.dp)
                        .weight(1f),
                    textAlign = TextAlign.Right,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
                Icon (
                    imageVector = Icons. Default.ChevronRight,
                    contentDescription = "Itinerary View",
                    tint = Color.White,
                    modifier = Modifier.size(36.dp).align(Alignment.CenterVertically),
                )
            }
        }

        Spacer(modifier = Modifier.height(15.dp))

        // Grid of Buttons
        GridOfButtons()
    }
}

@Composable
fun GridOfButtons() {
    val viewModel = hiltViewModel<LandingScreenViewModel>()

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        Row(
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            Box(
                modifier = Modifier.weight(1f)
                    .fillMaxHeight()
                    .background(CustomColors.LightIndigo, shape = RoundedCornerShape(15.dp))
                    .clickable {
                               viewModel.navigateToTrips()
                    },
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    modifier = Modifier.size(45.dp),
                    imageVector = Icons.Default.FlightTakeoff,
                    contentDescription = null,
                    tint = Color.White,
                )
            }
            Box(
                modifier = Modifier.weight(1f)
                    .fillMaxHeight()
                    .background(CustomColors.LightIndigo, shape = RoundedCornerShape(15.dp))
                    .clickable {
                        viewModel.navigateToTranslate()
                    },
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    modifier = Modifier.size(45.dp),
                    imageVector = Icons.Default.Translate,
                    contentDescription = null,
                    tint = Color.White,
                )
            }
        }
        Row(
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            Box(
                modifier = Modifier.weight(1f)
                    .fillMaxHeight()
                    .background(CustomColors.LightIndigo, shape = RoundedCornerShape(15.dp))
                    .clickable {
                        viewModel.navigateToCurrency()
                    },
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    modifier = Modifier.size(45.dp),
                    imageVector = Icons.Default.CurrencyExchange,
                    contentDescription = null,
                    tint = Color.White,
                )
            }
            Box(
                modifier = Modifier.weight(1f)
                    .fillMaxHeight()
                    .background(CustomColors.LightIndigo, shape = RoundedCornerShape(15.dp))
                    .clickable {
                        viewModel.navigateToFAQ()
                    },
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    modifier = Modifier.size(45.dp),
                    imageVector = Icons.Default.PrivateConnectivity,
                    contentDescription = null,
                    tint = Color.White,
                )
            }
        }
    }
}