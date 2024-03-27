package com.example.travelbuddy.repository

import com.example.travelbuddy.data.model.ResponseModel
import com.example.travelbuddy.data.model.TripModel

interface TripRepository {
    suspend fun addTrip(tripName: String, destIDList: List<String>) : ResponseModel.Response
}