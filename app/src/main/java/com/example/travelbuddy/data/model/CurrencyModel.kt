package com.example.travelbuddy.data.model

data class CurrencyExchangeRateResponse(
    val base_code: String? = null,
    val conversion_rates: Map<String, Double>? = null
)

data class CurrencyCountryResponse(
    val currencies: List<Currencies>? = null,
    val flags: Flags,
) {
    data class Flags(
        val svg: String,
        val png: String
    )
    // currencies":[{"code":"AOA","name":"Angolan kwanza","symbol":"Kz"}]
    data class Currencies(
        val code: String,
        val name: String,
        val symbol: String
    )
}

data class Currency(
    val code: String,
    val name: String,
    val symbol: String
)

fun getFlagUrlByCountryCode(currencyCode: String?, list: List<CurrencyCountryResponse>?): String? {
    if (currencyCode.isNullOrBlank() || list.isNullOrEmpty()) return null
    // Iterate through each CurrencyCountryResponse in the list
    for (item in list) {
        // Check if any currency code in the currencies list matches the currencyCode
        item.currencies?.any { it.code.equals(currencyCode, ignoreCase = true) }?.let { matches ->
            // If a match is found, return the flag SVG URL
            if (matches) {
                return item.flags.svg
            }
        }
    }
    // Return null if no match is found
    return null
}
