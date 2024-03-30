package com.example.travelbuddy.data.model

import com.example.travelbuddy.util.Money

class TripModel {
    data class Trip(
        val id: String = "",
        val name: String = "",
        var budgets: MutableMap<ExpenseModel.ExpenseType, Money>? = null,
        var totalExpenses: MutableMap<ExpenseModel.ExpenseType, Money>? = null,
        var expensesList: List<String>? = null,
        var destinationList: List<String>? = null
    )
}