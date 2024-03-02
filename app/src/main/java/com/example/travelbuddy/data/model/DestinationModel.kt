package com.example.travelbuddy.data.model

import java.time.LocalDate

class DestinationModel {
    data class Destination(
        val Name: String = "",
        val startDate: LocalDate,
        val endDate: LocalDate,
    )
}