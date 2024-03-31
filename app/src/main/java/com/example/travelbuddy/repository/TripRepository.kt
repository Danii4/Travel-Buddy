package com.example.travelbuddy.repository

import com.example.travelbuddy.data.model.ExpenseModel
import com.example.travelbuddy.data.model.ResponseModel
import java.math.BigDecimal
import com.example.travelbuddy.data.model.TripModel
import kotlinx.coroutines.flow.Flow

interface TripRepository {
    suspend fun addTrip(tripName: String, destIdList: List<String>, budgets: List<Pair<ExpenseModel.ExpenseType, BigDecimal>>, defaultCurrency: String) : ResponseModel.ResponseWithData<String>
    suspend fun addTripIdToUser(Id: String)
    suspend fun getExpenseIds(tripId: String): ResponseModel.ResponseWithData<MutableList<String>>
    suspend fun getDestinationIds(tripId: String?): ResponseModel.ResponseWithData<MutableList<String>>
    suspend fun updateDestinationIds(tripId: String?, destIdList: List<String>): ResponseModel.Response
    suspend fun getTrips(tripId: String = ""): Flow<ResponseModel.ResponseWithData<List<TripModel.Trip>>>
    suspend fun getTripsIds(): ResponseModel.ResponseWithData<MutableList<String>>

}