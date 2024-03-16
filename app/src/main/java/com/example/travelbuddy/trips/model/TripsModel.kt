package com.example.travelbuddy.trips.model

import androidx.compose.runtime.Composable

data class TripsModel(val id: Int, val title: String, val date: String)
data class TripAddPageModel(val title: String = "Test", val content: @Composable () -> Unit)
