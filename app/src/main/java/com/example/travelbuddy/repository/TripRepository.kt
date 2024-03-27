package com.example.travelbuddy.repository

import com.example.travelbuddy.data.model.ResponseModel

interface TripRepository {
    suspend fun addTrip(tripName: String, destIdList: List<String>) : ResponseModel.ResponseWithData<String>
    suspend fun addTripIdToUser(Id: String)
    suspend fun getExpenseIds(tripId: String): ResponseModel.ResponseWithData<MutableList<String>>
}