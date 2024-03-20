package com.example.travelbuddy.data

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.example.travelbuddy.data.model.DestinationModel
import com.example.travelbuddy.data.model.ResponseModel
import com.example.travelbuddy.repository.DestinationRepository
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class DestinationRepositoryImpl @Inject constructor() :  DestinationRepository {
    private var db: FirebaseFirestore = FirebaseFirestore.getInstance()

    override suspend fun addDestination(destination: DestinationModel.Destination) : ResponseModel.Response {
        return try {
            val destinationLoad = hashMapOf(
                "name" to destination.name,
                "startDate" to destination.startDate,
                "endDate" to destination.endDate,
            )
            Log.d("destination loaded", destinationLoad.toString())
//            val destinationDB = db.collection("destinations").add(
//                destinationLoad
//            ).await()
            val firestoreDb = Firebase.firestore
            firestoreDb.collection("destinations").add(
                destinationLoad
            ).await()
            ResponseModel.Response.Success
        } catch (e: Exception){
            ResponseModel.Response.Failure(e.message ?: "Error adding destination")
        }
    }
}