package com.example.travelbuddy.unit_conversion.model

import android.graphics.drawable.Icon
import androidx.compose.ui.graphics.Color
import com.example.travelbuddy.ui.theme.DarkBlue
import com.example.travelbuddy.ui.theme.DarkGreen
import com.example.travelbuddy.ui.theme.DarkOrange
import com.example.travelbuddy.ui.theme.DarkPurple
import com.example.travelbuddy.ui.theme.DarkRed
import com.example.travelbuddy.ui.theme.ExtraLightBlue
import com.example.travelbuddy.ui.theme.ExtraLightGreen
import com.example.travelbuddy.ui.theme.ExtraLightOrange
import com.example.travelbuddy.ui.theme.ExtraLightPurple
import com.example.travelbuddy.ui.theme.ExtraLightRed
import com.example.travelbuddy.ui.theme.LightBlue
import com.example.travelbuddy.ui.theme.LightGreen
import com.example.travelbuddy.ui.theme.LightOrange
import com.example.travelbuddy.ui.theme.LightPurple
import com.example.travelbuddy.ui.theme.LightRed
import com.example.travelbuddy.unit_conversion.repository.getDefaultConversionData
import com.example.travelbuddy.unit_conversion.repository.getTemperatureConversionData

enum class ScreenType {
    DEFAULT,
    CURRENCY,
    LENGTHS,
    TEMPERATURE,
    WEIGHT,
    VOLUME,
}

data class ScreenColor(
    val bgColor: Color,
    val fgColor: Color,
    val bgbgColor: Color,
    val textColor: Color,
)

fun ScreenType.getColors(): ScreenColor {
    return when(this){
        ScreenType.DEFAULT -> ScreenColor(Color.White, Color.White, Color.White, Color.White)
        ScreenType.CURRENCY -> ScreenColor(DarkGreen, LightGreen, ExtraLightGreen, Color.White)
        ScreenType.LENGTHS -> ScreenColor(DarkRed, LightRed, ExtraLightRed, Color.White)
        ScreenType.TEMPERATURE -> ScreenColor(DarkBlue, LightBlue, ExtraLightBlue, Color.White)
        ScreenType.WEIGHT -> ScreenColor(DarkPurple, LightPurple, ExtraLightPurple, Color.White)
        ScreenType.VOLUME -> ScreenColor(DarkOrange, LightOrange, ExtraLightOrange, Color.White)
    }
}
fun ScreenType.getTitle(): String {
    return when(this){
        ScreenType.DEFAULT -> "Default"
        ScreenType.CURRENCY -> "Currency Converter"
        ScreenType.LENGTHS -> "Length Converter"
        ScreenType.TEMPERATURE -> "Temperature Converter"
        ScreenType.WEIGHT -> "Weight Converter"
        ScreenType.VOLUME -> "Volume Converter"
    }
}

enum class UpdateType {
    INPUT,
    OUTPUT,
}

data class UnitConversionUiState(
    val screenType: ScreenType = ScreenType.DEFAULT,
    val screenData: ScreenData = getDefaultConversionData()
)

sealed interface ScreenData {

    sealed interface ConvValue {
        val conv: ConvMethod
        val label: String

        data class Default(
            override val conv: ConvMethod,
            override val label: String,
        ): ConvValue

        data class Country(
            override val conv: ConvMethod,
            override val label: String,
            val icon: Icon,
        ): ConvValue
    }

    sealed interface ConvMethod{
        data class Equiv(val amount: Double): ConvMethod
        data class ConvFunction(
            val forwardConv: ((Double) -> Double),
            val backwardConv: ((Double) -> Double)
        ): ConvMethod
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

        fun fbwConvert(amount: Double, conv: ConvMethod, isForward:Boolean): Double{
            return when(conv) {
                is ConvMethod.ConvFunction -> {
                    if(isForward) conv.forwardConv(amount)
                    else conv.backwardConv(amount)
                }

                is ConvMethod.Equiv -> {
                    if(isForward) amount/conv.amount
                    else amount * conv.amount
                }
            }
        }

        fun recalculateData(updateType: UpdateType, updateAmount: Double): Double {
            return when(updateType){
                UpdateType.INPUT -> {
                    val inputNorm = fbwConvert(updateAmount, inputData.conv, true)
                    val outputNorm = fbwConvert(inputNorm, outputData.conv, false)
                    outputNorm
                }
                UpdateType.OUTPUT -> {
                    // Convert to Standard Unit + Convert from Standard Unit using conversion Rate
                    val outputNorm = fbwConvert(updateAmount, outputData.conv, true)
                    val inputNorm = fbwConvert(outputNorm, inputData.conv, false)
                    inputNorm;
                }
            }
        }

        fun recalculateDataTypeChange(updateType: UpdateType, convValue: ConvValue): String {
            return when(updateType){
                UpdateType.INPUT -> {
                    if(outputAmount.toDoubleOrNull() != null) {
                        val outputNorm = fbwConvert(outputAmount.toDouble(), outputData.conv, true)
                        val inputNorm = fbwConvert(outputNorm, convValue.conv, false)
                        inputNorm.toString()
                    } else { inputAmount }
                }
                UpdateType.OUTPUT -> {
                    if (inputAmount.toDoubleOrNull() != null) {
                        val inputNorm = fbwConvert(inputAmount.toDouble(), inputData.conv, true)
                        val outputNorm = fbwConvert(inputNorm, convValue.conv, false)
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
