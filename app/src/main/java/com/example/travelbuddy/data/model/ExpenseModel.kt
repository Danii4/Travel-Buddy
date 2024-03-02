package com.example.travelbuddy.data.model

import androidx.compose.material.icons.Icons
import androidx.compose.ui.graphics.Color
import com.example.travelbuddy.R
import java.time.LocalDate

class ExpenseModel {
    enum class ExpenseType(val stringValue: String, val colour: Color?, val icon: Int) {
        FLIGHT("Flight", Color(0xFFE5F0C8), R.drawable.baseline_flight_24),
        FOOD("Food", Color(0xFFFBEECC), R.drawable.baseline_restaurant_24),
        ACCOMMODATION("Accommodation", Color(0xFFFFE6DB), R.drawable.baseline_hotel_24),
        TRANSPORTATION("Transportation", Color(0xFFFFE6DB), R.drawable.baseline_directions_car_24),
        ENTERTAINMENT("Entertainment", Color(0xFFFFE6DB), R.drawable.baseline_attractions_24),
        SHOPPING("Shopping", Color(0xFFFFE6DB), R.drawable.baseline_shopping_bag_24),
        MISCELLANEOUS("Miscellaneous", Color(0xFFFFE6DB), R.drawable.baseline_pending_24);

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
        val amount: Double,
        val date: LocalDate
    )

}
