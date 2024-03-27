package com.example.travelbuddy.repository

import com.example.travelbuddy.data.model.ResponseModel
import com.example.travelbuddy.data.model.TripModel

interface TripRepository {
    suspend fun addTrip(trip: TripModel.Trip) : ResponseModel.Response
}