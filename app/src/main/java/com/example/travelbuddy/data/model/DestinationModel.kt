package com.example.travelbuddy.data.model

import com.example.travelbuddy.itinerary.model.ItineraryItem
import com.example.travelbuddy.itinerary.model.ItineraryModel
import java.time.LocalDate
import java.util.Date

class DestinationModel {
    data class Destination(
        val id: String = "",
        val name: String = "",
        val itinerary: ItineraryModel = ItineraryModel(0, listOf(ItineraryItem(0,"a","test")))
        val startDate: Date,
        val endDate: Date,
    )
}