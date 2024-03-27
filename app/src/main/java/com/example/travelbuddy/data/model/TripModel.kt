package com.example.travelbuddy.data.model

class TripModel {
    data class Trip(
        val id: String = "",
        val name: String = "",
        var budgets: MutableMap<ExpenseModel.ExpenseType, Double>,
        var totalExpenses: MutableMap<ExpenseModel.ExpenseType, Double>,
        var expensesList: List<String>,
        var destinationList: List<String>
    )
}