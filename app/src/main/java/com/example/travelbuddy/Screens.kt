package com.example.travelbuddy

sealed class Screen(val route: String, var drawerItem: Int = 0) {

    // Auth Screens
    object InitialAuthScreen: Screen("initial-auth-screen")
    object Login: Screen("login")
    object Signup: Screen("signup")
    object ResetPassword: Screen("reset-password")

    // Main App Screens
    object Home: Screen("home", 0)
    object FAQ: Screen("faq", 0)
    object Trips: Screen("trip", 1)
    object TripAdd: Screen("trip-add", 1)
    object Expenses: Screen("expenses", 2)

    object Itinerary: Screen(route = "itinerary")
    object AddEditExpense: Screen("add-edit-expense", 2)

    object LanguageTranslation: Screen("language-translation", 3)
    object UnitConversion: Screen("unit-conversion", 4)
}