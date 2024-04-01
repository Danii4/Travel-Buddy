package com.example.travelbuddy.data

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.travelbuddy.data.model.Currency
import com.example.travelbuddy.data.model.ExpenseModel
import com.example.travelbuddy.data.model.ResponseModel
import com.example.travelbuddy.data.model.TripModel
import com.example.travelbuddy.repository.AuthRepository
import com.example.travelbuddy.repository.TripRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import java.math.BigDecimal
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
class TripRepositoryImpl @Inject constructor(
    private val authRepository: AuthRepository
) : TripRepository{
    private var db: FirebaseFirestore = FirebaseFirestore.getInstance()
    override suspend fun addTrip(
        tripName: String,
        destIdList: List<String>,
        budgets: List<Pair<ExpenseModel.ExpenseType, BigDecimal>>,
        defaultCurrency: Currency
    ): ResponseModel.ResponseWithData<String> {
        return try {
            val budgetMap = budgets.associate { it.first.name to it.second.toString() }
            // add trip to Trip table
            val tripID = db.collection("trips").add(
                mapOf(
                    "name" to tripName,
                    "budgets" to budgetMap,
                    "expensesList" to listOf<String>(),
                    "destinationList" to destIdList,
                    "defaultCurrency" to defaultCurrency.toMap()
                )
            ).await().id

            ResponseModel.ResponseWithData.Success(tripID)
        } catch (e: Exception) {
            return ResponseModel.ResponseWithData.Failure(
                error = e.message ?: "Error adding a trip. Please try again."
            )
        }
    }

    override suspend fun deleteTrip(tripId: String): ResponseModel.Response {
        try {
            authRepository.getUserId().let {
                db.collection("users").document(it!!).update("tripsIdList", FieldValue.arrayRemove(tripId))
            }
//            ResponseModel.Response.Success
        } catch (e: Exception) {
            return ResponseModel.Response.Failure(e.message ?: "Unknown error while updating user")
        }
        try {
            db.collection("trips").document(tripId).delete()
        } catch (e: Exception) {
            return ResponseModel.Response.Failure(e.message ?: "Unknown error while deleting trip")
        }
        return ResponseModel.Response.Success
    }

    override suspend fun addTripIdToUser(Id: String) {
        authRepository.getUserId().let {
            db.collection("users").document(it!!).update(
                "tripsIdList", FieldValue.arrayUnion(Id)
            )
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getTrips(tripId: String): Flow<ResponseModel.ResponseWithData<List<TripModel.Trip>>> {
        return flow {
            emit(getTripsData(tripId))
        }.flowOn(Dispatchers.IO)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private suspend fun getTripsData(tripId: String = ""): ResponseModel.ResponseWithData<List<TripModel.Trip>> {
        var tripIds : MutableList<String>? = mutableListOf()
        if (tripId.isBlank()) {
            val tripIdsResponse = getTripsIds()
            if (tripIdsResponse.error != null) {
                return ResponseModel.ResponseWithData.Failure(error = tripIdsResponse.error)
            }
            else {
                tripIds = tripIdsResponse.data
            }
        }
        else {
            tripIds?.add(tripId)
        }
        val tripData: MutableList<DocumentSnapshot> = mutableListOf()
        tripIds?.forEach {
            try {
                tripData.add(db.collection("trips").document(it).get().await())
            } catch (e: Exception) {
                return ResponseModel.ResponseWithData.Failure(error = e.message ?: "Error getting trips")
            }
        }

        val tripList = mutableListOf<TripModel.Trip>()
        for (trip in tripData) {
            val budgetsData = trip.get("budgets") as Map<String, String>
            val budgets = budgetsData.map { ExpenseModel.ExpenseType.from(it.key) to BigDecimal(it.value) }.toMap().toMutableMap()
            val defaultCurrencyMap = trip.get("defaultCurrency") as Map<String, String>
            TripModel.Trip(
                id = trip.id,
                name = trip.get("name") as String,
                budgets = budgets,
                expensesList = trip.get("expensesList") as List<String>,
                destinationList = trip.get("destinationList") as List<String>,
                defaultCurrency = Currency(code = defaultCurrencyMap["code"], name = defaultCurrencyMap["name"], symbol = defaultCurrencyMap["symbol"])
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

    override suspend fun getDestinationIds(tripId: String?): ResponseModel.ResponseWithData<MutableList<String>> {
        val tripRef = tripId.let { db.collection("trips").document(it.toString()) }
        return try {
            val documentSnapshot  = tripRef.get().await()
            if (documentSnapshot?.exists() == true) {
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

    override suspend fun addDestinationId(tripId: String?, destinationId: String): ResponseModel.Response {
        return try {
            tripId?.let { db.collection("trips").document(it).update("destinationList", FieldValue.arrayUnion(destinationId)) }
            ResponseModel.Response.Success
        } catch (e: Exception) {
            ResponseModel.Response.Failure(error = e.message ?: "Error adding destination")
        }
    }
    override suspend fun deleteDestinationId(tripId: String?, destinationId: String): ResponseModel.Response {
        return try {
            tripId?.let { db.collection("trips").document(it).update("destinationList", FieldValue.arrayRemove(destinationId)) }
            ResponseModel.Response.Success
        } catch (e: Exception) {
            ResponseModel.Response.Failure(error = e.message ?: "Error deleting destination")
        }
    }

    override suspend fun getTripName(tripId: String): ResponseModel.ResponseWithData<String> {
        return try {
            val destSnapshot = db.collection("trips").document(tripId).get().await()
            if (destSnapshot.exists()) {
                val destData = destSnapshot.data
                ResponseModel.ResponseWithData.Success(destData?.get("name").toString())
            } else {
                ResponseModel.ResponseWithData.Failure(error="Error getting Destination")
            }
        } catch (e: Exception) {
            ResponseModel.ResponseWithData.Failure(error="Error getting Destination")
        }
    }
}