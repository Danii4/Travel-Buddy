package com.example.travelbuddy.data

import com.example.travelbuddy.data.model.ResponseModel
import com.example.travelbuddy.repository.AuthRepository
import com.example.travelbuddy.repository.TripRepository
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class TripRepositoryImpl @Inject constructor(
    private val authRepository: AuthRepository
) : TripRepository{
    private var db: FirebaseFirestore = FirebaseFirestore.getInstance()
    override suspend fun addTrip(tripName: String, destIdList: List<String>) : ResponseModel.ResponseWithData<String> {
        return try {
            // add trip to Trip table
            val tripID = db.collection("trips").add(
                mapOf(
                    "name" to tripName,
                    "budgets" to null,
                    "totalExpenses" to null,
                    "expensesList" to null,
                    "destinationList" to destIdList
                )
            ).await().id

            ResponseModel.ResponseWithData.Success(tripID)
        }
        catch (e: Exception) {
            return ResponseModel.ResponseWithData.Failure(error=e.message?:"Error adding a trip. Please try again.")
        }
    }

    override suspend fun addTripIdToUser(Id: String){
        authRepository.getUserId().let {
            db.collection("users").document(it!!).update(
                "tripsIdList", FieldValue.arrayUnion(Id)
            )
        }
    }

    override suspend fun getExpenseIds(tripId: String): ResponseModel.ResponseWithData<MutableList<String>> {
        val tripRef = db.collection("trips").document(tripId)
        return try {
            val documentSnapshot  = tripRef.get().await()
            if (documentSnapshot.exists()) {
                val tripData = documentSnapshot.data?.get("expensesList") as MutableList<String>
                ResponseModel.ResponseWithData.Success(tripData)
            }
            else {
                ResponseModel.ResponseWithData.Failure(error = "Trip does not exist")
            }
        } catch (e: Exception) {
            ResponseModel.ResponseWithData.Failure(error = e.message ?: "Error getting expense ids")
        }
    }

    override suspend fun getDestinationIds(tripId: String): ResponseModel.ResponseWithData<MutableList<String>> {
        val tripRef = db.collection("trips").document(tripId)
        return try {
            val documentSnapshot  = tripRef.get().await()
            if (documentSnapshot.exists()) {
                val tripData = documentSnapshot.data?.get("destinationList") as MutableList<String>
                ResponseModel.ResponseWithData.Success(tripData)
            }
            else {
                ResponseModel.ResponseWithData.Failure(error = "Trip does not exist")
            }
        } catch (e: Exception) {
            ResponseModel.ResponseWithData.Failure(error = e.message ?: "Error getting destination ids")
        }
    }

    override suspend fun updateDestinationIds(tripId: String, destIdList: List<String>): ResponseModel.Response {
        return try {
            db.collection("trips").document(tripId).update("destinationList", destIdList)
            ResponseModel.Response.Success
        } catch (e: Exception) {
            ResponseModel.Response.Failure(error = e.message ?: "Error updating destination")
        }
    }
}