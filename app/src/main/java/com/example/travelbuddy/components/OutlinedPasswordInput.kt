package com.example.travelbuddy.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation

@Composable
fun OutlinedPasswordInput(
    value: String,
    label: @Composable (() -> Unit),
    icon: ImageVector,
    onInputChange: (String) -> Unit
) {
    var hidePassword = remember {  mutableStateOf(false) }

    OutlinedTextField(
        label = label,
        value = value,
        onValueChange = { change: String -> onInputChange(change) },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        shape = MaterialTheme.shapes.medium,
        leadingIcon = {
            Icon(
                imageVector = icon,
                contentDescription = "profile"
            )
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        visualTransformation = if (hidePassword.value) VisualTransformation.None else PasswordVisualTransformation() ,
        trailingIcon = {
            val iconImage = if (hidePassword.value) Icons.Outlined.Visibility else Icons.Outlined.VisibilityOff
            val description = if (hidePassword.value) "Show Password" else "Hide Password"
            IconButton(onClick = { hidePassword.value = !hidePassword.value }) {
                Icon(imageVector = iconImage, contentDescription = description)
            }
        },
        textStyle = TextStyle(color = Color.Black) // This line changes the text color to black
    )
}