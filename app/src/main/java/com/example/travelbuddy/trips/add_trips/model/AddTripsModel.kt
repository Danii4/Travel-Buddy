package com.example.travelbuddy.trips.add_trips.model

import com.example.travelbuddy.data.model.DestinationModel

class DestinationPageModel {
    data class DestinationViewState(
        val destinationList: MutableList<DestinationModel.Destination>
    )
}