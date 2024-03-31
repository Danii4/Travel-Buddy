package com.example.travelbuddy.data

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.travelbuddy.data.model.ItineraryModel
import com.example.travelbuddy.data.model.ResponseModel
import com.example.travelbuddy.repository.DestinationRepository
import com.example.travelbuddy.repository.ItineraryRepository
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

class ItineraryRepositoryImpl @Inject constructor(
    private val destinationRepository : DestinationRepository
) : ItineraryRepository {
    private var db: FirebaseFirestore = FirebaseFirestore.getInstance()
    override suspend fun addItineraryItem(itinerary: ItineraryModel.Itinerary) : ResponseModel.ResponseWithData<String> {
        val itineraryLoad = hashMapOf(
            "name" to itinerary.name,
            "time" to itinerary.time,
        )
        return try {
            val itineraryId = db.collection("itinerary").add(itineraryLoad).await().id
            Log.d("Itinerary ID", itineraryId)
            ResponseModel.ResponseWithData.Success(itineraryId)
        } catch (e: Exception) {
            ResponseModel.ResponseWithData.Failure(error = e.message ?: "Error adding itinerary")
        }
    }

    override suspend fun updateDestItineraryIds(destId: String?, itineraryId: String) : ResponseModel.Response{
        return try {
            db.collection("destinations").document(destId!!).update("itineraryList", FieldValue.arrayUnion(itineraryId))
            ResponseModel.Response.Success
        } catch (e: Exception){
            ResponseModel.Response.Failure("Error updating Destination Itinerary Ids")
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getItinerary(destinationId: String?): Flow<ResponseModel.ResponseWithData<List<ItineraryModel.Itinerary>>> {
        return flow {
            emit(getItineraryData(destinationId))
        }.flowOn(Dispatchers.IO)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private suspend fun getItineraryData(destinationId: String?): ResponseModel.ResponseWithData<List<ItineraryModel.Itinerary>> {
        val itineraryIds = destinationRepository.getItineraryIds(destinationId)
        if (itineraryIds.error != null) {
            return ResponseModel.ResponseWithData.Failure(error = itineraryIds.error)
        }
        val itineraryData: MutableList<DocumentSnapshot> = mutableListOf()
        itineraryIds.data?.forEach {
            try {
                itineraryData.add(db.collection("itinerary").document(it).get().await())
            } catch (e: Exception) {
                return ResponseModel.ResponseWithData.Failure(error = e.message ?: "Error getting itinerary data")
            }
        }

        val itineraryList = mutableListOf<ItineraryModel.Itinerary>()
        for (itinerary in itineraryData) {
            val time = itinerary.get("time") as Timestamp
            ItineraryModel.Itinerary(
                id = itinerary.id,
                name = itinerary.get("name") as String,
                time = time.toDate(),
            )
                .let { itineraryList.add(it) }
        }
        return ResponseModel.ResponseWithData.Success(itineraryList)
    }
}