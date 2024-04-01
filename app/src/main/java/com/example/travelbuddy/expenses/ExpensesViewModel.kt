package com.example.travelbuddy.expenses

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.ui.graphics.Color
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

    private val isDefaultCurrency: MutableStateFlow<Boolean> = MutableStateFlow(_state.value.isDefaultCurrency)

    init {
        viewModelScope.launch {
            combine(
                expensesList,
                budgets,
                trip,
                isDefaultCurrency
            ) { expensesList: List<ExpenseModel.Expense>,
                budgets: Map<ExpenseModel.ExpenseType, BigDecimal>,
                trip: TripModel.Trip,
                isDefaultCurrency: Boolean ->
                ExpensesModel.ExpensesViewState(
                    expensesList = expensesList,
                    budgets = budgets,
                    trip = trip,
                    isDefaultCurrency = isDefaultCurrency
                )
            }.collect {
                _state.value = it
            }

        }
    }

    init {
        viewModelScope.launch {
            isDefaultCurrency.collect {
                getData()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getData() {
        viewModelScope.launch {
            combine(
                expenseRepository.getExpenses(tripId!!, if (isDefaultCurrency.value) trip.value.defaultCurrency else null),
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

    fun getTotalExpenses(expenseType: ExpenseModel.ExpenseType): BigDecimal {
        var totalAmount = BigDecimal(0.00)
        for (expense in expensesList.value) {
            if (expense.type == expenseType) {
                totalAmount += expenseRepository.convertCurrency(expense.amount, expense.currency.code!!, trip.value.defaultCurrency.code!!)
            }
        }
        return totalAmount
    }
    fun navigateBack() {
        navWrapper.getNavController().navigateUp()
    }

    fun getProgress(expenseType: ExpenseModel.ExpenseType, amount: BigDecimal): Float {
        return if (amount.compareTo(BigDecimal.ZERO) != 0) (getTotalExpenses(expenseType) / amount).toFloat() else 0.0f
    }

    fun getProgressColour(progress: Float): Color {
        return when {
            progress >= 0.75 -> Color.Red
            progress >= 0.50 -> Color.Yellow
            else -> Color.Green
        }
    }
    fun setCurrencyToggle(toggleState: Boolean) {
        isDefaultCurrency.value = toggleState
    }
}