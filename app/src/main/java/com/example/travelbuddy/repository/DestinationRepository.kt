package com.example.travelbuddy.repository

import com.example.travelbuddy.data.model.DestinationModel
import com.example.travelbuddy.data.model.ResponseModel
import kotlinx.coroutines.flow.Flow

interface DestinationRepository {
    suspend fun addDestination(destination: DestinationModel.Destination) : ResponseModel.ResponseWithData<String>?
    suspend fun deleteDestination(destinationId: String, tripId: String?): ResponseModel.Response
    suspend fun getDestinations(tripId: String?): Flow<ResponseModel.ResponseWithData<List<DestinationModel.Destination>>>
    suspend fun getItineraryIds(destinationId: String?): ResponseModel.ResponseWithData<MutableList<String>>
}