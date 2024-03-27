package com.example.travelbuddy.repository

import com.example.travelbuddy.data.model.ResponseModel
import com.example.travelbuddy.data.model.TripModel
import com.example.travelbuddy.itinerary.model.ItineraryModel

interface ItineraryRepository {
    suspend fun addItineraryItem(item: ItineraryModel.ItineraryItem) : ResponseModel.Response
}