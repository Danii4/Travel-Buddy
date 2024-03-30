package com.example.travelbuddy.unit_conversion.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.travelbuddy.util.ImageType
import kotlin.math.round

@Composable
fun UnitTypeRow(
    bgColor: Color,
    fgColor: Color,
    icon: ImageType,
    title: String,
    onClick: () -> Unit,
){
    return Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .height(60.dp)
            .clip(RoundedCornerShape(10.dp, 10.dp, 10.dp, 10.dp))
            .background(bgColor)
            .clickable { onClick() },
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = icon.resolveImage(),
            contentDescription = title,
            tint = fgColor,
            modifier = Modifier.size(width = 30.dp, height = 30.dp)
        )
        Text(
            text = title,
            fontSize = 30.sp,
            color = fgColor,
            modifier = Modifier.padding(horizontal = 10.dp)
        )
    }
}