package com.example.travelbuddy.auth.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun StandardText(value: String) {
    Text(
        text = value,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 40.dp),
        fontSize = 24.sp,
        color = Color.Black,
        textAlign = TextAlign.Center
    )
}

@Composable
fun OutlinedTextEntry(label: @Composable (() -> Unit), icon: ImageVector) {
    var textValue = remember { mutableStateOf("") }
    OutlinedTextField(
        label = label,
        value = textValue.value,
        onValueChange = { change: String -> textValue.value = change },
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        singleLine = true,
        leadingIcon = {
            Icon(
                imageVector = icon,
                contentDescription = "profile"
            )
        },
    )
}

@Composable
fun OutlinedTextPasswordEntry(label: @Composable (() -> Unit), icon: ImageVector) {
    var password = remember { mutableStateOf("") }
    var hidePassword = remember {  mutableStateOf(false) }

    OutlinedTextField(
        label = label,
        value = password.value,
        onValueChange = { change: String -> password.value = change },
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
    )
}