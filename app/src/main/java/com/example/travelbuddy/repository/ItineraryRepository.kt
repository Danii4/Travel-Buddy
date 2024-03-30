package com.example.travelbuddy.repository

import com.example.travelbuddy.data.model.ItineraryModel
import com.example.travelbuddy.data.model.ResponseModel

interface ItineraryRepository {
    suspend fun addItineraryItem(item: ItineraryModel.Itinerary) : ResponseModel.Response
}