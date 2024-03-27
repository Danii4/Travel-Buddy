package com.example.travelbuddy.trips

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.travelbuddy.Screen
import com.example.travelbuddy.data.TripRepository
import com.example.travelbuddy.data.model.DestinationModel
import com.example.travelbuddy.data.model.TripModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel

class TripsViewModel @Inject constructor(
    private val TripRepository: TripRepository,
//    private val navWrapper: NavWrapper
): ViewModel() {

    fun submitTrip(trip: TripModel.Trip) {
        viewModelScope.launch {
            TripRepository.addTrip(
                trip
            )
        }
    }
    fun navigateToTrips(navController: NavController){
        navController.navigate(Screen.Trips.route)
    }
}