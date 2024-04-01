package com.example.travelbuddy.amadeus_client

import android.content.Context
import android.util.Log
import com.amadeus.android.Amadeus
import com.amadeus.android.ApiResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

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
                        Log.d("ERROR AMADEUS", "${coordinates.toString()}")
                        res = listOf("")
                    } else {
                        Log.d("Result", coordinates?.data?.get(0)?.geoCode.toString())
                        res = listOf(coordinates.data[0].geoCode?.latitude.toString(),
                            coordinates.data[0].geoCode?.longitude.toString())
                    }
                }
                is ApiResult.Error -> {
                    Log.d("ERROR AMADEUS", "${coordinates.toString()}")
                    // Handle your error

                }
            }
        }
        return res
    }

    fun getGeoPoint(){
        scope.launch {
            when (val pointsOfInterest = client!!.referenceData.locations.pointsOfInterest.get(latitude = 41.397158, longitude = 2.160873)){
                is ApiResult.Success -> {
                    Log.d("Result", "${pointsOfInterest.data}")
                }
                is ApiResult.Error -> {
                    // Handle your error
                }
            }
        }
    }
    fun setActivityContext(context: Context) {
        activityContext = context
    }
}