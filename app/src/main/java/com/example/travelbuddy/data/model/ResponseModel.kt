package com.example.travelbuddy.data.model

sealed class ResponseModel<T>(val data: T? = null, val msg: String? = null) {
    class Success<T>(data: T) : ResponseModel<T>(data)
    class Failure<T>(data: T? = null, msg: String) : ResponseModel<T>(data, msg)
    class Loading<T>(data: T? = null) : ResponseModel<T>(data)
}
