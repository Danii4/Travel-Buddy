package com.example.travelbuddy.repository

import com.example.travelbuddy.data.model.CurrencyExchangeRateResponse
import retrofit2.Response
import retrofit2.http.GET

interface CurrencyExchangeRateApiService {
    @GET("v6/9fd3b26c375f907f96fd9e09/latest/USD")
    suspend fun getExchangeRates(): Response<CurrencyExchangeRateResponse>
}