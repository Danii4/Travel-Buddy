package com.example.travelbuddy.data

import com.example.travelbuddy.data.model.ResponseModel
import com.example.travelbuddy.repository.TripRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class TripRepositoryImpl @Inject constructor() : TripRepository{
    private var db: FirebaseFirestore = FirebaseFirestore.getInstance()
    override suspend fun addTrip(tripName: String, destIDList: List<String>) : ResponseModel.Response {
        return try {
            // add trip to Trip table
            val tripID = db.collection("trips").add(
                mapOf(
                    "name" to tripName,
                    "budgets" to null,
                    "totalExpenses" to null,
                    "expensesList" to null,
                    "destinationList" to destIDList
                )
            ).await()

            ResponseModel.Response.Success
        }
        catch (e: Exception) {
            return ResponseModel.Response.Failure(e.message?:"Error adding a trip. Please try again.")
        }
    }
}