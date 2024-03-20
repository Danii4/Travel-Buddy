package com.example.travelbuddy.data

import android.util.Log
//import com.example.farmeraid.data.model.InventoryModel
//import com.example.farmeraid.data.model.MarketModel
//import com.example.farmeraid.data.model.ResponseModel
//import com.example.farmeraid.data.model.TransactionModel
//import com.example.farmeraid.data.source.NetworkMonitor
//import com.example.farmeraid.transactions.model.TransactionsModel
import com.example.travelbuddy.data.model.ResponseModel
import com.example.travelbuddy.data.model.UserModel
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.Source
import kotlinx.coroutines.tasks.await
import java.util.Date
class ItineraryRepository(
    private val userRepository: UserRepository
) {
    private var db: FirebaseFirestore = FirebaseFirestore.getInstance()

    suspend fun createItinerary() : ResponseModel.Response {
        return userRepository.getUserId()?.let {
            val itineraryItemMap = hashMapOf(
                "Title" to item.title,
                "timestamp" to item.serverTimestamp(),
//                "type" to item.type.stringValue,
            )

            val createItineraryItemRef = db.collection("itineraryItem").document()
            try {
                createItineraryItemRef.set(itineraryItemMap)
                ResponseModel.Response.Success
            } catch (e: Exception) {
                ResponseModel.Response.Failure(e.message ?: "Unknown error while creating itinerary")
            }
        } ?: ResponseModel.Response.Failure("User does not exist")
    }
    suspend fun addItineraryItem(item: ItineraryModel.Item) : ResponseModel.Response {
        return userRepository.getItineraryId()?.let {ItineraryId ->
            val itineraryItemMap = hashMapOf(
                "Title" to item.title,
                "timestamp" to item.serverTimestamp(),
//                "type" to item.type.stringValue,
                "itineraryID" to ItineraryId,
            )

            val createItineraryItemRef = db.collection("itineraryItem").document()
            try {
                createItineraryItemRef.set(itineraryItemMap)
                ResponseModel.FAResponse.Success
            } catch (e: Exception) {
                ResponseModel.FAResponse.Failure(e.message ?: "Unknown error while adding itinerary item")
            }
        } ?: ResponseModel.FAResponse.Failure("User does not exist")
    }
}