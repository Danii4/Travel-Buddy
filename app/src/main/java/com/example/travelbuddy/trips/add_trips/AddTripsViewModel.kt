package com.example.travelbuddy.trips.add_trips

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travelbuddy.NavWrapper
import com.example.travelbuddy.Screen
import com.example.travelbuddy.data.DestinationRepositoryImpl
import com.example.travelbuddy.data.TripRepositoryImpl
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
    private val destinationRepositoryImpl: DestinationRepositoryImpl,
    private val tripRepositoryImpl: TripRepositoryImpl,
    private val navWrapper: NavWrapper
) : ViewModel() {
    private val _state = MutableStateFlow(AddTripsPageModel.AddTripViewState())
    val state: StateFlow<AddTripsPageModel.AddTripViewState>
        get() = _state

    private val destinationList: MutableStateFlow<List<DestinationModel.Destination>> = MutableStateFlow(listOf())
    private val tripName: MutableStateFlow<String> = MutableStateFlow(_state.value.tripName)

    init {
        viewModelScope.launch {
            combine(destinationList, tripName) {
                    destinationList: List<DestinationModel.Destination>,
                    tripName: String ->
                AddTripsPageModel.AddTripViewState(
                    destinationList = destinationList,
                    tripName = tripName,
                )
            }.collect {
                _state.value = it
            }
        }
    }


    fun addDestination(destination: DestinationModel.Destination) {
        destinationList.value += destination
    }

    fun deleteDestination(destination: DestinationModel.Destination){
        destinationList.value -= destination
    }

    fun setTripName(name: String){
        tripName.value = name
    }

    fun submitDestination(){
        viewModelScope.launch {
            val destIdList = mutableListOf<String>()
            destinationList.value.forEach { destination ->
                val resp = destinationRepositoryImpl.addDestination(destination)
                destIdList.add(resp?.data.toString())
            }
            val tripId = tripRepositoryImpl.addTrip(
                tripName= tripName.value,
                destIdList = destIdList,
            )
            tripRepositoryImpl.addTripIdToUser(tripId.data.toString())
        }
    }

    fun navigateToTrips() {
        navWrapper.getNavController().navigate(Screen.Trips.route)
    }
}