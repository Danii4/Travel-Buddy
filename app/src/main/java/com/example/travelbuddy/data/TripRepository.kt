package com.example.travelbuddy.data

import com.example.travelbuddy.data.model.ExpenseModel
import com.example.travelbuddy.data.model.ResponseModel
import com.example.travelbuddy.data.model.TripModel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class TripRepository @Inject constructor()
//    private val AuthRepositoryImpl: AuthRepositoryImpl
 {
    private var db: FirebaseFirestore = FirebaseFirestore.getInstance()
    suspend fun addTrip(trip: TripModel.Trip) : ResponseModel.Response {
        return try {
            // add trip to Trip table
            val colRef = db.collection("trip").add(
                mapOf(
                    "name" to trip.name,
//                    "budgets" to trip.budgets,
//                    "totalExpenses" to trip.totalExpenses,
//                    "expensesList" to trip.expensesList,
//                    "destinationList" to trip.destinationList
                )
            ).await()

//            // add Trip ID to current User entry
//            userRepository.getUserId()?.let {
//                db.collection("user")
//            }

            ResponseModel.Response.Success
        }
        catch (e: Exception) {
            return ResponseModel.Response.Failure(e.message?:"Error adding a trip. Please try again.")
        }
    }

     suspend fun getTrips(
//         trip: TripModel.Trip
         //add user later
     ) : ResponseModel.ResponseWithData<TripModel.Trip> {
         return try {
//             val trips = db.collection("trip").get(
//                 db.collection("farm").document(it).get(networkMonitor.getSource()).await().data?.let{ farm ->
//                     farm["farmCode"] as String
//                 }
//             ).await()

//            // add Trip ID to current User entry
//            userRepository.getUserId()?.let {
//                db.collection("user")
//            }
             return db.collection("trip").get() as

         }
         catch (e: Exception) {
             return null
//             return ResponseModel.Response.Failure(e.message?:"Error adding a trip. Please try again.")
         }
     }
}