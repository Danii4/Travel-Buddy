package com.example.travelbuddy.expenses.model

import com.example.travelbuddy.data.model.ExpenseModel
import com.example.travelbuddy.data.model.TripModel
import java.math.BigDecimal

class ExpensesModel {
    data class ExpensesViewState(
        val expensesList: List<ExpenseModel.Expense> = emptyList(),
        val budgets: Map<ExpenseModel.ExpenseType, BigDecimal> = emptyMap(),
        val trip: TripModel.Trip = TripModel.Trip(),
        val defaultCurrency: String = ""
    )
}