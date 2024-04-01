package com.example.travelbuddy.landing_page.model


class LandingScreenPageModel {
    data class LandingScreenViewState(
        var tripCount: Int = 0,
        var userName: String = "",
        var lastTripName: String = ""
    )
}