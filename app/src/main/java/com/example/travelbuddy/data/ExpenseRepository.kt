package com.example.travelbuddy.data

import com.example.travelbuddy.data.model.ExpenseModel
import com.example.travelbuddy.data.model.ResponseModel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class ExpenseRepository {
    private var db: FirebaseFirestore = FirebaseFirestore.getInstance()

    suspend fun addExpense(expense: ExpenseModel.Expense): ResponseModel.Response {
        return try {
            val colRef = db.collection("expense").add(
                mapOf(
                    "name" to expense.name,
                    "type" to expense.type,
                    "amount" to expense.amount,
                    "date" to expense.date
                )
            ).await()
            ResponseModel.Response.Success
        } catch (e: Exception) {
            ResponseModel.Response.Failure(
                error = e.message ?: "Error adding an expense. Please try again."
            )
        }
    }


}