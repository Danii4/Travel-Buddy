package com.example.travelbuddy.languageTranslation

import android.util.Log
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.TranslatorOptions
import com.google.mlkit.nl.translate.Translation



object Translator {

    // maps languages to proper code
    private fun mapLanguageToCode(language: String): String {
        return when (language) {
            "English" -> TranslateLanguage.ENGLISH
            "French" -> TranslateLanguage.FRENCH
            "Spanish" -> TranslateLanguage.SPANISH
            "German" -> TranslateLanguage.GERMAN
            else -> TranslateLanguage.ENGLISH // Default
        }
    }

    // performs the translation
    fun performTranslation(
        inputText: String,
        sourceLanguage: String,
        targetLanguage: String,
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit
    ) {
        Log.d("Translator", "Starting translation from $sourceLanguage to $targetLanguage")


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
            Log.e("Translator", "ERROR DOWNLOADING MODEL")
            onFailure("Error downloading translation model: ${exception.localizedMessage}")
        }
    }
}