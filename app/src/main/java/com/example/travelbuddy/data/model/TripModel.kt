package com.example.travelbuddy.data.model

import com.example.travelbuddy.util.Money
import java.math.BigDecimal

class TripModel {
    data class Trip(
        val id: String = "",
        val name: String = "",
        var budgets: MutableMap<ExpenseModel.ExpenseType, BigDecimal>? = null,
        var expensesList: List<String>? = null,
        var destinationList: List<String>? = null,
        var defaultCurrency: String = "CAD"
    )
}