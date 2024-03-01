package com.example.travelbuddy.expenses.model

import com.example.travelbuddy.data.model.ExpenseModel
import java.util.UUID

class ExpensesModel {
    enum class FilterName(val uiName : String, val dbFieldName : String) {
        Type("Type", "type"),
        Trip("Trip", "tripId"),
    }

    data class ExpensesViewState(
        val expenseList: List<ExpenseModel.Expense> = emptyList(),
        val filterList: List<Filter> = emptyList(),
        val isLoading: Boolean = false
    )

    data class Filter(
        val id: UUID = UUID.randomUUID(),
        val name: FilterName,
        val itemsList: List<String>,
        val selectedItem: String?
    )

}