package com.example.travelbuddy.languageTranslation

import androidx.lifecycle.ViewModel
import com.example.travelbuddy.languageTranslation.model.TranslationModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

data class RecentInput(
    val inputText: String,
    val inputLanguage: String,
    val outputLanguage: String
)
@HiltViewModel
class TranslationViewModel @Inject constructor(
    private val translationModel: TranslationModel
) : ViewModel() {

    private val _inputText = MutableStateFlow("")

    private val _translatedText = MutableStateFlow("")
    val translatedText = _translatedText.asStateFlow()

    private val _languageHistory = MutableStateFlow<List<String>>(emptyList())
    val languageHistory = _languageHistory.asStateFlow()

    private val _recentInputs = MutableStateFlow<List<RecentInput>>(emptyList())
    val recentInputs = _recentInputs.asStateFlow()

    fun setInputText(inputText: String) {
        _inputText.value = inputText
    }
    
    fun translateText(inputText: String, sourceLanguage: String, targetLanguage: String) {
        addRecentInput(inputText, sourceLanguage, targetLanguage)
        translationModel.performTranslation(inputText, sourceLanguage, targetLanguage,
            onSuccess = { translatedText ->
                _translatedText.value = translatedText
            },
            onFailure = { error ->
                _translatedText.value = error
            })
    }

    fun mapToCode(inputText: String): String {
        return translationModel.mapLanguageToCode(inputText)
    }
    fun updateLanguagesHistory(selectedLanguage: String) {
        val updatedList = _languageHistory.value.toMutableList()
        updatedList.remove(selectedLanguage)
        updatedList.add(0, selectedLanguage)
        _languageHistory.value = updatedList.take(3)
    }

    fun addRecentInput(inputText: String, inputLanguage: String, outputLanguage: String) {
        if (inputText != "") {
            val newEntry = RecentInput(inputText, inputLanguage, outputLanguage)
            val updatedInputs = listOf(newEntry) + _recentInputs.value.filterNot {
                it.inputText == inputText && it.inputLanguage == inputLanguage && it.outputLanguage == outputLanguage
            }.take(3)
            _recentInputs.value = updatedInputs

        }

    }
}