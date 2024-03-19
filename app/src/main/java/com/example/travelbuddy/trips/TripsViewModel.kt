package com.example.travelbuddy.trips

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.travelbuddy.Screen

class TripsViewModel: ViewModel() {
    fun navigateToTrips(navController: NavController){
        navController.navigate(Screen.Trips.route)
    }
}