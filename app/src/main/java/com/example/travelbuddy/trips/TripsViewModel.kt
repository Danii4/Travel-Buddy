package com.example.travelbuddy.trips

import androidx.lifecycle.ViewModel
import com.example.travelbuddy.NavWrapper
import com.example.travelbuddy.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TripsViewModel @Inject constructor(
    private val navWrapper: NavWrapper
) : ViewModel() {
    fun navigateToTrips() {
        navWrapper.getNavController().navigate(Screen.Trips.route)
    }
}