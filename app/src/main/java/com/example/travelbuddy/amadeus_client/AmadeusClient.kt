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