package com.example.travelbuddy.domain.model

sealed class Response<T>(val data: T? = null, val msg: String? = null) {
    class Success<T>(data: T) : Response<T>(data)
    class Failure<T>(data: T? = null, msg: String) : Response<T>(data, msg)
    class Loading<T>(data: T? = null) : Response<T>(data)
}
