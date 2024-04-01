package com.example.travelbuddy.amadeus_client

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.amadeus.android.Amadeus
import com.amadeus.android.ApiResult
import com.example.travelbuddy.data.model.ItineraryModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import java.util.Date

class AmadeusClient() {
    private var client: Amadeus? = null
    private lateinit var activityContext: Context
    val job = SupervisorJob()
    val scope = CoroutineScope(Dispatchers.Main + job)

    fun startClient() {
        client = Amadeus.Builder(activityContext)
            .setClientId("tbqK2WGYeVhktIJwwBQQ0MMyHTRDZ7pw")
            .setClientSecret("EAd0FaAWEOMObSxa")
            .build()
    }

    fun getCoordinates(dest: String): List<String> {
        var res = listOf("")

        scope.launch {
            when (val coordinates = client!!.referenceData.locations.get(keyword=dest, subType = listOf(
                "CITY",
            )
            )){
                is ApiResult.Success -> {
                    if (coordinates?.data.isNullOrEmpty()){
                        res = listOf("")
                    } else {
                        res = listOf(coordinates.data[0].geoCode?.latitude.toString(),
                            coordinates.data[0].geoCode?.longitude.toString())
                    }
                }
                is ApiResult.Error -> {

                }
            }
        }
        return res
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getGeoPoint(): Flow<MutableList<ItineraryModel.Itinerary>>{
        val generatedItineraryList = mutableListOf<ItineraryModel.Itinerary>()

        val pointsOfInterest = client!!.referenceData.locations.pointsOfInterest.get(latitude = 40.71427, longitude = -74.00597)

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