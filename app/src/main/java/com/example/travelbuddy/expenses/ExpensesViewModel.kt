package com.example.travelbuddy.expenses

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travelbuddy.NavWrapper
import com.example.travelbuddy.Screen
import com.example.travelbuddy.data.ExpenseRepository
import com.example.travelbuddy.data.model.ExpenseModel
import com.example.travelbuddy.data.model.TripModel
import com.example.travelbuddy.expenses.model.ExpensesModel
import com.example.travelbuddy.repository.TripRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import java.math.BigDecimal
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class ExpensesViewModel @Inject constructor(
    private val expenseRepository: ExpenseRepository,
    private val tripRepository: TripRepository,
    private val navWrapper: NavWrapper,
    savedStateHandle: SavedStateHandle

) : ViewModel() {
    private val _state = MutableStateFlow(ExpensesModel.ExpensesViewState())
    val state: StateFlow<ExpensesModel.ExpensesViewState>
        get() = _state
    private val tripId: String? = savedStateHandle["tripId"]

    private val expensesList: MutableStateFlow<List<ExpenseModel.Expense>> =
        MutableStateFlow(listOf())

    private val budgets: MutableStateFlow<MutableMap<ExpenseModel.ExpenseType, BigDecimal>> =
        MutableStateFlow(mutableMapOf())

    private val trip: MutableStateFlow<TripModel.Trip> = MutableStateFlow(_state.value.trip)

    init {
        viewModelScope.launch {
            combine(
                expensesList,
                budgets,
                trip,
            ) { expensesList: List<ExpenseModel.Expense>,
                budgets: Map<ExpenseModel.ExpenseType, BigDecimal>,
                trip: TripModel.Trip ->
                ExpensesModel.ExpensesViewState(
                    expensesList = expensesList,
                    budgets = budgets,
                    trip = trip
                )
            }.collect {
                _state.value = it
            }

        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getData() {
        viewModelScope.launch {
            combine(
                expenseRepository.getExpenses(tripId!!),
                tripRepository.getTrips(tripId)
            ) { expenses, tripResponse ->
                Pair(expenses, tripResponse)
            }.collect { (expenses, tripResponse) ->
                expenses.data?.let {
                    expensesList.value = it
                } ?: run {
                    Log.d(
                        "Firebase Error",
                        "Error initializing ExpensesViewModel while  getting expenses from Expense Repository"
                    )
                }
                tripResponse.data?.let {
                    trip.value = it[0]
                    budgets.value = it[0].budgets!!
                } ?: run {
                    Log.d(
                        "Firebase Error",
                        "Error initializing ExpensesViewModel while getting trip from Trip Repository"
                    )
                }
            }
        }
    }

    init {
        getData()
    }

    fun navigateToAddEditExpense(expenseId: String = "") {
        navWrapper.getNavController()
            .navigate(Screen.AddEditExpense.route + "?expenseId=${expenseId}&tripId=${tripId}")
    }

    fun getTotalExpenses(expenseType: ExpenseModel.ExpenseType): Any {
        var totalAmount = BigDecimal(0.00)
        for (expense in expensesList.value) {
            if (expense.type == expenseType) {
                totalAmount += convertCurrency(expense, trip.value.defaultCurrency)
            }
        }
        return totalAmount
    }

    private fun convertCurrency(
        expense: ExpenseModel.Expense,
        defaultCurrency: String
    ): BigDecimal {
        if (expense.currencyCode == defaultCurrency) {
            return expense.amount
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
        if (currencyToUsdConversionRates.containsKey(expense.currencyCode) and currencyFromUsdConversionRates.containsKey(
                defaultCurrency
            )
        ) {
            return expense.amount * BigDecimal(currencyToUsdConversionRates[expense.currencyCode]!!) * BigDecimal(
                currencyFromUsdConversionRates[defaultCurrency]!!
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

    fun navigateBack() {
        navWrapper.getNavController().navigateUp()
    }
}