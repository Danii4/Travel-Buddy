package com.example.travelbuddy.expenses.model

class ExpensesModel {
    enum class FilterName(val uiName : String, val dbFieldName : String) {
        Type("Type", "type"),
        Trip("Trip", "tripId"),
    }

    data class TransactionViewState(
        val transactionList: List<ExpensesModel.> = emptyList(),
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