package com.example.travelbuddy.data

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.travelbuddy.data.model.ExpenseModel
import com.example.travelbuddy.data.model.ResponseModel
import com.example.travelbuddy.repository.AuthRepository
import com.example.travelbuddy.repository.TripRepository
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import java.math.BigDecimal

class ExpenseRepository(
    private val tripRepository: TripRepository,
    private val authRepository: AuthRepository
) {
    private var db: FirebaseFirestore = FirebaseFirestore.getInstance()


    suspend fun addUpdateExpense(expense: ExpenseModel.Expense, tripId: String?): ResponseModel.Response {
        val expenseSnapshot = db.collection("expenses").document(expense.id).get().await()
        if (tripId.isNullOrBlank()) {
            return ResponseModel.Response.Failure(error = "Error: Null or Empty tripId")
        }
        return try {
            if (expenseSnapshot.exists()) {
                db.collection("expenses").document(expense.id).update(
                    "name", expense.name,
                    "type", expense.type,
                    "amount", expense.amount.toString(),
                    "date", expense.date,
                    "currencyCode", expense.currencyCode
                )
            } else {
                val expenseRef = db.collection("expenses").add(
                    mapOf(
                        "name" to expense.name,
                        "type" to expense.type,
                        "amount" to expense.amount,
                        "date" to expense.date
                    )
                ).await()
                db.collection("trips").document(tripId).update(
                    "expenses", FieldValue.arrayUnion(expenseRef.id)
                )
            }
            ResponseModel.Response.Success
        }
        catch (e: Exception) {
                return ResponseModel.Response.Failure(
                    error = e.message ?: "Error adding an expense. Please try again."
                )
            }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getExpenses(tripId: String): Flow<ResponseModel.ResponseWithData<List<ExpenseModel.Expense>>> {
        return flow {
            emit(getExpensesData(tripId))
        }.flowOn(Dispatchers.IO)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private suspend fun getExpensesData(tripId: String): ResponseModel.ResponseWithData<List<ExpenseModel.Expense>> {
        val expenseIds = tripRepository.getExpenseIds(tripId)
        if (expenseIds.error != null) {
            return ResponseModel.ResponseWithData.Failure(error = expenseIds.error)
        }

        val expenseData: MutableList<DocumentSnapshot> = mutableListOf()
        expenseIds.data?.forEach {
            try {
                expenseData.add(db.collection("expenses").document(it).get().await())
            } catch (e: Exception) {
                return ResponseModel.ResponseWithData.Failure(
                    error = e.message ?: "Error getting expenses"
                )
            }
        }

        val expenseList = mutableListOf<ExpenseModel.Expense>()
        for (expense in expenseData) {
            val timestamp = expense.get("date") as Timestamp
            val amount = expense.get("amount") as String
            ExpenseModel.Expense(
                id = expense.id,
                name = expense.get("name") as String,
                type = ExpenseModel.ExpenseType.valueOf(expense.get("type") as String),
                amount = BigDecimal(amount),
                date = timestamp.toDate(),
                currencyCode = expense.get("currencyCode") as String
            )
                .let { expenseList.add(it) }
        }
        return ResponseModel.ResponseWithData.Success(expenseList)
    }

    suspend fun getExpenseData(expenseId: String): ResponseModel.ResponseWithData<ExpenseModel.Expense> {
        try {
            val expense = db.collection("expenses").document(expenseId).get().await()
            val timestamp = expense.get("date") as Timestamp
            val amount = expense.get("amount") as String
            val expenseData = ExpenseModel.Expense(
                id = expense.id,
                name = expense.get("name") as String,
                type = ExpenseModel.ExpenseType.valueOf(expense.get("type") as String),
                amount = BigDecimal(amount),
                date = timestamp.toDate(),
                currencyCode = expense.get("currencyCode") as String
            )
            return ResponseModel.ResponseWithData.Success(expenseData)
        } catch (e: Exception) {
            return ResponseModel.ResponseWithData.Failure(
                error = e.message ?: "Error getting expense with id"
            )
        }
    }

    suspend fun getExpense(expenseId: String) : Flow<ResponseModel.ResponseWithData<ExpenseModel.Expense>> {
        return flow {
            emit(getExpenseData(expenseId))
        }.flowOn(Dispatchers.IO)
    }
}