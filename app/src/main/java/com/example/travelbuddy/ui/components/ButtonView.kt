@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.travelbuddy.ui.components

import android.service.autofill.OnClickAction
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.List
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun ButtonView(
//    buttonUiState: UiComponentModel.ButtonUiState,
//    buttonUiEvent: UiComponentModel.ButtonUiEvent,
//    modifier: Modifier = Modifier,
    onClickAction: OnClickAction,
    Icon: Icons,
    text: String
) {
    return AssistChip(
        onClick = { },
        colors = AssistChipDefaults.assistChipColors(
            leadingIconContentColor = MaterialTheme.colorScheme.onSurfaceVariant
        ),
        leadingIcon = {
            Icon
        },
        label = {
            Text(text = text)
        }
    )
}