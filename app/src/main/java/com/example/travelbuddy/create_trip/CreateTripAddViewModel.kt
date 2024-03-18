package com.example.travelbuddy.create_trip

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.travelbuddy.Screen

class CreateTripAddViewModel: ViewModel()  {
    fun navigateToCreateTripAdd(navController: NavController) {
        navController.navigate(Screen.TripAdd.route)
    }
}