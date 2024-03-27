package com.example.travelbuddy.repository

import com.example.travelbuddy.data.model.CurrencyCountryResponse
import com.example.travelbuddy.data.model.CurrencyExchangeRateResponse
import com.example.travelbuddy.data.model.ResponseModel
import kotlinx.coroutines.flow.Flow

interface CurrencyExchangeRepository {
    val exchangeApiService: CurrencyExchangeRateApiService

    val countryFlagApiService: CurrencyCountryApiService
    fun getExchangeRates(): Flow<ResponseModel.ResponseWithData<CurrencyExchangeRateResponse>>

     fun getCountryFlags(): Flow<ResponseModel.ResponseWithData<List<CurrencyCountryResponse>>>
}
