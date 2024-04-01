package com.example.travelbuddy.amadeus_client

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.amadeus.android.Amadeus
import com.amadeus.android.ApiResult
import com.example.travelbuddy.data.model.ItineraryModel
import com.example.travelbuddy.data.model.ResponseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.util.Date

class AmadeusClient() {
    private var client: Amadeus? = null
    private lateinit var activityContext: Context

    fun startClient() {
        client = Amadeus.Builder(activityContext)
            .setClientId("tbqK2WGYeVhktIJwwBQQ0MMyHTRDZ7pw")
            .setClientSecret("EAd0FaAWEOMObSxa")
            .build()
    }

    suspend fun getCoordinates(dest: String): ResponseModel.ResponseWithData<Pair<Double, Double>> {
        when (val coordinates =
            client!!.referenceData.locations.get(keyword = dest, subType = listOf("CITY"))) {
            is ApiResult.Success -> {
                return if (coordinates.data.isEmpty()) {
                    ResponseModel.ResponseWithData.Failure(error = "Coordinates is null")
                } else {
                    ResponseModel.ResponseWithData.Success(
                        Pair(
                            coordinates.data[0].geoCode!!.latitude,
                            coordinates.data[0].geoCode!!.longitude
                        )
                    )
                }
            }

            is ApiResult.Error -> {
                Log.d("Get Coordinate Error", "coordinates.exception?.message!!")
                return ResponseModel.ResponseWithData.Failure(error = "Error getting coordinates")
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getGeoPoint(
        latitude: Double?,
        longitude: Double?
    ): Flow<MutableList<ItineraryModel.Itinerary>> {
        val generatedItineraryList = mutableListOf<ItineraryModel.Itinerary>()
        val pointsOfInterest = client!!.referenceData.locations.pointsOfInterest.get(
            latitude = latitude!!,
            longitude = longitude!!
        )
        when (pointsOfInterest) {
            is ApiResult.Success -> {
                val results = pointsOfInterest.data
                results.forEach { location ->
                    val newItinerary = ItineraryModel.Itinerary(
                        name = location.name.toString(),
                        time = Date()
                    )
                    generatedItineraryList.add(newItinerary)
                }
            }
            is ApiResult.Error -> {}
        }
        return flow {
            emit(generatedItineraryList)
        }.flowOn(Dispatchers.IO)
    }

    fun setActivityContext(context: Context) {
        activityContext = context
    }
}