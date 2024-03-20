package com.example.travelbuddy.create_trip

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.travelbuddy.Screen
import com.example.travelbuddy.data.DestinationRepositoryImpl
import com.example.travelbuddy.data.model.DestinationModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

@HiltViewModel
class CreateTripAddViewModel @Inject constructor(
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