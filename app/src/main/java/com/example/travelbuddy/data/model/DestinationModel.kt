package com.example.travelbuddy.data.model

import com.example.travelbuddy.itinerary.model.ItineraryItem
import com.example.travelbuddy.itinerary.model.ItineraryModel
import java.time.LocalDate

class DestinationModel {
    data class Destination(
        val name: String = "",
        val startDate: LocalDate,
        val endDate: LocalDate,
        val itinerary: ItineraryModel = ItineraryModel(0, listOf(ItineraryItem(0,"a","test")))
    )
}