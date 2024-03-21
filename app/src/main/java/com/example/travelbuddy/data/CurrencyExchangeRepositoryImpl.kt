package com.example.travelbuddy.data

import com.example.travelbuddy.data.model.CurrencyExchangeRateResponse
import com.example.travelbuddy.data.model.ResponseModel
import com.example.travelbuddy.repository.CurrencyExchangeRateApiService
import com.example.travelbuddy.repository.CurrencyExchangeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CurrencyExchangeRepositoryImpl : CurrencyExchangeRepository {

    override val apiService: CurrencyExchangeRateApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://v6.exchangerate-api.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CurrencyExchangeRateApiService::class.java)
    }

    override fun getExchangeRates(): Flow<ResponseModel.ResponseWithData<CurrencyExchangeRateResponse>> = flow {
        emit(ResponseModel.ResponseWithData.Loading())
        val response = apiService.getExchangeRates()
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
}
