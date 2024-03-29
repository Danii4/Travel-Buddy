package com.example.travelbuddy.util

import java.math.BigDecimal

data class Money(
    var amount: BigDecimal,
    val currencyCode: String,
    val displayAmount: BigDecimal? = null
)


