package com.example.travelbuddy.itinerary.model

import com.example.travelbuddy.data.model.ItineraryModel

class ItineraryModel {
    data class ItineraryViewState(
        val itineraryList: List<ItineraryModel.Itinerary> = emptyList(),
        val isLoading: Boolean = false,
    )
}