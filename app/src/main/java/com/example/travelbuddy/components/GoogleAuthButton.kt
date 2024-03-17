package com.example.travelbuddy.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.travelbuddy.R

@Composable
fun GoogleAuthButton(
    text: String,
    onClick: () -> Unit,
) {
    Button(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(50.dp),
        colors = ButtonDefaults.buttonColors(Color.Transparent),
        border = BorderStroke(width = 1.dp, color = Color.Black),
        onClick = onClick,
    ) {
        Icon(
            modifier = Modifier.padding(horizontal = 5.dp),
            imageVector = ImageVector.vectorResource(R.drawable.google_24),
            contentDescription = "Google Icon",
            tint = Color.Black,
        )
        Text(
            text = text,
            color = Color.Black,
            modifier = Modifier.padding(5.dp),
            fontSize = 20.sp
        )
    }
}