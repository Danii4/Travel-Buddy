package com.example.travelbuddy.data

import com.google.firebase.firestore.FirebaseFirestore
import com.example.travelbuddy.data.model.DestinationModel
import com.example.travelbuddy.data.model.ResponseModel
import kotlinx.coroutines.tasks.await


class DestinationRepositoryImpl () {
    private var db: FirebaseFirestore = FirebaseFirestore.getInstance()

    suspend fun addDestination(destination: DestinationModel.Destination) : ResponseModel.Response {
        return try {
            val destinationLoad = hashMapOf(
                "name" to destination.name,
                "startDate" to destination.startDate,
                "endDate" to destination.endDate,
            )
            val destinationDB = db.collection("destinations").add(
                destinationLoad
            ).await()
            ResponseModel.Response.Success
        } catch (e: Exception){
            ResponseModel.Response.Error(e.message ?: "Error adding destination")
        }
    }
}