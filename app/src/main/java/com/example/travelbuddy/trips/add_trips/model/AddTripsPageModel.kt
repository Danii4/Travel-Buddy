package com.example.travelbuddy.trips.add_trips.model

import com.example.travelbuddy.data.model.DestinationModel

class AddTripsPageModel {
    data class AddTripViewState(
        val destinationList: List<DestinationModel.Destination> = emptyList(),
        val tripName: String = "",
        var tripId: String? = null
    )
}