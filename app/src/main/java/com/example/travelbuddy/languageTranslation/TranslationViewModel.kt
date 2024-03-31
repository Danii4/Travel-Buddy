package com.example.travelbuddy.languageTranslation

import androidx.lifecycle.ViewModel
import com.example.travelbuddy.languageTranslation.model.TranslationModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class TranslationViewModel @Inject constructor(
    private val translationModel: TranslationModel
) : ViewModel() {

    private val _inputText = MutableStateFlow("")

    private val _translatedText = MutableStateFlow("")
    val translatedText = _translatedText.asStateFlow()

    private val _languageHistory = MutableStateFlow<List<String>>(emptyList())
    val languageHistory = _languageHistory.asStateFlow()

    private val _recentInputs = MutableStateFlow<List<String>>(emptyList())
    val recentInputs = _recentInputs.asStateFlow()

    fun setInputText(inputText: String) {
        _inputText.value = inputText
        addRecentInput(inputText)
    }
    fun translateText(inputText: String, sourceLanguage: String, targetLanguage: String) {
        addRecentInput(inputText)
        translationModel.performTranslation(inputText, sourceLanguage, targetLanguage,
            onSuccess = { translatedText ->
                _translatedText.value = translatedText
            },
            onFailure = { error ->
                _translatedText.value = error
            })
    }
    fun updateLanguagesHistory(selectedLanguage: String) {
        val updatedList = _languageHistory.value.toMutableList()
        updatedList.remove(selectedLanguage)
        updatedList.add(0, selectedLanguage)
        _languageHistory.value = updatedList.take(3)
    }

    fun addRecentInput(inputText: String) {
        if (inputText != "" && _recentInputs.value.firstOrNull() != inputText) {
            val updatedInputs = _recentInputs.value.toMutableList()
            updatedInputs.add(0, inputText)
            _recentInputs.value = updatedInputs.take(3)
        }
    }
}