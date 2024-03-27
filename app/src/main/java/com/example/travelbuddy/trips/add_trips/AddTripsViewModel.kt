package com.example.travelbuddy.trips.add_trips

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.travelbuddy.Screen
import com.example.travelbuddy.data.DestinationRepositoryImpl
import com.example.travelbuddy.data.model.DestinationModel
import com.example.travelbuddy.trips.add_trips.model.AddTripsPageModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddTripsViewModel @Inject constructor(
    private val destinationRepositoryImpl: DestinationRepositoryImpl
) : ViewModel() {
    private val _state = MutableStateFlow(AddTripsPageModel.AddTripViewState())
    val state: StateFlow<AddTripsPageModel.AddTripViewState>
        get() = _state

    private val destinationList: MutableStateFlow<List<DestinationModel.Destination>> = MutableStateFlow(listOf())
    private val dummy : MutableStateFlow<Boolean> = MutableStateFlow(_state.value.dummy)

    init {
        viewModelScope.launch {
            combine(destinationList, dummy) {
                    destinationList: List<DestinationModel.Destination>,
                    dummy: Boolean ->
                AddTripsPageModel.AddTripViewState(
                    destinationList = destinationList,
                    dummy = dummy,
                )
            }.collect {
                _state.value = it
            }
        }
    }


    fun addDestination(destination: DestinationModel.Destination) {
//        viewModelScope.launch {
//            destinationRepositoryImpl.addDestination(
//                destination
//            )
//        }
        destinationList.value += destination
    }

    fun deleteDestination(destination: DestinationModel.Destination){
        destinationList.value -= destination
    }
    fun navigateToCreateTripAdd(navController: NavController) {
        navController.navigate(Screen.TripAdd.route)
    }
}