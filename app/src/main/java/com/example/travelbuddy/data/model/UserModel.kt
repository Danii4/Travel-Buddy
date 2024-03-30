package com.example.travelbuddy.data.model

class UserModel {
    data class User(
        val id: String,
        val email: String,
        val name: String,
        val tripsIdList: MutableList<String> = emptyList<String>().toMutableList()
    )
}