package com.example.travelbuddy.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle

@Composable
fun OutlinedTextInput(
    value: String,
    label: @Composable (() -> Unit),
    icon: ImageVector,
    onInputChange: (String) -> Unit
) {
    OutlinedTextField(
        label = label,
        value = value,
        onValueChange = { change: String -> onInputChange(change) },
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        singleLine = true,
        leadingIcon = {
            Icon(
                imageVector = icon,
                contentDescription = "profile"
            )
        },
        textStyle = TextStyle(color = Color.Black) // This line changes the text color to black
    )
}