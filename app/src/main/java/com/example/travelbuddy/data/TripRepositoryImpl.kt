package com.example.travelbuddy.data

import com.example.travelbuddy.data.model.ResponseModel
import com.example.travelbuddy.data.model.TripModel
import com.example.travelbuddy.repository.TripRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class TripRepositoryImpl(
) : TripRepository{
    private var db: FirebaseFirestore = FirebaseFirestore.getInstance()
    override suspend fun addTrip(trip: TripModel.Trip) : ResponseModel.Response {
        return try {
            // add trip to Trip table
            val colRef = db.collection("trip").add(
                mapOf(
                    "name" to trip.name,
                    "budgets" to trip.budgets,
                    "totalExpenses" to trip.totalExpenses,
                    "expensesList" to trip.expensesList,
                    "destinationList" to trip.destinationList
                )
            ).await()

            ResponseModel.Response.Success
        }
        catch (e: Exception) {
            return ResponseModel.Response.Failure(e.message?:"Error adding a trip. Please try again.")
        }
    }
}