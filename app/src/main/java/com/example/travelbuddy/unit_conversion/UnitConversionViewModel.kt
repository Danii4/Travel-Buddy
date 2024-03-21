package com.example.travelbuddy.unit_conversion

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travelbuddy.data.model.ResponseModel
import com.example.travelbuddy.repository.CurrencyExchangeRepository
import com.example.travelbuddy.unit_conversion.model.ScreenData
import com.example.travelbuddy.unit_conversion.model.ScreenType
import com.example.travelbuddy.unit_conversion.model.UnitConversionUiState
import com.example.travelbuddy.unit_conversion.model.UpdateType
import com.example.travelbuddy.unit_conversion.repository.getDefaultConversionData
import com.example.travelbuddy.unit_conversion.repository.getLengthConversionData
import com.example.travelbuddy.unit_conversion.repository.getTemperatureConversionData
import com.example.travelbuddy.unit_conversion.repository.getVolumeConversionData
import com.example.travelbuddy.unit_conversion.repository.getWeightConversionData
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


data class CurrencyExchangeState(
    val loading: Boolean = false,
    val success: String? = null,
    val error: String? = null
)

@HiltViewModel
class UnitConversionViewModel @Inject constructor(
    private val repo: CurrencyExchangeRepository
): ViewModel() {

    // Expose screen UI state
    private val _uiState = MutableStateFlow(UnitConversionUiState())
    val uiState: StateFlow<UnitConversionUiState> = _uiState.asStateFlow()

    private val _currencyExchangeData = MutableStateFlow<ScreenData.ConversionData?>(null)
    private val currencyExchangeData = _currencyExchangeData.asStateFlow()


    fun getConversionData(screenType: ScreenType): ScreenData {
        return when (screenType) {
            ScreenType.DEFAULT -> getDefaultConversionData()
            ScreenType.CURRENCY -> getDefaultConversionData()
            ScreenType.LENGTHS -> getLengthConversionData()
            ScreenType.TEMPERATURE -> getTemperatureConversionData()
            ScreenType.WEIGHT -> getWeightConversionData()
            ScreenType.VOLUME -> getVolumeConversionData()
        }
    }


    fun clickEvent(screenType: ScreenType) {
        when(screenType) {
            ScreenType.CURRENCY -> {
                viewModelScope.launch { fetchCurrencyExchangeData() }
            }
            else -> {
                // Immediately available data
                _uiState.update { currentState ->
                    currentState.copy(
                        screenType = screenType,
                        screenData = getConversionData(screenType),
                    )
                }
            }
        }
    }

    fun goBack() {
        _uiState.update { currentState ->
            when(currentState.screenData){
                is ScreenData.Default -> currentState
                is ScreenData.ConversionData -> currentState.copy(screenType = ScreenType.DEFAULT)
            }
        }
    }

    fun updateAmount(updateType: UpdateType, newValue: String) {
        _uiState.update { currentState ->
            when(currentState.screenData) {
                is ScreenData.Default -> currentState
                is ScreenData.ConversionData -> {

                    lateinit var newInputAmount: String
                    lateinit var newOutputAmount: String

                    if(updateType == UpdateType.INPUT){
                        newInputAmount = newValue
                        if(newValue.toDoubleOrNull() != null) {
                            newOutputAmount = currentState.screenData.recalculateData(
                                updateType = updateType,
                                updateAmount = newValue.toDouble(),
                            ).toString()
                        } else {
                            newOutputAmount = currentState.screenData.outputAmount
                        }
                    } else {
                        newOutputAmount = newValue
                        if(newValue.toDoubleOrNull() != null) {
                            newInputAmount = currentState.screenData.recalculateData(
                                updateType = updateType,
                                updateAmount = newValue.toDouble()
                            ).toString()
                        } else {
                            newInputAmount = currentState.screenData.inputAmount
                        }
                    }
                    currentState.copy(
                        screenData = currentState.screenData.copy(
                            inputAmount = newInputAmount,
                            outputAmount = newOutputAmount
                        )
                    )
                }
            }
        }
    }

    private fun fetchCurrencyExchangeData() = viewModelScope.launch {
        repo.getExchangeRates().collect {
            when(it) {
                is ResponseModel.ResponseWithData.Failure -> { Log.d("ERROR", "error fetching exchange rates")}
                is ResponseModel.ResponseWithData.Loading -> { Log.d("LOADING", "waiting to fetch exchange rates")}
                is ResponseModel.ResponseWithData.Success -> {
                    it.data?.let { currencyData ->

                        Log.d("SUCCESS", "fetching exchange rate data")
                        Log.d("DATA", Gson().toJson(currencyData))

                        val list_data: List<ScreenData.ConvValue> = it.data.conversion_rates?.map { (label, conv) ->
                            ScreenData.ConvValue.Default(
                                conv = ScreenData.ConvMethod.Equiv(1/conv),
                                label = label
                            )
                        }.orEmpty()

                        currencyData.conversion_rates
                        val data = ScreenData.ConversionData(
                            screenType = ScreenType.CURRENCY,
                            inputAmount = "0",
                            outputAmount = "0",
                            inputData = list_data[0],
                            outputData = list_data[1],
                            listOfData = list_data,
                        )
                        _uiState.update { currentState ->
                            currentState.copy(
                                screenType = ScreenType.CURRENCY,
                                screenData = data,
                            )
                        }
                    }
                }
            }
        }
    }

    fun updateType(updateType: UpdateType, newType: String) {
        _uiState.update { currentState ->
            when(currentState.screenData) {
                is ScreenData.Default -> currentState
                is ScreenData.ConversionData -> {

                    lateinit var newInputAmount: String
                    lateinit var newOutputAmount: String
                    lateinit var newInputData: ScreenData.ConvValue
                    lateinit var newOutputData: ScreenData.ConvValue
                    // output change then output value should change!!

                    if(updateType == UpdateType.INPUT){
                        newOutputAmount = currentState.screenData.outputAmount
                        newOutputData = currentState.screenData.outputData
                        newInputData = currentState.screenData.searchForData(newType)
                        newInputAmount = currentState.screenData.recalculateDataTypeChange(
                            updateType = updateType,
                            convValue =  newOutputData
                        )
                        Log.d("FAHAD", newInputAmount)
                    } else {
                        newInputAmount = currentState.screenData.inputAmount
                        newInputData = currentState.screenData.inputData
                        newOutputData = currentState.screenData.searchForData(newType)
                        newOutputAmount = currentState.screenData.recalculateDataTypeChange(
                            updateType = updateType,
                            convValue =  newOutputData
                        )
                    }
                    currentState.copy(
                        screenData = currentState.screenData.copy(
                            inputAmount = newInputAmount,
                            outputAmount = newOutputAmount,
                            inputData = newInputData,
                            outputData = newOutputData,
                        )
                    )
                }
            }
        }
    }
}

