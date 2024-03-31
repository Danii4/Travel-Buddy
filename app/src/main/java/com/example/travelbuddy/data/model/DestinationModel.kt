package com.example.travelbuddy.data.model

import java.util.Date

class DestinationModel {
    data class Destination(
        val id: String = "",
        val name: String = "",
        val itineraryList: List<ItineraryModel.Itinerary> = emptyList(),
        val startDate: Date,
        val endDate: Date,
    )
}