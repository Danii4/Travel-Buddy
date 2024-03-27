package com.example.travelbuddy.trips.add_trips

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.travelbuddy.Screen
import com.example.travelbuddy.data.DestinationRepositoryImpl
import com.example.travelbuddy.data.model.DestinationModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddTripsViewModel @Inject constructor(
    private val destinationRepositoryImpl: DestinationRepositoryImpl
) : ViewModel() {
    fun submitDestination(destination: DestinationModel.Destination) {
        viewModelScope.launch {
            destinationRepositoryImpl.addDestination(
                destination
            )
        }
    }
    fun navigateToCreateTripAdd(navController: NavController) {
        navController.navigate(Screen.TripAdd.route)
    }
}