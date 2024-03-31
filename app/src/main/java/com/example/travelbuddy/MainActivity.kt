package com.example.travelbuddy

import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.rememberNavController
import com.example.travelbuddy.ui.theme.TravelBuddyTheme
import com.example.travelbuddy.util.ImageType
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import com.example.travelbuddy.amadeus_client.AmadeusClient

data class DrawerItem(
    val label: String,
    val iconSelected: ImageType,
    val iconUnselected: ImageType,
    val screen: Screen
)

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var navWrapper: NavWrapper

    @Inject
    lateinit var amadeusClient: AmadeusClient

    private val viewModel by viewModels<MainViewModel>()
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        amadeusClient.setActivityContext(this)
        setContent {
            val loggedIn: Boolean = viewModel.getLoggedInStatus()
            val currentUserName = viewModel.getCurrentUserName()
            val context = LocalContext.current

            if(currentUserName != null) {
                Toast.makeText(context, "Welcome back: $currentUserName", Toast.LENGTH_SHORT)
            }
            TravelBuddyTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    navWrapper.setNavController(navController)

                    Navigation(
                        loggedIn = loggedIn,
                        modifier = Modifier.padding(),
                        navController = navController
                    )
                }
            }
        }
    }
}