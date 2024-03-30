package com.example.travelbuddy.data

import com.example.travelbuddy.data.model.ItineraryModel
import com.example.travelbuddy.data.model.ResponseModel
import com.example.travelbuddy.repository.ItineraryRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class ItineraryRepositoryImpl(
) : ItineraryRepository {
    private var db: FirebaseFirestore = FirebaseFirestore.getInstance()
    override suspend fun addItineraryItem(item: ItineraryModel.Itinerary) : ResponseModel.Response {
        return try {
            val colRef = db.collection("itinerary").add(
                mapOf(
                    "title" to item.title,
                    "time" to item.time
                )
            ).await()

            ResponseModel.Response.Success
        }
        catch (e: Exception) {
            return ResponseModel.Response.Failure(e.message?:"Error adding a itinerary item. Please try again.")
        }
    }
}