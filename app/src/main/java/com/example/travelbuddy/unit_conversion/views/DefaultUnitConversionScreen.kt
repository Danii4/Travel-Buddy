package com.example.travelbuddy.unit_conversion.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.travelbuddy.CustomColors
import com.example.travelbuddy.R
import com.example.travelbuddy.ui.theme.DarkBlue
import com.example.travelbuddy.ui.theme.DarkGreen
import com.example.travelbuddy.ui.theme.DarkGrey
import com.example.travelbuddy.ui.theme.DarkOrange
import com.example.travelbuddy.ui.theme.DarkPurple
import com.example.travelbuddy.ui.theme.DarkRed
import com.example.travelbuddy.unit_conversion.model.ScreenType
import com.example.travelbuddy.util.ImageType

@Composable
fun DefaultUnitConversionScreen(onClick: (currency: ScreenType) -> Unit){
    Surface {
        Column(
            modifier = Modifier.padding(5.dp).fillMaxHeight(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Select Conversion Type:",
                color = Color.Black,
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(10.dp),
            )
            UnitTypeRow(
                bgColor = CustomColors.LightGreen,
                fgColor = Color.White,
                icon = ImageType.Drawable(R.drawable.money_24),
                title = "Currency",
                onClick = { onClick(ScreenType.CURRENCY) }
            )
            UnitTypeRow(
                bgColor = CustomColors.Pink,
                fgColor = Color.White,
                icon = ImageType.Drawable(R.drawable.length_24),
                title = "Lengths",
                onClick = { onClick(ScreenType.LENGTHS) }
            )
            UnitTypeRow(
                bgColor = CustomColors.Blue,
                fgColor = Color.White,
                icon = ImageType.Drawable(R.drawable.temperature_24),
                title = "Temperature",
                onClick = { onClick(ScreenType.TEMPERATURE) }
            )
            UnitTypeRow(
                bgColor = CustomColors.Indigo,
                fgColor = Color.White,
                icon = ImageType.Drawable(R.drawable.weight_24),
                title = "Weight",
                onClick = { onClick(ScreenType.WEIGHT) }
            )
            UnitTypeRow(
                bgColor = CustomColors.DarkGreen,
                fgColor = Color.White,
                icon = ImageType.Drawable(R.drawable.volume_24),
                title = "Volume",
                onClick = { onClick(ScreenType.VOLUME) }
            )
        }
    }
}