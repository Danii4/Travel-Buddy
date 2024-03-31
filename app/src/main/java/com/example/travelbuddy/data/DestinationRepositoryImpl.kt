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

@Suppress("UNCHECKED_CAST")
class DestinationRepositoryImpl @Inject constructor(
    private val tripRepository: TripRepository
    ) : DestinationRepository {
    private var db: FirebaseFirestore = FirebaseFirestore.getInstance()
    override suspend fun addDestination(destination: DestinationModel.Destination): ResponseModel.ResponseWithData<String>? {
        val destinationLoad = hashMapOf(
            "name" to destination.name,
            "startDate" to destination.startDate,
            "endDate" to destination.endDate,
            "itineraryIdList" to destination.itineraryIdList
        )
        return try {
            val destID = db.collection("destinations").add(destinationLoad).await().id
            ResponseModel.ResponseWithData.Success(destID)
        } catch (e: Exception) {
            ResponseModel.ResponseWithData.Failure(error = e.message ?: "Error adding destination")
        }
    }

    override suspend fun deleteDestination(destinationId: String, tripId: String?): ResponseModel.ResponseWithData<String>? {
        return try {
            db.collection("destinations").document(destinationId).delete()
            ResponseModel.ResponseWithData.Success(destinationId)
        } catch (e: Exception) {
            ResponseModel.ResponseWithData.Failure(error = e.message ?: "Error deleting Destination")
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
                endDate = endDate.toDate(),
                itineraryIdList = destination.get("itineraryIdList") as List<String>,
            )
                .let { destinationList.add(it) }
        }
        return ResponseModel.ResponseWithData.Success(destinationList)
    }

    override suspend fun getItineraryIds(destinationId: String?): ResponseModel.ResponseWithData<MutableList<String>> {
        val destRef = destinationId.let { db.collection("destinations").document(it.toString()) }
        return try {
            val documentSnapshot  = destRef.get().await()
            if (documentSnapshot?.exists() == true) {
                val destData = documentSnapshot.data?.get("itineraryIdList") as MutableList<String>
                ResponseModel.ResponseWithData.Success(destData)
            }
            else {
                ResponseModel.ResponseWithData.Failure(error = "Destination does not exist")
            }
        } catch (e: Exception) {
            ResponseModel.ResponseWithData.Failure(error = e.message ?: "Error getting itinerary ids")
        }
    }
}