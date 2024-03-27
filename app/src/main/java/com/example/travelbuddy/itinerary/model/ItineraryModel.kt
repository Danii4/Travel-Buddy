package com.example.travelbuddy.itinerary.model
data class ItineraryModel(val id: Int, val items: List<ItineraryItem>) {
    class ItineraryItem(val id: Int, val title: String, val date: String) {

    }
}