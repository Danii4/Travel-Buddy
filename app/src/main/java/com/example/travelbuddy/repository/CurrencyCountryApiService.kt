package com.example.travelbuddy.repository

import com.example.travelbuddy.data.model.CurrencyCountryResponse
import retrofit2.Response
import retrofit2.http.GET

interface CurrencyCountryApiService {
    @GET("v2/all")
    suspend fun getAllCountries(): Response<List<CurrencyCountryResponse>>

}