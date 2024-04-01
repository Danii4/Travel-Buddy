package com.example.travelbuddy.data.model

import java.math.BigDecimal

class TripModel {
    data class Trip(
        val id: String = "",
        val name: String = "",
        var budgets: MutableMap<ExpenseModel.ExpenseType, BigDecimal>? = null,
        var expensesList: List<String>? = null,
        var destinationList: List<String>? = null,
        var defaultCurrency: Currency = Currency(code = "CAD", "Canadian Dollar", "$")
    )
}