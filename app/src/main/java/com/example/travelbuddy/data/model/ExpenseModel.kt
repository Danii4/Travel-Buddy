package com.example.travelbuddy.data.model

import androidx.compose.ui.graphics.Color

class ExpenseModel {
}
enum class ExpenseType(val stringValue: String, val colour: Color?) {
    FLIGHT("Flight", Color(0xFFE5F0C8)),
    FOOD("Food", Color(0xFFFBEECC)),
    ACCOMMODATION("Accommodation", Color(0xFFFFE6DB)),
    TRANSPORTATION("Transportation", Color(0xFFFFE6DB)),
    ENTERTAINMENT("Entertainment", Color(0xFFFFE6DB)),
    SHOPPING("Shopping", Color(0xFFFFE6DB)),
    MISCELLANEOUS("Miscellaneous", null);

    companion object{
        fun from(type : String) : ExpenseType {
            return when (type) {
                FLIGHT.stringValue -> FLIGHT
                FOOD.stringValue -> FOOD
                ACCOMMODATION.stringValue -> ACCOMMODATION
                TRANSPORTATION.stringValue -> TRANSPORTATION
                ENTERTAINMENT.stringValue -> ENTERTAINMENT
                SHOPPING.stringValue -> SHOPPING
                else -> MISCELLANEOUS
            }
        }
    }
}
data class Expense(
    val expenseId: String = "",
    val expenseType: ExpenseType,
    val cost: Double,
)