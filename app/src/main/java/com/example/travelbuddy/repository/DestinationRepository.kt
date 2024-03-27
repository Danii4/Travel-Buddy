package com.example.travelbuddy.repository

import com.example.travelbuddy.data.model.DestinationModel
import com.example.travelbuddy.data.model.ResponseModel

interface DestinationRepository {
    suspend fun addDestination(destination: DestinationModel.Destination) : ResponseModel.Response?
}