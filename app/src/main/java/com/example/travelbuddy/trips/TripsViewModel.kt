package com.example.travelbuddy.trips

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travelbuddy.NavWrapper
import com.example.travelbuddy.Screen
import com.example.travelbuddy.data.model.TripModel
import com.example.travelbuddy.repository.TripRepository
import com.example.travelbuddy.trips.model.TripsModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class TripsViewModel @Inject constructor(
    private val tripRepository: TripRepository,
    private val navWrapper: NavWrapper,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _state = MutableStateFlow(TripsModel.TripsViewState())
    val state: StateFlow<TripsModel.TripsViewState>
        get() = _state

    private val tripsList: MutableStateFlow<List<TripModel.Trip>> =
        MutableStateFlow(listOf())

    private val isLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)

    init {
        viewModelScope.launch {
            combine(
                tripsList,
                isLoading,
            ) { tripsList: List<TripModel.Trip>,
                isLoading: Boolean ->
                TripsModel.TripsViewState(
                    tripsList = tripsList,
                    isLoading = isLoading
                )
            }.collect {
                _state.value = it
            }

        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getData() {
        viewModelScope.launch {
            tripRepository.getTrips().collect { trips ->
                trips.data?.let {
                    tripsList.value = it
                } ?: run {
                    Log.d("Error", "Error getting trips data")
                }
            }
        }

    }

    init {
        getData()
    }

    fun navigateToTrips() {
        navWrapper.getNavController().navigate(Screen.Trips.route)
    }

    fun navigateToExpense(tripId: String) {
        navWrapper.getNavController().navigate(Screen.Expenses.route + "?tripId=${tripId}")
    }

    fun deleteTrip(tripId: String) {
        Log.d("delete view model", tripId)
        viewModelScope.launch {
            tripRepository.deleteTrip(tripId)
        }
    }
}