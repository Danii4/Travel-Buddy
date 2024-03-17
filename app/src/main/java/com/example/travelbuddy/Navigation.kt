package com.example.travelbuddy

import HomeScreen
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.travelbuddy.create_trip.views.CreateTripAddView
import com.example.travelbuddy.data.Mock
import com.example.travelbuddy.expenses.add_edit_expense.views.AddEditExpenseView
import com.example.travelbuddy.expenses.views.ExpensesView
import com.example.travelbuddy.firebaseauth.screens.InitialAuthScreen
import com.example.travelbuddy.firebaseauth.screens.LoginScreen
import com.example.travelbuddy.firebaseauth.screens.ResetPasswordScreen
import com.example.travelbuddy.firebaseauth.screens.SignupScreen
import com.example.travelbuddy.languageTranslation.TranslationScreen
import com.example.travelbuddy.trips.views.TripsView
import com.example.travelbuddy.unit_conversion.views.UnitConversionScreen

@Composable
fun Navigation(
    loggedIn: Boolean,
    modifier: Modifier,
    navController: NavHostController,
) {
    var start: String = if (!loggedIn) Screen.InitialAuthScreen.route else Screen.Home.route

    return NavHost(navController = navController, startDestination = start, modifier = modifier) {
        // Auth Screens
        composable(Screen.InitialAuthScreen.route) { InitialAuthScreen(navController = navController) }
        composable(Screen.Signup.route) { SignupScreen(navController = navController) }
        composable(Screen.Login.route) { LoginScreen(navController = navController) }
        composable(Screen.ResetPassword.route) { ResetPasswordScreen(navController = navController) }

        // Other Screens
        composable(Screen.Home.route) { HomeScreen() }
        composable(Screen.Expenses.route) { ExpensesView(navController = navController, trip = Mock.trip) }
        composable(Screen.LanguageTranslation.route) { TranslationScreen() }
        composable(Screen.Trips.route) { TripsView(navController) }
        composable(Screen.TripAdd.route) { CreateTripAddView() }
        composable(Screen.UnitConversion.route) { UnitConversionScreen() }
        composable(Screen.AddEditExpense.route) { AddEditExpenseView(navController = navController, trip = Mock.trip) }
    }
}