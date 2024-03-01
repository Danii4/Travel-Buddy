package com.example.travelbuddy

sealed class Screen(val route: String) {
    object Home: Screen("home")
    object TripPlanning: Screen("trip-planning")
    object Expenses: Screen("expenses")
    object Translation: Screen("translation")
    object UnitConversion: Screen("unit-conversion")
}