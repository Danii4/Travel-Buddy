package com.example.travelbuddy.expenses.add_edit_expense

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.travelbuddy.NavWrapper
import com.example.travelbuddy.Screen
import com.example.travelbuddy.data.ExpenseRepository
import com.example.travelbuddy.data.model.ExpenseModel
import com.example.travelbuddy.expenses.add_edit_expense.model.AddEditExpenseModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AddEditExpenseViewModel @Inject constructor(
    private val expenseRepository: ExpenseRepository,
    private val navWrapper: NavWrapper
) : ViewModel() {

//    private val _state = MutableStateFlow(AddEditExpenseModel.AddEditExpenseViewState())
//    val state: StateFlow<AddEditExpenseModel.AddEditExpenseViewState>
//        get() = _state

//    private val expenseName : MutableStateFlow<String> = MutableStateFlow("")
//    private val selectedExpense : MutableStateFlow<ExpenseModel.Expense?> = MutableStateFlow(_state.value.selectedExpense)
//    private val marketsList : MutableStateFlow<List<ExpenseModel.Expense?>> = MutableStateFlow(emptyList())
//    fun setExpenseName(it: Any) {
//
//    }

    fun submitExpense(expense: ExpenseModel.Expense) {
        viewModelScope.launch {
            expenseRepository.addExpense(expense)
        }
    }
    fun navigateToExpenses() {
        navWrapper.getNavController().navigate(Screen.Expenses.route)
    }
}