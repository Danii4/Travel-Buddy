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

    private val budgets: MutableStateFlow<Map<ExpenseModel.ExpenseType, BigDecimal>> =
        MutableStateFlow(emptyMap())

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
                }?: run {
                    Log.d("Firebase Error", "Error initializing ExpensesViewModel while  getting expenses from Expense Repository")
                }
                tripResponse.data?.let {
                    trip.value = it[0]
                }?: run {
                    Log.d("Firebase Error", "Error initializing ExpensesViewModel while getting trip from Trip Repository")
                }
            }
//            expenseRepository.getExpenses(tripId!!).collect { expenses -> expenses.data?.let {
//                expensesList.value = it
//            } ?: run {
//                Log.d("Error", "Error getting expense data")
//            }
//            }
        }

    }

    init {
        getData()
    }

    fun navigateToAddEditExpense(expenseId: String = "") {
        navWrapper.getNavController()
            .navigate(Screen.AddEditExpense.route + "?expenseId=${expenseId}&tripId=${tripId}")
    }
}