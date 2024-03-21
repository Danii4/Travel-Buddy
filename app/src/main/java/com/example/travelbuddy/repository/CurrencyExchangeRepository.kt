package com.example.travelbuddy.repository

import com.example.travelbuddy.data.model.CurrencyExchangeRateResponse
import com.example.travelbuddy.data.model.ResponseModel
import kotlinx.coroutines.flow.Flow

interface CurrencyExchangeRepository {
    val apiService: CurrencyExchangeRateApiService
    fun getExchangeRates(): Flow<ResponseModel.ResponseWithData<CurrencyExchangeRateResponse>>
}
