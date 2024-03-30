package com.example.travelbuddy.data.model

import com.google.firebase.Timestamp

class ItineraryModel {
    data class Itinerary(
        val id: Int,
        val title: String,
        val time: Timestamp
    )
}