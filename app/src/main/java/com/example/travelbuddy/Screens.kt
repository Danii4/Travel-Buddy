package com.example.travelbuddy

sealed class Screen(val route: String) {

    // Auth Screens
    object InitialAuthScreen: Screen("initial-auth-screen")
    object Login: Screen("login")
    object Signup: Screen("signup")
    object ResetPassword: Screen("reset-password")

    // Main App Screens
    object Home: Screen("home")
    object Trips: Screen("trip")
    object TripAdd: Screen("trip-add")
    object Expenses: Screen("expenses")
    object LanguageTranslation: Screen("language-translation")
    object UnitConversion: Screen("unit-conversion")
    object AddEditExpense: Screen("add-edit-expense")
}