package com.example.travelbuddy.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BottomAuthComponent(
    primaryButtonText: String,
    primaryOnClick: () -> Unit,
    secondaryButtonText: String,
    secondaryOnClick: () -> Unit,
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            PrimaryButton(text = primaryButtonText, onClick = primaryOnClick)

            Row(
                modifier = Modifier.fillMaxWidth().padding(vertical = 5.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                HorizontalDivider(
                    modifier = Modifier.fillMaxWidth().weight(1f),
                    thickness = 1.dp,
                    color = Color.Gray
                )
                Text(
                    text = "Or",
                    modifier = Modifier.padding(10.dp),
                    fontSize = 20.sp
                )
                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    thickness = 1.dp,
                    color = Color.Gray
                )
            }
            SecondaryButton(
                text = secondaryButtonText,
                onClick = secondaryOnClick,
            )
        }
    }
    Spacer(modifier = Modifier.height(15.dp))
}
