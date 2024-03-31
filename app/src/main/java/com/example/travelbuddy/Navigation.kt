package com.example.travelbuddy

import HomeScreen
import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.travelbuddy.components.NavigationDrawerWrapper
import com.example.travelbuddy.expenses.add_edit_expense.views.AddEditExpenseView
import com.example.travelbuddy.expenses.views.ExpensesView
import com.example.travelbuddy.firebaseauth.screens.InitialAuthScreen
import com.example.travelbuddy.firebaseauth.screens.LoginScreen
import com.example.travelbuddy.firebaseauth.screens.ResetPasswordScreen
import com.example.travelbuddy.firebaseauth.screens.SignupScreen
import com.example.travelbuddy.itinerary.views.ItineraryView
import com.example.travelbuddy.languageTranslation.views.TranslationScreen
import com.example.travelbuddy.trips.views.TripsView
import com.example.travelbuddy.unit_conversion.views.UnitConversionScreen

@SuppressLint("NewApi")
@RequiresApi(Build.VERSION_CODES.O)
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
        composable(Screen.Home.route) {
            NavigationDrawerWrapper(
                navController = navController,
                children = { HomeScreen() },
                itemIndex = Screen.Home.drawerItem
            )
        }
        composable(route = Screen.Expenses.route + "?tripId={tripId}",
            arguments = listOf(navArgument("tripId") {
                type = NavType.StringType
                nullable = true
            })
        ) {
            ExpensesView()
        }
        composable(Screen.LanguageTranslation.route) {
            NavigationDrawerWrapper(
                navController = navController,
                children = { TranslationScreen() },
                itemIndex = Screen.LanguageTranslation.drawerItem
            )
        }
        composable(Screen.Trips.route) {
            NavigationDrawerWrapper(
                navController = navController,
                children = { TripsView(navController = navController) },
                itemIndex = Screen.Trips.drawerItem
            )
        }
        composable(Screen.UnitConversion.route) {
            NavigationDrawerWrapper(
                navController = navController,
                children = { UnitConversionScreen() },
                itemIndex = Screen.UnitConversion.drawerItem
            )
        }
        composable(
            route = Screen.AddEditExpense.route + "?expenseId={expenseId}&tripId={tripId}",
            arguments = listOf(navArgument("expenseId") {
                type = NavType.StringType
                nullable = true
            },
                navArgument("tripId") {
                    type = NavType.StringType
                    nullable = true
                })
        ) {
            AddEditExpenseView()
        }

        composable(
            route = Screen.Itinerary.route + "?destinationId={destinationId}",
            arguments = listOf(
                navArgument("destinationId") {
                    type = NavType.StringType
                    nullable = true
                }
            )
        ) {
            ItineraryView()
        }
    }
}