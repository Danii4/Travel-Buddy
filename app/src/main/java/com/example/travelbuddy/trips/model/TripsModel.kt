package com.example.travelbuddy.trips.model

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable

data class TripsModel(val id: Int, val title: String, val date: String)
data class TripAddPageModel(val page: @Composable (paddingVal: PaddingValues) -> Unit, val metadata: String = "")
