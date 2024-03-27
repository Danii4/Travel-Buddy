package com.example.travelbuddy.expenses

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.Navigator
import com.example.travelbuddy.NavWrapper
import com.example.travelbuddy.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ExpensesViewModel @Inject constructor(
    private val navWrapper: NavWrapper
) : ViewModel() {
    fun navigateToAddEditExpense() {
        navWrapper.getNavController().navigate(Screen.AddEditExpense.route)
    }
}