package com.example.travelbuddy.data.model

import java.time.LocalDate

class DestinationModel {
    data class Metadata (
        val destName: String = "",
        val destStartDate: LocalDate,
        val destEndDate: LocalDate,
    )
}