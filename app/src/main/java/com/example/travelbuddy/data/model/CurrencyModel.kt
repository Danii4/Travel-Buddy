package com.example.travelbuddy.data.model

data class CurrencyExchangeRateResponse(
    val base_code: String? = null,
    val conversion_rates: Map<String, Double>? = null
)


