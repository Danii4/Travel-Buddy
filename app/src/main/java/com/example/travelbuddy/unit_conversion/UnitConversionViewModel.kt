package com.example.travelbuddy.unit_conversion

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.travelbuddy.unit_conversion.model.ScreenData
import com.example.travelbuddy.unit_conversion.model.ScreenType
import com.example.travelbuddy.unit_conversion.model.UnitConversionUiState
import com.example.travelbuddy.unit_conversion.model.UpdateType
import com.example.travelbuddy.unit_conversion.repository.getConversionData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class UnitConversionViewModel : ViewModel() {

    // Expose screen UI state
    private val _uiState = MutableStateFlow(UnitConversionUiState())
    val uiState: StateFlow<UnitConversionUiState> = _uiState.asStateFlow()

    fun clickEvent(screenType: ScreenType) {
        _uiState.update { currentState ->
            // Over here make fetch request to get data for that screenType:
            currentState.copy(
                screenType = screenType,
                screenData = screenType.getConversionData(),
            )
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

