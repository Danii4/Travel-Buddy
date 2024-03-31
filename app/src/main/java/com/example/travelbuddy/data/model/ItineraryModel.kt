package com.example.travelbuddy.data.model

import java.util.Date

class ItineraryModel {
    data class Itinerary(
        val id: String = "",
        val name: String,
        val time: Date
    )
}