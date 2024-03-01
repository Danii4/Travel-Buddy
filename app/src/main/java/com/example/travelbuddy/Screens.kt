package com.example.travelbuddy

sealed class Screen(val route: String) {
    object Home: Screen("home")
    object Trips: Screen("trip")
    object Expenses: Screen("expenses")
    object Translation: Screen("translation")
    object UnitConversion: Screen("unit-conversion")
}