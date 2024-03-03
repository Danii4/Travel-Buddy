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
import com.example.travelbuddy.R
import java.time.LocalDate

class ExpenseModel {
    enum class ExpenseType(val stringValue: String, val colour: Color?, val icon: ImageVector) {
        FLIGHT("Flight", Color(0xFFE5F0C8), Icons.Default.Flight),
        FOOD("Food", Color(0xFFFBEECC), Icons.Default.Restaurant),
        ACCOMMODATION("Accommodation", Color(0xFFFFE6DB), Icons.Default.Hotel),
        TRANSPORTATION("Transportation", Color(0xFFFFE6DB), Icons.Default.DirectionsCar),
        ENTERTAINMENT("Entertainment", Color(0xFFFFE6DB), Icons.Default.Attractions),
        SHOPPING("Shopping", Color(0xFFFFE6DB), Icons.Default.ShoppingBag),
        MISCELLANEOUS("Miscellaneous", Color(0xFFFFE6DB), Icons.Default.Pending);

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
        val id: String = "",
        val name: String = "",
        val type: ExpenseType,
        val amount: Float,
        val date: LocalDate
    )

}
