package com.example.travelbuddy.trips.add_trips

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travelbuddy.NavWrapper
import com.example.travelbuddy.Screen
import com.example.travelbuddy.data.model.DestinationModel
import com.example.travelbuddy.data.model.ExpenseModel
import com.example.travelbuddy.repository.DestinationRepository
import com.example.travelbuddy.repository.TripRepository
import com.example.travelbuddy.trips.add_trips.model.AddTripsPageModel
import com.github.nkuppan.country.domain.model.Currency
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.util.Date
import javax.inject.Inject


@HiltViewModel
class AddTripsViewModel @Inject constructor(
    private val destinationRepository: DestinationRepository,
    private val tripRepository: TripRepository,
    private val navWrapper: NavWrapper
) : ViewModel() {
    private val _state = MutableStateFlow(AddTripsPageModel.AddTripViewState())
    val state: StateFlow<AddTripsPageModel.AddTripViewState>
        get() = _state

    private val destinationList: MutableStateFlow<List<DestinationModel.Destination>> = MutableStateFlow(listOf())
    private val tripName: MutableStateFlow<String> = MutableStateFlow(_state.value.tripName)
    private val tripId: MutableStateFlow<String?> = MutableStateFlow(_state.value.tripId)
    private val changeDetected: MutableStateFlow<Boolean> =
        MutableStateFlow(_state.value.changeDetected)
    private val budgets: MutableStateFlow<List<Pair<ExpenseModel.ExpenseType, BigDecimal>>> =
        MutableStateFlow(
            listOf()
        )
    private val defaultCurrency : MutableStateFlow<Currency> = MutableStateFlow(_state.value.defaultCurrency)

    init {
        viewModelScope.launch {
            combine(destinationList, tripName, tripId, budgets, defaultCurrency) {
                    destinationList: List<DestinationModel.Destination>,
                    tripName: String,
                    tripId: String?,
                    budgets: List<Pair<ExpenseModel.ExpenseType, BigDecimal>>,
                defaultCurrency: Currency ->
                AddTripsPageModel.AddTripViewState(
                    destinationList = destinationList,
                    tripName = tripName,
                    tripId = tripId,
                    budgets = budgets,
                    defaultCurrency = defaultCurrency
                )
            }.collect {
                _state.value = it
            }
        }
    }

    private fun getData() {
        viewModelScope.launch {
            destinationRepository.getDestinations(tripId.value).collect { destination ->
                destination.data?.let {
                    destinationList.value = it
                } ?: run {
                    Log.d("Error", "Error getting destination data")
                }
            }
        }
    }

    init {
        getData()
    }

    fun addDestination(name: String, startDate: Date, endDate: Date, readMode: Boolean) {
        val destination = DestinationModel.Destination(
            name = name,
            startDate = startDate,
            endDate = endDate,
        )
        destinationList.value += destination
        if (readMode){
            viewModelScope.launch {
                val destRef = destinationRepository.addDestination(destination)
                val destinationId = destRef?.data.toString()
                tripRepository.addDestinationId(
                    tripId = tripId.value,
                    destinationId = destinationId
                )
                getData()
            }
        }
    }

    fun deleteDestination(destination: DestinationModel.Destination, readMode: Boolean) {
        destinationList.value -= destination
        if (readMode){
            viewModelScope.launch {
                val destRef = destinationRepository.deleteDestination(
                    destinationId = destination.id,
                    tripId = tripId.value
                )
                val destinationId = destRef?.data.toString()
                tripRepository.deleteDestinationId(
                    tripId = tripId.value,
                    destinationId = destinationId
                )
            }
        }
    }

    fun setTripName(name: String) {
        tripName.value = name
    }

    fun setTripId(Id: String?) {
        tripId.value = Id.toString()
        getData()
    }

    fun addBudget(expenseType: ExpenseModel.ExpenseType, amount: String) {
        budgets.value += Pair(
            expenseType,
            BigDecimal(amount))
    }

    fun removeBudget(expenseType: ExpenseModel.ExpenseType) {
        for (budget in budgets.value) {
            if (budget.first == expenseType) {
                budgets.value -= budget
                break
            }
        }
    }

    fun updateBudget(
        expenseType: ExpenseModel.ExpenseType,
        selectedExpenseType: ExpenseModel.ExpenseType,
        amount: String
    ) {
        val newBudgets = mutableListOf<Pair<ExpenseModel.ExpenseType, BigDecimal>>()
        for (budget in budgets.value) {
            if (budget.first == expenseType) {
                newBudgets += Pair(
                    selectedExpenseType,
                    BigDecimal(amount))
            } else {
                newBudgets += budget
            }
        }
        budgets.value = newBudgets.toList()
    }

    fun deleteBudget() {
        budgets.value = budgets.value.toMutableList().removeLast()
            .toList() as List<Pair<ExpenseModel.ExpenseType, BigDecimal>>
    }

    fun initializeBudget() {
        addBudget(getExpenseTypes()[0], "0.00")
    }

    fun getExpenseTypes(): List<ExpenseModel.ExpenseType> {
        val expenseTypes = ExpenseModel.ExpenseType.values()
        val selectedExpenseTypes = budgets.value.map { it.first }
        var availableExpenseTypes = mutableListOf<ExpenseModel.ExpenseType>()
        for (expenseType in expenseTypes) {
            if (expenseType !in selectedExpenseTypes) {
                availableExpenseTypes.add(expenseType)
            }
        }
        return availableExpenseTypes
    }

    fun submitTrip() {
        viewModelScope.launch {
            val destIdList = mutableListOf<String>()
            destinationList.value.forEach { destination ->
                val response = destinationRepository.addDestination(destination)
                destIdList.add(response?.data.toString())
            }
            val tripId = tripRepository.addTrip(
                tripName = tripName.value,
                destIdList = destIdList,
                budgets = budgets.value,
                defaultCurrency = com.example.travelbuddy.data.model.Currency(code = defaultCurrency.value.code, name = defaultCurrency.value.name, symbol = defaultCurrency.value.symbol)
            )
            tripRepository.addTripIdToUser(tripId.data.toString())
        }
    }

    fun navigateToTrips() {
        navWrapper.getNavController().navigate(Screen.Trips.route)
    }

    fun navigateToItinerary(destinationId: String) {
        navWrapper.getNavController()
            .navigate(Screen.Itinerary.route + "?destinationId=${destinationId}")
    }

    fun setDefaultCurrency(currency: Currency) {
        defaultCurrency.value = currency
    }
}