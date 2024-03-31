package com.example.travelbuddy.languageTranslation.views

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.travelbuddy.languageTranslation.TranslationViewModel
import com.example.travelbuddy.languageTranslation.components.LanguageSelectionDropdown

@Composable
fun TranslationScreen() {
    var inputText by remember { mutableStateOf(TextFieldValue("")) }
    val viewModel = hiltViewModel<TranslationViewModel>()
    val translatedText by viewModel.translatedText.collectAsState()
    var inputLanguageSelected by remember { mutableStateOf("English") }
    var outputLanguageSelected by remember { mutableStateOf("French") }
    val languageHistory by viewModel.languageHistory.collectAsState()
    val recentInputs by viewModel.recentInputs.collectAsState()

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
            onLanguageSelected = {  language ->
                inputLanguageSelected = language
                viewModel.updateLanguagesHistory(language)
                showInputDropdown = false },
            onDropdownChange = { showInputDropdown = it },
            languageHistory = languageHistory
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
                    viewModel.translateText(inputText.text, inputLanguageSelected, outputLanguageSelected)
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
            onLanguageSelected = {  language ->
                outputLanguageSelected = language
                viewModel.updateLanguagesHistory(language)
                showOutputDropdown = false },
            onDropdownChange = { showOutputDropdown = it },
            languageHistory = languageHistory
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

        // Clickable recent inputs shown in a column
        Column {
            Text("Recent Inputs: ")
            recentInputs.forEach { recentInput ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {
                    Text(
                        text = "${viewModel.mapToCode(recentInput.inputLanguage)} to ${viewModel.mapToCode(recentInput.outputLanguage)}",
                    )
                    OutlinedButton(
                        onClick = {
                            inputText = TextFieldValue(recentInput.inputText)
                            inputLanguageSelected = recentInput.inputLanguage
                            outputLanguageSelected = recentInput.outputLanguage
                            viewModel.translateText(inputText.text, inputLanguageSelected, outputLanguageSelected)
                        },
                        modifier = Modifier
                            .padding(start = 8.dp)
                    ) {
                        Text(
                            text = recentInput.inputText,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
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
