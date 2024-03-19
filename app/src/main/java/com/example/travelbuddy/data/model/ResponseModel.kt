package com.example.travelbuddy.data.model

class ResponseModel {
    sealed class Response(val error : String? = null) {
        class Success : Response()
        class Failure(error : String): Response(error = error)
    }
    sealed class ResponseWithData<T>(
        val data : T? = null,
        val error : String? = null
    ) {
        class Success<T>(data: T?): ResponseWithData<T>(data = data)
        class Failure<T>(error: String?): ResponseWithData<T>(error = error)

        class Loading<T>(data: T?): ResponseWithData<T>(data = data)
    }
}
