package com.example.travelbuddy

sealed class Screen(val route: String) {
    object Home: Screen("home")
    object TripPlanning: Screen("trip-planning")
    object Expenses: Screen("expenses")
    object LanguageTranslation: Screen("language-translation")
    object UnitConversion: Screen("unit-conversion")
}