package com.example.travelbuddy.data.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Attractions
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Flight
import androidx.compose.material.icons.filled.Hotel
import androidx.compose.material.icons.filled.Pending
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.toUpperCase
import java.math.BigDecimal
import java.util.Date

class ExpenseModel {
    enum class ExpenseType(val stringValue: String, val displayValue: String, val colour: Color?, val icon: ImageVector) {
        FLIGHT("FLIGHT","Flight", Color(0xFFE5F0C8), Icons.Default.Flight),
        FOOD("FOOD","Food", Color(0xFFFBEECC), Icons.Default.Restaurant),
        ACCOMMODATION("ACCOMMODATION","Accommodation", Color(0xFFFFE6DB), Icons.Default.Hotel),
        TRANSPORTATION("TRANSPORTATION","Transportation", Color(0xFFFFE6DB), Icons.Default.DirectionsCar),
        ENTERTAINMENT("ENTERTAINMENT","Entertainment", Color(0xFFFFE6DB), Icons.Default.Attractions),
        SHOPPING("SHOPPING", "Shopping", Color(0xFFFFE6DB), Icons.Default.ShoppingBag),
        MISCELLANEOUS("MISCELLANEOUS","Miscellaneous", Color(0xFFFFE6DB), Icons.Default.Pending);

        companion object{
            fun from(stringValue : String) : ExpenseType {
                return when (stringValue) {
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
        val id: String = "",
        val name: String = "",
        val type: ExpenseType,
        val amount: BigDecimal,
        val date: Date,
        val currency: Currency
    )

}
