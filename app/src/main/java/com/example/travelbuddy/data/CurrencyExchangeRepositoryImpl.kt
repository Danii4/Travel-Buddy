package com.example.travelbuddy.data

import android.util.Log
import com.example.travelbuddy.data.model.CurrencyCountryResponse
import com.example.travelbuddy.data.model.CurrencyExchangeRateResponse
import com.example.travelbuddy.data.model.ResponseModel
import com.example.travelbuddy.repository.CurrencyCountryApiService
import com.example.travelbuddy.repository.CurrencyExchangeRateApiService
import com.example.travelbuddy.repository.CurrencyExchangeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CurrencyExchangeRepositoryImpl : CurrencyExchangeRepository {

    override val exchangeApiService: CurrencyExchangeRateApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://v6.exchangerate-api.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CurrencyExchangeRateApiService::class.java)
    }


    override val countryFlagApiService: CurrencyCountryApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://restcountries.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CurrencyCountryApiService::class.java)
    }

    override fun getExchangeRates(): Flow<ResponseModel.ResponseWithData<CurrencyExchangeRateResponse>> = flow {
        emit(ResponseModel.ResponseWithData.Loading())
        val response = exchangeApiService.getExchangeRates()
        if (response.isSuccessful) {
            response.body()?.let {
                emit(ResponseModel.ResponseWithData.Success(it))
            } ?: throw Exception("No data available")
        } else {
            throw Exception("Error fetching exchange rates: ${response.message()}")
        }
    }.catch { e ->
        emit(ResponseModel.ResponseWithData.Failure(error = e.message))
    }

    override fun getCountryFlags(): Flow<ResponseModel.ResponseWithData<List<CurrencyCountryResponse>>> = flow {
        emit(ResponseModel.ResponseWithData.Loading())
        val response = countryFlagApiService.getAllCountries()
        Log.d("RESPONSE IS: ", response.toString())
        if (response.isSuccessful) {
            response.body()?.filterNotNull()?.let {
                emit(ResponseModel.ResponseWithData.Success(it))
            } ?: throw Exception("No data available")
        } else {
            throw Exception("Error fetching country flag data: ${response?.message()}")
        }
    }.catch { e ->
        emit(ResponseModel.ResponseWithData.Failure(error = e.message))
    }
}
