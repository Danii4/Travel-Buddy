package com.example.travelbuddy.data

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.travelbuddy.data.model.DestinationModel
import com.example.travelbuddy.data.model.ResponseModel
import com.example.travelbuddy.repository.DestinationRepository
import com.example.travelbuddy.repository.TripRepository
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class DestinationRepositoryImpl @Inject constructor(
    private val tripRepository: TripRepository
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

    override suspend fun deleteDestination(destinationId: String, tripId: String?): ResponseModel.Response {
        try {
            db.collection("transactions").document(destinationId).delete()
        } catch (e: Exception) {
            return ResponseModel.Response.Failure(e.message ?: "Unknown error while deleting destination")
        }
        return try {
            tripId?.let { db.collection("trips").document(it).update("destinationList", FieldValue.arrayRemove(destinationId)) }
            ResponseModel.Response.Success
        } catch (e: Exception) {
            ResponseModel.Response.Failure(e.message ?: "Unknown message while updating farm")
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getDestinations(tripId: String?): Flow<ResponseModel.ResponseWithData<List<DestinationModel.Destination>>>{
        return flow {
            emit(getDestinationData(tripId))
        }.flowOn(Dispatchers.IO)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private suspend fun getDestinationData(tripId: String?): ResponseModel.ResponseWithData<List<DestinationModel.Destination>> {
        val destinationIds = tripRepository.getDestinationIds(tripId)
        if (destinationIds.error != null) {
            return ResponseModel.ResponseWithData.Failure(error = destinationIds.error)
        }
        val destinationData: MutableList<DocumentSnapshot> = mutableListOf()
        destinationIds.data?.forEach {
            try {
                destinationData.add(db.collection("destinations").document(it).get().await())
            } catch (e: Exception) {
                return ResponseModel.ResponseWithData.Failure(error = e.message ?: "Error getting destinations")
            }
        }

        val destinationList = mutableListOf<DestinationModel.Destination>()
        for (destination in destinationData) {
            val startDate = destination.get("startDate") as Timestamp
            val endDate = destination.get("endDate") as Timestamp
            DestinationModel.Destination(
                id = destination.id,
                name = destination.get("name") as String,
                startDate = startDate.toDate(),
                endDate = endDate.toDate()
            )
                .let { destinationList.add(it) }
        }
        return ResponseModel.ResponseWithData.Success(destinationList)
    }

    override suspend fun updateItineraryIds(destinationId: String, itineraryIdList: List<String>): ResponseModel.Response {
        return try {
            db.collection("destinations").document(destinationId).update("itineraryIdList", itineraryIdList)
            ResponseModel.Response.Success
        } catch (e: Exception) {
            ResponseModel.Response.Failure(error = e.message ?: "Error updating destination itinerary")
        }
    }
}