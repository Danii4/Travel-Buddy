package com.example.travelbuddy.languageTranslation

import androidx.compose.ui.text.input.TextFieldValue
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
//    private val _inputText = MutableStateFlow("")
    private val _translatedText = MutableStateFlow("")
    val translatedText = _translatedText.asStateFlow()

//    fun setInputText(text: TextFieldValue) {
//        _inputText.value = text.toString()
//    }

    fun translateText(inputText: String, sourceLanguage: String, targetLanguage: String) {
        translationModel.performTranslation(inputText, sourceLanguage, targetLanguage,
            onSuccess = { translatedText ->
                _translatedText.value = translatedText
            },
            onFailure = { error ->
                _translatedText.value = error
            })
    }
}