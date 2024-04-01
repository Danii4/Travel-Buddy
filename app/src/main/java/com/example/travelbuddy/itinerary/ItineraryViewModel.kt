package com.example.travelbuddy.itinerary

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travelbuddy.NavWrapper
import com.example.travelbuddy.amadeus_client.AmadeusClient
import com.example.travelbuddy.data.model.ItineraryModel
import com.example.travelbuddy.itinerary.model.ItineraryPageModel
import com.example.travelbuddy.repository.DestinationRepository
import com.example.travelbuddy.repository.ItineraryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class ItineraryViewModel @Inject constructor(
    private val itineraryRepository: ItineraryRepository,
    private val destinationRepository: DestinationRepository,
    private val amadeusClient: AmadeusClient,
    savedStateHandle: SavedStateHandle,
    private val navWrapper: NavWrapper
) : ViewModel() {
    private val _state = MutableStateFlow(ItineraryPageModel.ItineraryViewState())
    val state: StateFlow<ItineraryPageModel.ItineraryViewState>
        get() = _state

    private val destinationId: MutableStateFlow<String?> =
        MutableStateFlow(savedStateHandle["destinationId"])
    private val itineraryList: MutableStateFlow<List<ItineraryModel.Itinerary>> =
        MutableStateFlow(listOf())
    private val itineraryName: MutableStateFlow<String> = MutableStateFlow(_state.value.name)

    init {
        viewModelScope.launch {
            combine(
                itineraryList,
                itineraryName,
                destinationId
            ) { itineraryList: List<ItineraryModel.Itinerary>,
                itineraryName: String,
                destinationId: String? ->
                ItineraryPageModel.ItineraryViewState(
                    itineraryList = itineraryList,
                    name = itineraryName,
                    destinationId = destinationId
                )
            }.collect {
                _state.value = it
            }
        }
    }

    private fun getData() {
        viewModelScope.launch {
            itineraryRepository.getItinerary(destinationId.value).collect { itinerary ->
                itinerary.data?.let {
                    itineraryList.value = it
                } ?: run {
                    Log.d("Error", "Error getting itinerary data")
                }
            }
        }
    }

    init {
        getData()
    }

    @SuppressLint("NewApi")
    fun addItinerary(name: String, time: LocalDateTime?) {
        val itineraryItem = ItineraryModel.Itinerary(
            name = name,
            time = Date.from(time!!.atZone(ZoneId.systemDefault()).toInstant()),
        )
        itineraryList.value += itineraryItem
    }

    fun deleteItinerary(itinerary: ItineraryModel.Itinerary) {
        itineraryList.value -= itinerary
    }

    fun setItineraryName(name: String) {
        itineraryName.value = name
    }

    fun navigateBack() {
        navWrapper.getNavController().navigateUp()
    }

    fun submitItinerary() {
        viewModelScope.launch {
            val itineraryIdList = mutableListOf<String>()
            itineraryList.value.forEach { itinerary ->
                val response = itineraryRepository.addItineraryItem(itinerary)
                itineraryIdList.add(response.data.toString())
            }
            itineraryIdList.forEach { itineraryId ->
                itineraryRepository.updateDestItineraryIds(
                    destId = destinationId.value,
                    itineraryId = itineraryId
                )
            }
            navigateBack()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun generateItinerary(destinationId: String) {
        amadeusClient.startClient()
        viewModelScope.launch {
            val destName = destinationRepository.getDestinationName(destinationId).data.toString()
            val coords = amadeusClient.getCoordinates(destName)
            amadeusClient.getGeoPoint(
                latitude = coords.data?.first,
                longitude = coords.data?.second
            ).collect { generatedItineraryList ->
                generatedItineraryList.let {
                    itineraryList.value = it
                }
            }
        }
    }
}