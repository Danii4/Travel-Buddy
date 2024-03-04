package com.example.travelbuddy.create_trip

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.travelbuddy.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
@HiltViewModel
class CreateTripAddViewModel: ViewModel()  {
    fun navigateToCreateTripAdd(navController: NavController) {
        navController.navigate(Screen.TripAdd.route)
    }
}