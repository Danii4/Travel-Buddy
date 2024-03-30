package com.example.travelbuddy.trips.model

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import com.example.travelbuddy.data.model.TripModel

class TripsModel{
    data class TripsViewState(
        val tripsList: List<TripModel.Trip> = emptyList(),
        val isLoading: Boolean = false,
    )
}
data class TripAddPageModel(val page: @Composable (paddingVal: PaddingValues) -> Unit, val metadata: String = "")