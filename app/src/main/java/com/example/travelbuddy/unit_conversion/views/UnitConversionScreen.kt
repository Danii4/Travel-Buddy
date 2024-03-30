package com.example.travelbuddy.unit_conversion.views

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.travelbuddy.unit_conversion.UnitConversionViewModel
import com.example.travelbuddy.unit_conversion.model.ScreenData
import com.example.travelbuddy.unit_conversion.model.ScreenType
import com.example.travelbuddy.unit_conversion.model.UnitConversionUiState
import com.example.travelbuddy.unit_conversion.model.UpdateType

@Composable
fun UnitConversionScreen(viewModel: UnitConversionViewModel = hiltViewModel()) {

    var state = viewModel.uiState.collectAsState(initial = UnitConversionUiState())

    when(state.value?.screenType){
        ScreenType.DEFAULT -> DefaultUnitConversionScreen(
            onClick = { screenType -> viewModel.clickEvent(screenType = screenType) }
        )
        else -> ConversionScreen(
            data = state.value?.screenData as ScreenData.ConversionData,
            updateInputAmount = { viewModel.updateAmount(UpdateType.INPUT, it) },
            updateOutputAmount = {viewModel.updateAmount(UpdateType.OUTPUT, it) },
            updateInputType = { viewModel.updateType(UpdateType.INPUT, it) },
            updateOutputType = { viewModel.updateType(UpdateType.OUTPUT, it) },
            onBackPress = { viewModel.goBack() }
        )
    }
}