package com.example.travelbuddy.expenses.model

import com.example.travelbuddy.data.model.ExpenseModel
import java.math.BigDecimal

class ExpensesModel {
    data class ExpensesViewState(
        val expensesList: List<ExpenseModel.Expense> = emptyList(),
        val budgets: Map<ExpenseModel.Expense, BigDecimal> = emptyMap(),
        val isLoading: Boolean = false,
    )
}