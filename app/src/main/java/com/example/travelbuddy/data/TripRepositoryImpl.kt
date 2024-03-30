package com.example.travelbuddy.data

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.travelbuddy.data.model.ResponseModel
import com.example.travelbuddy.repository.AuthRepository
import com.example.travelbuddy.data.model.TripModel
import com.example.travelbuddy.repository.TripRepository
import com.google.firebase.firestore.FieldValue
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
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

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getTrips(): Flow<ResponseModel.ResponseWithData<List<TripModel.Trip>>> {
        return flow {
            emit(getTripData())
        }.flowOn(Dispatchers.IO)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private suspend fun getTripData(): ResponseModel.ResponseWithData<List<TripModel.Trip>> {
        val tripIds = getTripsIds()
        if (tripIds.error != null) {
            return ResponseModel.ResponseWithData.Failure(error = tripIds.error)
        }

        val tripData: MutableList<DocumentSnapshot> = mutableListOf()
        tripIds.data?.forEach {
            try {
                tripData.add(db.collection("trips").document(it).get().await())
            } catch (e: Exception) {
                return ResponseModel.ResponseWithData.Failure(error = e.message ?: "Error getting trips")
            }
        }

        val tripList = mutableListOf<TripModel.Trip>()
        for (trip in tripData) {
            TripModel.Trip(
                id = trip.id,
                name = trip.get("name") as String,
//                budgets = MutableMap<ExpenseModel.ExpenseType, Double>,
//                expensesList = trip.get("expensesList") as List<String>,
                expensesList = null,
//                destinationList= trip.get("destinationList") as List<String>,
                destinationList = null
//                totalExpenses = MutableMap<ExpenseModel.ExpenseType, Double>
            )
                .let { tripList.add(it) }
        }
        return ResponseModel.ResponseWithData.Success(tripList)
    }

    override suspend fun getTripsIds(): ResponseModel.ResponseWithData<MutableList<String>> {
        val userId = FirebaseAuth.getInstance().currentUser?.uid.toString()
        val userRef = db.collection("users").document(userId)

        return try {
            val documentSnapshot  = userRef.get().await()
            if (documentSnapshot.exists()) {
                val tripsListData = documentSnapshot.data?.get("tripsIdList") as MutableList<String>
                ResponseModel.ResponseWithData.Success(tripsListData)
            }
            else {
                ResponseModel.ResponseWithData.Failure(error = "User does not exist")
            }
        } catch (e: Exception) {
            ResponseModel.ResponseWithData.Failure(error = e.message ?: "Error getting trip ids")
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
}