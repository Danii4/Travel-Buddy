package com.example.travelbuddy.data.model

class ResponseModel {
    sealed class Response(val error : String? = null) {
        class Success : Response()
        class Failure(error : String): Response(error = error)
        class Loading : Response()
    }
    sealed class ResponseWithData<T>(
        val data : T? = null,
        val error : String? = null
    ) {
        class Success<T>(data: T?): ResponseWithData<T>(data = data)
        class Failure<T>(data : T? = null, error: String?): ResponseWithData<T>(data = data, error = error)
        class Loading<T>(data: T? = null): ResponseWithData<T>(data = data)
    }
}
