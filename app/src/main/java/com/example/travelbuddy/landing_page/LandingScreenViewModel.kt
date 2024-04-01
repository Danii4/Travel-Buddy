package com.example.travelbuddy.landing_page.views

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travelbuddy.NavWrapper
import com.example.travelbuddy.Screen
import com.example.travelbuddy.landing_page.model.LandingScreenPageModel
import com.example.travelbuddy.repository.AuthRepository
import com.example.travelbuddy.repository.TripRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LandingScreenViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val tripRepository: TripRepository,
    private val navWrapper: NavWrapper
) : ViewModel() {

    private val _state = MutableStateFlow(LandingScreenPageModel.LandingScreenViewState())
    val state: StateFlow<LandingScreenPageModel.LandingScreenViewState>
        get() = _state

    private val tripCount: MutableStateFlow<Int> = MutableStateFlow(_state.value.tripCount)
    private val userName: MutableStateFlow<String> = MutableStateFlow(_state.value.userName)
    private val lastTripName: MutableStateFlow<String> = MutableStateFlow(_state.value.lastTripName)


    init {
        viewModelScope.launch {
            combine(
                tripCount,
                userName,
                lastTripName,
            ) { tripCount: Int,
                userName: String,
                lastTripName: String ->
                LandingScreenPageModel.LandingScreenViewState(
                    tripCount = tripCount,
                    userName = userName,
                    lastTripName = lastTripName
                )
            }.collect {
                _state.value = it
            }
        }
    }

    private fun getData() {
        viewModelScope.launch {
            val tripsList = authRepository.getTripsList()
            tripCount.value = tripsList?.size ?: 0
            userName.value = authRepository.getUserName().toString()
            lastTripName.value = tripRepository.getTripName(tripsList
                ?.get(tripsList.size - 1) ?: "").data.toString()
        }
    }

    init {
        getData()
    }

    fun navigateToTrips() {
        navWrapper.getNavController().navigate(Screen.Trips.route)
    }

    fun navigateToCurrency() {
        navWrapper.getNavController().navigate(Screen.UnitConversion.route)
    }

    fun navigateToTranslate() {
        navWrapper.getNavController().navigate(Screen.LanguageTranslation.route)
    }

    fun navigateToFAQ() {
        navWrapper.getNavController().navigate(Screen.FAQ.route)
    }
}