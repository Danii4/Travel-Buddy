package com.example.travelbuddy.data

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.travelbuddy.data.model.ExpenseModel
import com.example.travelbuddy.data.model.ResponseModel
import com.example.travelbuddy.repository.AuthRepository
import com.example.travelbuddy.repository.TripRepository
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await

class ExpenseRepository(
    private val tripRepository: TripRepository,
    private val authRepository: AuthRepository
) {
    private var db: FirebaseFirestore = FirebaseFirestore.getInstance()


    suspend fun addExpense(expense: ExpenseModel.Expense): ResponseModel.Response {
        return try {
            val colRef = db.collection("expenses").add(
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

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getExpenses(tripId : String): Flow<ResponseModel.ResponseWithData<List<ExpenseModel.Expense>>> {
        return flow {
            emit(getExpenseData(tripId))
        }.flowOn(Dispatchers.IO)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private suspend fun getExpenseData(tripId: String): ResponseModel.ResponseWithData<List<ExpenseModel.Expense>> {
        val expenseIds = tripRepository.getExpenseIds(tripId)
        if (expenseIds.error != null) {
            return ResponseModel.ResponseWithData.Failure(error = expenseIds.error)
        }

        val expenseData: MutableList<DocumentSnapshot> = mutableListOf()
        expenseIds.data?.forEach {
            try {
                expenseData.add(db.collection("expenses").document(it).get().await())
            } catch (e: Exception) {
                return ResponseModel.ResponseWithData.Failure(error = e.message ?: "Error getting expenses")
            }
        }

        val expenseList = mutableListOf<ExpenseModel.Expense>()
        for (expense in expenseData) {
            val timestamp = expense.get("date") as Timestamp
            ExpenseModel.Expense(
                id = expense.id,
                name = expense.get("name") as String,
                type = ExpenseModel.ExpenseType.valueOf(expense.get("type") as String),
                amount = expense.getDouble("amount") as Double,
                date = timestamp.toDate()
            )
                .let { expenseList.add(it) }
        }
        return ResponseModel.ResponseWithData.Success(expenseList)
    }
}