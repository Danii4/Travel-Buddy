package com.example.travelbuddy.data

import com.google.firebase.firestore.FirebaseFirestore
import com.example.travelbuddy.data.model.DestinationModel
import com.example.travelbuddy.data.model.ResponseModel
import com.example.travelbuddy.repository.AuthRepository
import com.example.travelbuddy.repository.DestinationRepository
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class DestinationRepositoryImpl @Inject constructor(
    private val authRepository: AuthRepository
) : DestinationRepository {
    private var db: FirebaseFirestore = FirebaseFirestore.getInstance()
    override suspend fun addDestination(destination: DestinationModel.Destination): ResponseModel.ResponseWithData<String>? {
        val destinationLoad = hashMapOf(
            "name" to destination.name,
            "startDate" to destination.startDate,
            "endDate" to destination.endDate,
        )
        return try {
            val destID = db.collection("destinations").add(destinationLoad).await().id
            ResponseModel.ResponseWithData.Success(destID)
        } catch (e: Exception) {
            ResponseModel.ResponseWithData.Failure(error = e.message ?: "Error adding destination")
        }
    }
}