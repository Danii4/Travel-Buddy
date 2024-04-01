package com.example.travelbuddy.languageTranslation.model

import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.TranslatorOptions


class TranslationModel {
    // Performs the translation
    fun performTranslation(
        inputText: String,
        sourceLanguage: String,
        targetLanguage: String,
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit
    ) {

        val sourceLangCode = mapLanguageToCode(sourceLanguage)
        val targetLangCode = mapLanguageToCode(targetLanguage)

        val options = TranslatorOptions.Builder()
            .setSourceLanguage(sourceLangCode)
            .setTargetLanguage(targetLangCode)
            .build()
        val translator = Translation.getClient(options)

        translator.downloadModelIfNeeded().addOnSuccessListener {
            translator.translate(inputText)
                .addOnSuccessListener { translatedText ->
                    onSuccess(translatedText)
                }
                .addOnFailureListener { exception ->
                    onFailure("Error translating text: ${exception.localizedMessage}")
                }
        }.addOnFailureListener { exception ->
            onFailure("Error downloading translation model: ${exception.localizedMessage}")
        }
    }

    // Maps languages to proper code
    fun mapLanguageToCode(language: String): String {
        return when (language) {
            "Arabic" -> "ar"
            "Bengali" -> "bn"
            "Chinese" -> "zh"
            "English" -> "en"
            "French" -> "fr"
            "German" -> "de"
            "Hindi" -> "hi"
            "Indonesian" -> "id"
            "Italian" -> "it"
            "Japanese" -> "ja"
            "Korean" -> "ko"
            "Malay" -> "ms"
            "Marathi" -> "mr"
            "Persian" -> "fa"
            "Polish" -> "pl"
            "Portuguese" -> "pt"
            "Russian" -> "ru"
            "Spanish" -> "es"
            "Swahili" -> "sw"
            "Tamil" -> "ta"
            "Telugu" -> "te"
            "Thai" -> "th"
            "Turkish" -> "tr"
            "Urdu" -> "ur"
            "Vietnamese" -> "vi"
            "Dutch" -> "nl"
            "Greek" -> "el"
            "Hungarian" -> "hu"
            "Romanian" -> "ro"
            "Swedish" -> "sv"
            else -> "Unsupported Language"
        }
    }
}