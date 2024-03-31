package com.example.travelbuddy.repository

import com.example.travelbuddy.data.model.ItineraryModel
import com.example.travelbuddy.data.model.ResponseModel
import kotlinx.coroutines.flow.Flow

interface ItineraryRepository {
    suspend fun addItineraryItem(itinerary: ItineraryModel.Itinerary) : ResponseModel.ResponseWithData<String>
    suspend fun updateDestItineraryIds(destId: String?, itineraryId: String) : ResponseModel.Response
    suspend fun getItinerary(destinationId: String?): Flow<ResponseModel.ResponseWithData<List<ItineraryModel.Itinerary>>>
}