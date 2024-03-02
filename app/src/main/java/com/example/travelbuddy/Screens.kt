package com.example.travelbuddy

sealed class Screen(val route: String) {
    object Home: Screen("home")
    object TripPlanning: Screen("trip-planning")
    object TripAdd: Screen("trip-add")
    object BudgetTracking: Screen("budget-tracking")
    object Translation: Screen("translation")
    object UnitConversion: Screen("unit-conversion")
}