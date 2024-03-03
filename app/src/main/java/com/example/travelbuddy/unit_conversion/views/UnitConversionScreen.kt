package com.example.travelbuddy.unit_conversion.views

import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.travelbuddy.unit_conversion.ScreenData
import com.example.travelbuddy.unit_conversion.ScreenType
import com.example.travelbuddy.unit_conversion.UnitConversionViewModel
import com.example.travelbuddy.unit_conversion.UpdateType


@Composable
fun UnitConversionScreen(viewModel: UnitConversionViewModel = viewModel()) {

    val state = viewModel.uiState.collectAsState()

    when(state.value.screenType){
        ScreenType.DEFAULT -> DefaultUnitConversionScreen(
            onClick = { screenType -> viewModel.clickEvent(screenType = screenType) }
        )
        else -> ConversionScreen(
            data = state.value.screenData as ScreenData.ConversionData,
            updateInputAmount = { viewModel.updateAmount(UpdateType.INPUT, it) },
            updateOutputAmount = {viewModel.updateAmount(UpdateType.OUTPUT, it) },
            updateInputType = { viewModel.updateType(UpdateType.INPUT, it) },
            updateOutputType = { viewModel.updateType(UpdateType.OUTPUT, it) },
            onBackPress = { viewModel.goBack() }
        )
    }
}