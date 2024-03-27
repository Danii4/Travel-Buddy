package com.example.travelbuddy

import androidx.navigation.NavHostController

class NavWrapper {
    private var  _navController: NavHostController? = null
    fun setNavController(navigationController: NavHostController) {
        _navController = navigationController
    }

    fun getNavController(): NavHostController {
        return _navController!!
    }
}