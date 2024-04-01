package com.example.travelbuddy.trips.add_trips.model

import com.example.travelbuddy.data.model.DestinationModel
import com.example.travelbuddy.data.model.ExpenseModel
import com.github.nkuppan.country.domain.model.Currency
import java.math.BigDecimal

class AddTripsPageModel {
    data class AddTripViewState(
        val destinationList: List<DestinationModel.Destination> = emptyList(),
        val tripName: String = "",
        val budgets : List<Pair<ExpenseModel.ExpenseType, BigDecimal>> = emptyList(),
        val selectedExpenseTypes : Set<ExpenseModel.ExpenseType> = emptySet(),
        val defaultCurrency : Currency = Currency(code="CAD", symbol = "$"),
        var tripId: String? = null,
        val changeDetected: Boolean = false,
    )
}