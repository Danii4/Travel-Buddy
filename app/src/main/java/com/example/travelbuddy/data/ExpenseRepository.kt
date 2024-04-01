package com.example.travelbuddy.data

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.travelbuddy.data.model.Currency
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


    suspend fun addUpdateExpense(
        expense: ExpenseModel.Expense,
        tripId: String?
    ): ResponseModel.Response {
        if (tripId.isNullOrBlank()) {
            return ResponseModel.Response.Failure(error = "Error: Null or Empty tripId")
        }
        if (expense.id.isNotBlank()) {
            val expenseSnapshot = db.collection("expenses").document(expense.id).get().await()
            return try {
                if (expenseSnapshot.exists()) {
                    db.collection("expenses").document(expense.id).update(
                        "name", expense.name,
                        "type", expense.type,
                        "amount", expense.amount.toString(),
                        "date", Timestamp(expense.date),
                        "currency", expense.currency.toMap()
                    )
                }
                ResponseModel.Response.Success
            } catch (e: Exception) {
                return ResponseModel.Response.Failure(
                    error = e.message ?: "Error adding an expense. Please try again."
                )
            }
        } else {
            return try {
                val expenseRef = db.collection("expenses").add(
                    mapOf(
                        "name" to expense.name,
                        "type" to expense.type,
                        "amount" to expense.amount.toString(),
                        "date" to Timestamp(expense.date),
                        "currency" to expense.currency.toMap()
                    )
                ).await()
                db.collection("trips").document(tripId).update(
                    "expensesList", FieldValue.arrayUnion(expenseRef.id)
                )
                ResponseModel.Response.Success
            } catch (e: Exception) {
                return ResponseModel.Response.Failure(
                    error = e.message ?: "Error adding an expense. Please try again."
                )
            }
        }
    }

    suspend fun deleteExpense(expenseId: String?, tripId: String?): ResponseModel.Response {
        if (tripId.isNullOrBlank()) {
            return ResponseModel.Response.Failure("Error deleting expenses, tripId is null or blank")
        }
        else if (expenseId.isNullOrBlank()) {
            return ResponseModel.Response.Failure("Error deleting expenses, expenseId is null or blank")
        }
        return try {
            db.collection("trips").document(tripId).update("expensesList", FieldValue.arrayRemove(expenseId))
            try {
                db.collection("expenses").document(expenseId).delete()
                ResponseModel.Response.Success
            } catch (e: Exception) {
                return ResponseModel.Response.Failure(e.message ?: "Unknown error deleting expense")
            }
        } catch (e: Exception) {
            return ResponseModel.Response.Failure(e.message ?: "Unknown error removing expense from trip")
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getExpenses(tripId: String, defaultCurrency: Currency?): Flow<ResponseModel.ResponseWithData<List<ExpenseModel.Expense>>> {
        return flow {
            emit(getExpensesData(tripId, defaultCurrency))
        }.flowOn(Dispatchers.IO)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private suspend fun getExpensesData(tripId: String, defaultCurrency: Currency?): ResponseModel.ResponseWithData<List<ExpenseModel.Expense>> {
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
            val currencyMap = expense.get("currency") as Map<String, String>

            ExpenseModel.Expense(
                id = expense.id,
                name = expense.get("name") as String,
                type = ExpenseModel.ExpenseType.valueOf(expense.get("type") as String),
                amount = defaultCurrency?.let { convertCurrency(BigDecimal(amount), currencyMap["code"]!!, defaultCurrency.code!!)} ?: BigDecimal(amount),
                date = timestamp.toDate(),
                currency = defaultCurrency?.let { defaultCurrency } ?: Currency(code = currencyMap["code"], name = currencyMap["name"], symbol = currencyMap["symbol"])
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
            val currencyMap = expense.get("currency") as Map<String, String>
            val expenseData = ExpenseModel.Expense(
                id = expense.id,
                name = expense.get("name") as String,
                type = ExpenseModel.ExpenseType.valueOf(expense.get("type") as String),
                amount = BigDecimal(amount),
                date = timestamp.toDate(),
                currency = Currency(code = currencyMap["code"], name = currencyMap["name"], symbol = currencyMap["symbol"])
            )
            return ResponseModel.ResponseWithData.Success(expenseData)
        } catch (e: Exception) {
            return ResponseModel.ResponseWithData.Failure(
                error = e.message ?: "Error getting expense with id"
            )
        }
    }

    suspend fun getExpense(expenseId: String): Flow<ResponseModel.ResponseWithData<ExpenseModel.Expense>> {
        return flow {
            emit(getExpenseData(expenseId))
        }.flowOn(Dispatchers.IO)
    }

    fun convertCurrency(
        amount: BigDecimal,
        fromCurrency: String,
        toCurrency: String
    ): BigDecimal {
        if (fromCurrency == toCurrency) {
            return amount
        }
        val currencyToUsdConversionRates = mapOf(
            "USD" to 1.0,
            "EUR" to 1.13,   // Example conversion rate from Euro to USD
            "GBP" to 1.32,   // Example conversion rate from British Pound to USD
            "JPY" to 0.0092, // Example conversion rate from Japanese Yen to USD
            "AUD" to 0.75,   // Example conversion rate from Australian Dollar to USD
            "CAD" to 0.79,   // Example conversion rate from Canadian Dollar to USD
            "CHF" to 1.07,   // Example conversion rate from Swiss Franc to USD
            "CNY" to 0.16,   // Example conversion rate from Chinese Yuan to USD
            "SEK" to 0.12,   // Example conversion rate from Swedish Krona to USD
            "NZD" to 0.71,   // Example conversion rate from New Zealand Dollar to USD
            "KRW" to 0.0009, // Example conversion rate from South Korean Won to USD
            "SGD" to 0.74,   // Example conversion rate from Singapore Dollar to USD
            "NOK" to 0.11,   // Example conversion rate from Norwegian Krone to USD
            "MXN" to 0.05,   // Example conversion rate from Mexican Peso to USD
            "INR" to 0.014,  // Example conversion rate from Indian Rupee to USD
            "RUB" to 0.013,  // Example conversion rate from Russian Ruble to USD
            "ZAR" to 0.066,  // Example conversion rate from South African Rand to USD
            "BRL" to 0.18,   // Example conversion rate from Brazilian Real to USD
            "HKD" to 0.13,   // Example conversion rate from Hong Kong Dollar to USD
            "IDR" to 0.000070, // Example conversion rate from Indonesian Rupiah to USD
            "TRY" to 0.12,   // Example conversion rate from Turkish Lira to USD
            // Add more currencies and their conversion rates as needed
        )
        val currencyFromUsdConversionRates = mapOf(
            "USD" to 1.0,
            "EUR" to 0.885,   // Example conversion rate from USD to Euro
            "GBP" to 0.758,   // Example conversion rate from USD to British Pound
            "JPY" to 108.7,   // Example conversion rate from USD to Japanese Yen
            "AUD" to 1.33,    // Example conversion rate from USD to Australian Dollar
            "CAD" to 1.26,    // Example conversion rate from USD to Canadian Dollar
            "CHF" to 0.937,   // Example conversion rate from USD to Swiss Franc
            "CNY" to 6.38,    // Example conversion rate from USD to Chinese Yuan
            "SEK" to 8.44,    // Example conversion rate from USD to Swedish Krona
            "NZD" to 1.41,    // Example conversion rate from USD to New Zealand Dollar
            "KRW" to 1120.0,  // Example conversion rate from USD to South Korean Won
            "SGD" to 1.35,    // Example conversion rate from USD to Singapore Dollar
            "NOK" to 9.13,    // Example conversion rate from USD to Norwegian Krone
            "MXN" to 20.37,   // Example conversion rate from USD to Mexican Peso
            "INR" to 74.6,    // Example conversion rate from USD to Indian Rupee
            "RUB" to 75.9,    // Example conversion rate from USD to Russian Ruble
            "ZAR" to 15.2,    // Example conversion rate from USD to South African Rand
            "BRL" to 5.55,    // Example conversion rate from USD to Brazilian Real
            "HKD" to 7.77,    // Example conversion rate from USD to Hong Kong Dollar
            "IDR" to 14203.0, // Example conversion rate from USD to Indonesian Rupiah
            "TRY" to 8.07,    // Example conversion rate from USD to Turkish Lira
            // Add more currencies and their conversion rates as needed
        )
        if (currencyToUsdConversionRates.containsKey(fromCurrency) and currencyFromUsdConversionRates.containsKey(
                toCurrency
            )
        ) {
            return amount * BigDecimal(currencyToUsdConversionRates[fromCurrency]!!) * BigDecimal(
                currencyFromUsdConversionRates[toCurrency]!!
            )
        }
        return BigDecimal(0.00)

//        val client = OkHttpClient()
//        val url = "https://v6.exchangeratesapi.io/latest?base=${expense.currencyCode}&symbols=$defaultCurrency"
//        val request = Request.Builder()
//            .url(url)
//            .build()
//
//        client.newCall(request).enqueue(object : Callback {
//            override fun onFailure(call: Call, e: IOException) {
//                // Handle failure
//                println("Failed to fetch exchange rate: ${e.message}")
//            }
//
//            override fun onResponse(call: Call, response: Response) {
//                response.body?.let {
//                    val responseBody = it.string()
//                    val jsonObject = JSONObject(responseBody)
//                    val rates = jsonObject.getJSONObject("rates")
//                    val exchangeRate = rates.getDouble(defaultCurrency)
//                    val convertedAmount = amount * exchangeRate
//                    callback(convertedAmount)
//                }
//            }
//        })
    }

}