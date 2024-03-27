package com.example.travelbuddy.data

import com.example.travelbuddy.data.model.DestinationModel
import com.example.travelbuddy.data.model.ResponseModel
import com.example.travelbuddy.data.model.TripModel
import com.example.travelbuddy.itinerary.model.ItineraryModel
import com.example.travelbuddy.repository.ItineraryRepository
import com.example.travelbuddy.repository.TripRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class ItineraryRepositoryImpl(
) : ItineraryRepository {
    private var db: FirebaseFirestore = FirebaseFirestore.getInstance()
    override suspend fun addItineraryItem(item: ItineraryModel.ItineraryItem) : ResponseModel.Response {
        return try {
            // add trip to Trip table
            val colRef = db.collection("itinerary").add(
                mapOf(
                    "title" to item.title,
                    "date" to item.date
                )
            ).await()

            ResponseModel.Response.Success
        }
        catch (e: Exception) {
            return ResponseModel.Response.Failure(e.message?:"Error adding a itinerary item. Please try again.")
        }
    }
}