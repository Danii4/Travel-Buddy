package com.example.travelbuddy.data.model

import java.util.Date

class ItineraryModel {
    data class Itinerary(
        val id: Int,
        val title: String,
        val time: Date
    )
}