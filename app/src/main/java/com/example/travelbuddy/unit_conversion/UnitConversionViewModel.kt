package com.example.travelbuddy.unit_conversion

import android.graphics.drawable.Icon
import android.text.InputType
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.Arrays

enum class ScreenType {
    DEFAULT,
    CURRENCY,
    LENGTHS,
    TEMPERATURE,
    WEIGHT,
    VOLUME,
}

enum class UpdateType {
    INPUT,
    OUTPUT,
}

data class UnitConversionUiState(
    val screenType: ScreenType = ScreenType.DEFAULT,
    val screenData: ScreenData = ScreenData.ConversionData(
        screenType = ScreenType.TEMPERATURE,
        inputAmount = "",
        outputAmount = "",
        inputData = ScreenData.ConvValue.Default(amountEquivalent = 1.0, label = "Celcius"),
        outputData = ScreenData.ConvValue.Default(amountEquivalent = 32.0, label = "Farenheit"),
        listOfData = Arrays.asList(
             ScreenData.ConvValue.Default(amountEquivalent = 1.0, label = "Celcius"),
             ScreenData.ConvValue.Default(amountEquivalent = 32.0, label = "Farenheit"),
        )
    )
)

sealed interface ScreenData {

    sealed interface ConvValue {
        val amountEquivalent: Double
        val label: String

        data class Default(
            override val amountEquivalent: Double,
            override val label: String,
        ): ConvValue

        data class Country(
            override val amountEquivalent: Double,
            override val label: String,
            val icon: Icon,
        ): ConvValue
    }

    object Default: ScreenData
    data class ConversionData (
        val screenType: ScreenType,
        val inputAmount: String,
        val outputAmount: String,
        val inputData: ConvValue,
        val outputData: ConvValue,
        val listOfData: List<ConvValue>,
    ): ScreenData {
        fun recalculateData(updateType: UpdateType, updateAmount: Double): Double {
            return when(updateType){
                UpdateType.INPUT -> {
                    val inputNorm: Double = updateAmount /  inputData.amountEquivalent
                    val outputNorm: Double = inputNorm * outputData.amountEquivalent
                    outputNorm
                }
                UpdateType.OUTPUT -> {
                    // Convert to Standard Unit + Convert from Standard Unit using conversion Rate
                    val outputNorm: Double =  updateAmount / outputData.amountEquivalent
                    val inputNorm: Double = outputNorm * inputData.amountEquivalent
                    inputNorm;
                }
            }
        }

        fun recalculateDataTypeChange(updateType: UpdateType, convValue: ConvValue): String {
            return when(updateType){
                UpdateType.INPUT -> {
                    if(outputAmount.toDoubleOrNull() != null) {
                        val outputNorm: Double =
                            outputAmount.toDouble() / outputData.amountEquivalent
                        val inputNorm: Double = outputNorm * convValue.amountEquivalent
                        inputNorm.toString()
                    } else { inputAmount }
                }
                UpdateType.OUTPUT -> {
                    if (inputAmount.toDoubleOrNull() != null) {
                        val inputNorm: Double = inputAmount.toDouble() / inputData.amountEquivalent
                        val outputNorm: Double = inputNorm * convValue.amountEquivalent
                        outputNorm.toString()
                    } else { outputAmount }
                }
            }
        }

        fun searchForData(input: String): ScreenData.ConvValue{
            return listOfData.find { it.label == input }!!
        }
    }
}



class UnitConversionViewModel : ViewModel() {

    // Expose screen UI state
    private val _uiState = MutableStateFlow(UnitConversionUiState())
    val uiState: StateFlow<UnitConversionUiState> = _uiState.asStateFlow()

    fun clickEvent(screenType: ScreenType) {
        _uiState.update { currentState ->
            currentState.copy(
                screenType = screenType,
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

