package com.example.travelbuddy.expenses

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.travelbuddy.Screen
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel
class ExpensesViewModel() : ViewModel() {
    fun navigateToAddExpense(navController: NavController) {
        navController.navigate(Screen.AddEditExpense.route)
    }
}