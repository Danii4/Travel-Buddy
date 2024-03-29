package com.example.travelbuddy.data.model

import com.example.travelbuddy.util.Money

class TripModel {
    data class Trip(
        val id: String = "",
        val name: String = "",
        var budgets: MutableMap<ExpenseModel.ExpenseType, Money>,
        var totalExpenses: MutableMap<ExpenseModel.ExpenseType, Money>,
        var expensesList: List<String>,
        var destinationList: List<String>
    )
}