package com.example.travelbuddy.languageTranslation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.travelbuddy.languageTranslation.CustomColors

@Composable
fun LanguageSelectionDropdown(
    label: String,
    selectedLanguage: String,
    languages: List<String>,
    showDropdown: Boolean,
    onLanguageSelected: (String) -> Unit,
    onDropdownChange: (Boolean) -> Unit,
    languageHistory: List<String>
) {

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
    ) {
        Text(text = "$label ", color = CustomColors.Indigo, fontWeight = FontWeight.Bold)

        Box(modifier = Modifier.weight(2f)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .clickable { onDropdownChange(true) }
                    .clip(RoundedCornerShape(8.dp))
                    .background(color = CustomColors.LightIndigo)
            ){
                Text(
                    text = selectedLanguage,
                    modifier = Modifier.padding(start = 8.dp, top = 8.dp, bottom = 8.dp, end = 2.dp),
                    color = Color.White
                )
                Icon(
                    imageVector = Icons.Filled.ArrowDropDown,
                    contentDescription = "Dropdown Arrow",
                    tint = Color.White
                )
            }
            DropdownMenu(
                expanded = showDropdown,
                onDismissRequest = { onDropdownChange(false) }
            ) {
                //Displaying the language history first followed by border
                languageHistory.forEach { language ->
                    DropdownMenuItem(
                        text = { Text(language) },
                        onClick = {
                            onLanguageSelected(language)
                            onDropdownChange(false)
                        }
                    )
                }

                if (languageHistory.isNotEmpty()) {
                    HorizontalDivider()
                }

                // Displaying the rest of the languages
                (languages - languageHistory).forEach { language ->
                    DropdownMenuItem(
                        text = { Text(language) },
                        onClick = {
                            onLanguageSelected(language)
                            onDropdownChange(false)
                        }
                    )
                }
            }
        }
    }
}
