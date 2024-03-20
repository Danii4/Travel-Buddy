package com.example.travelbuddy.create_trip.model
import com.example.travelbuddy.data.model.DestinationModel

class DestinationPageModel {
    data class DestinationViewState(
        val destinationList: MutableList<DestinationModel.Destination>
    )
}