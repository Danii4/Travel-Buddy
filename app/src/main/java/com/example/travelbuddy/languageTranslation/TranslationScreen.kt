package com.example.travelbuddy.languageTranslation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


/*
* Notes for Prototype:
* Swap is not fully functional
* Currently only supports text input. Voice will be added later
* Dropdown will be enhanced for more clarity
* History feature currently not supported
* RTL languages currently not supported
* */

@Composable
fun TranslationScreen() {
    var inputText by remember { mutableStateOf(TextFieldValue("")) }
    var translatedText by remember { mutableStateOf("") }
    var inputLanguageSelected by remember { mutableStateOf("English") }
    var outputLanguageSelected by remember { mutableStateOf("French") }
    val languages = listOf(
        "Arabic",
        "Bengali",
        "Chinese",
        "English",
        "French",
        "German",
        "Hindi",
        "Indonesian",
        "Italian",
        "Japanese",
        "Korean",
        "Malay",
        "Marathi",
        "Persian",
        "Polish",
        "Portuguese",
        "Russian",
        "Spanish",
        "Swahili",
        "Tamil",
        "Telugu",
        "Thai",
        "Turkish",
        "Urdu",
        "Vietnamese",
        "Dutch",
        "Greek",
        "Hungarian",
        "Romanian",
        "Swedish"
    )
    var showInputDropdown by remember { mutableStateOf(false) }
    var showOutputDropdown by remember { mutableStateOf(false) }

    Column(modifier = Modifier.padding(16.dp)) {
        // Input language selection dropdown
        LanguageSelectionDropdown(
            label = "Input",
            selectedLanguage = inputLanguageSelected,
            languages = languages,
            showDropdown = showInputDropdown,
            onLanguageSelected = { inputLanguageSelected = it },
            onDropdownChange = { showInputDropdown = it },
        )

        // Box for displaying input text
        OutlinedTextField(
            value = inputText,
            onValueChange = { inputText = it },
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .padding(bottom = 16.dp),
            label = { Text("Input Text") }
        )

        // Translate and Swap buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Translate button
            Button(
                onClick = {
                    Translator.performTranslation(
                        inputText = inputText.text,
                        sourceLanguage = inputLanguageSelected,
                        targetLanguage = outputLanguageSelected,
                        onSuccess = { translation ->
                            translatedText = translation
                        },
                        onFailure = { error ->
                            translatedText = "Error: $error"

                        }
                    )
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("Translate")
            }
            Spacer(modifier = Modifier.width(8.dp))
            // Swap button
            Button(
                onClick = {
                    val tempLanguage = inputLanguageSelected
                    inputLanguageSelected = outputLanguageSelected
                    outputLanguageSelected = tempLanguage
                    inputText = TextFieldValue(translatedText)
                    translatedText = ""
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("Swap")
            }
        }

        // Output language selection dropdown
        LanguageSelectionDropdown(
            label = "Output",
            selectedLanguage = outputLanguageSelected,
            languages = languages,
            showDropdown = showOutputDropdown,
            onLanguageSelected = { outputLanguageSelected = it },
            onDropdownChange = { showOutputDropdown = it }
        )

        // Box for displaying translated text
        OutlinedTextField(
            value = translatedText,
            onValueChange = {},
            readOnly = true,
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .padding(bottom = 16.dp),
            label = { Text("Translated Text") }
        )
    }
}

@Composable
fun LanguageSelectionDropdown(
    label: String,
    selectedLanguage: String,
    languages: List<String>,
    showDropdown: Boolean,
    onLanguageSelected: (String) -> Unit,
    onDropdownChange: (Boolean) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
    ) {
        Text(text = "$label: ")

        Box(modifier = Modifier.weight(2f)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .clickable { onDropdownChange(true) }
                    .padding(end = 8.dp)
            ){
                Text(
                    text = selectedLanguage,
                    modifier = Modifier.padding(end = 4.dp)
                )
                Icon(
                    imageVector = Icons.Filled.ArrowDropDown,
                    contentDescription = "Dropdown Arrow"
                )
            }
            DropdownMenu(
                expanded = showDropdown,
                onDismissRequest = { onDropdownChange(false) }
            ) {
                languages.forEach { language ->
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

@Preview(showBackground = true)
@Composable
fun TranslationScreenPreview() {
    TranslationScreen()
}
