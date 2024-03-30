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
import com.example.travelbuddy.expenses.model.ExpensesModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class ExpensesViewModel @Inject constructor(
    private val expenseRepository: ExpenseRepository,
    private val navWrapper: NavWrapper,
    savedStateHandle: SavedStateHandle

) : ViewModel() {
    private val _state = MutableStateFlow(ExpensesModel.ExpensesViewState())
    val state: StateFlow<ExpensesModel.ExpensesViewState>
        get() = _state
//    private val tripId : String? = savedStateHandle["tripId"]
    private val tripId = "OREAgUxxBdNtecnL22tS"

    private val expensesList: MutableStateFlow<List<ExpenseModel.Expense>> =
        MutableStateFlow(listOf())
    private val isLoading : MutableStateFlow<Boolean>  = MutableStateFlow(false )
//    private val isLoading: MutableStateFlow<Boolean> = MutableStateFlow(_state.value.isLoading)
//    private val isDefaultCurrency: MutableStateFlow<Boolean> =
//        MutableStateFlow(_state.value.isDefaultCurrency)

    init {
        viewModelScope.launch {
            combine(
                expensesList,
                isLoading,
            ) { expensesList: List<ExpenseModel.Expense>,
                isLoading: Boolean ->
                ExpensesModel.ExpensesViewState(
                    expensesList = expensesList,
                    isLoading = isLoading
                )
            }.collect {
                _state.value = it
            }

        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getData() {
        viewModelScope.launch {
            expenseRepository.getExpenses(tripId).collect { expenses -> expenses.data?.let {
                expensesList.value = it
            } ?: run {
                Log.d("Error", "Error getting expense data")
            }
            }
        }

}
    init {
        getData()
    }

fun navigateToAddEditExpense(expenseId: String) {
    navWrapper.getNavController().navigate(Screen.AddEditExpense.route + "?expenseId=${expenseId}")
}
}