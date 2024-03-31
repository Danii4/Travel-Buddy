package com.example.travelbuddy.itinerary.model

import com.example.travelbuddy.data.model.ItineraryModel

class ItineraryPageModel {
    data class ItineraryViewState(
        var itineraryList: List<ItineraryModel.Itinerary> = emptyList(),
        var name: String = "",
        var destinationId: String? = null
    )
}