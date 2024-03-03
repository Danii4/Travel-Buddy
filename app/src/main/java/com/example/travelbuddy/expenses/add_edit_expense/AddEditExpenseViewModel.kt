package com.example.travelbuddy.expenses.add_edit_expense

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.travelbuddy.Screen
import com.example.travelbuddy.data.model.ExpenseModel
import com.example.travelbuddy.expenses.add_edit_expense.model.AddEditExpenseModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class AddEditExpenseViewModel : ViewModel() {

    private val _state = MutableStateFlow(AddEditExpenseModel.AddEditExpenseViewState())
    val state: StateFlow<AddEditExpenseModel.AddEditExpenseViewState>
        get() = _state

    private val expenseName : MutableStateFlow<String> = MutableStateFlow("")
    private val selectedExpense : MutableStateFlow<ExpenseModel.Expense?> = MutableStateFlow(_state.value.selectedExpense)
    private val marketsList : MutableStateFlow<List<ExpenseModel.Expense?>> = MutableStateFlow(emptyList())
    fun setExpenseName(it: Any) {

    }

    fun navigatetoExpenses(navController: NavController) {
        navController.navigate(Screen.Expenses.route)
    }
}