package com.example.travelbuddy.repository

import com.example.travelbuddy.data.model.Currency
import com.example.travelbuddy.data.model.ExpenseModel
import com.example.travelbuddy.data.model.ResponseModel
import com.example.travelbuddy.data.model.TripModel
import kotlinx.coroutines.flow.Flow
import java.math.BigDecimal

interface TripRepository {
    suspend fun addTrip(tripName: String, destIdList: List<String>, budgets: List<Pair<ExpenseModel.ExpenseType, BigDecimal>>, defaultCurrency: Currency) : ResponseModel.ResponseWithData<String>
    suspend fun addTripIdToUser(Id: String)
    suspend fun getExpenseIds(tripId: String): ResponseModel.ResponseWithData<MutableList<String>>
    suspend fun getDestinationIds(tripId: String?): ResponseModel.ResponseWithData<MutableList<String>>
    suspend fun addDestinationId(tripId: String?, destinationId: String): ResponseModel.Response
    suspend fun deleteDestinationId(tripId: String?, destinationId: String): ResponseModel.Response
    suspend fun getTrips(tripId: String = ""): Flow<ResponseModel.ResponseWithData<List<TripModel.Trip>>>
    suspend fun getTripsIds(): ResponseModel.ResponseWithData<MutableList<String>>
    suspend fun getTripName(tripId: String): ResponseModel.ResponseWithData<String>
    suspend fun deleteTrip(tripId: String): ResponseModel.Response
}