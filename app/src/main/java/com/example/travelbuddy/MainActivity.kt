package com.example.travelbuddy

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
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

data class DrawerItem(
    val label: String,
    val iconSelected: ImageType,
    val iconUnselected: ImageType,
    val screen: Screen
)

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<MainViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
                    ModalNavigationDrawer(
                        drawerContent = {
                            ModalDrawerSheet {
                                Spacer(modifier = Modifier.height(16.dp))
                                DRAWER_ITEMS.forEachIndexed { index, item ->
                                    NavigationDrawerItem(
                                        label = { Text(text = item.label) },
                                        selected = index == selectedItemIndex,
                                        onClick = {
                                            selectedItemIndex = index
                                            navController.navigate(item.screen.route)
                                            scope.launch {
                                                drawerState.close()
                                            }
                                        },
                                        icon = {
                                            Icon(
                                                imageVector =
                                                if (index == selectedItemIndex) { item.iconSelected.resolveImage() }
                                                else { item.iconUnselected.resolveImage() },
                                                contentDescription = item.label
                                            )
                                        },
                                        modifier = Modifier
                                            .padding(NavigationDrawerItemDefaults.ItemPadding)
                                    )
                                }
                            }
                        },
                        drawerState = drawerState
                    ) {
                        Scaffold(
                            modifier = Modifier
                                .fillMaxHeight()
                                .fillMaxWidth(),
                            topBar = {
                                TopAppBar(
                                    title = {
                                        Row {
                                            Icon(
                                                imageVector = ImageVector.vectorResource(id = R.drawable.travel_explore_24),
                                                contentDescription = "Menu",
                                                modifier = Modifier.padding(2.dp)
                                            )
                                            Text(
                                                text = DRAWER_ITEMS[selectedItemIndex].label,
                                                fontSize = 20.sp
                                            )
                                        }
                                    },
                                    navigationIcon = {
                                        IconButton(onClick = {
                                            scope.launch {
                                                drawerState.open()
                                            }
                                        }) {
                                            Icon(
                                                imageVector = Icons.Default.Menu,
                                                contentDescription = "Menu"
                                            )
                                        }
                                    }
                                )
                            }
                        ){ paddingValues ->
                            NavHost(
                                navController = navController,
                                startDestination = Screen.Login.route,
                                modifier = Modifier.padding(paddingValues)
                            ) {
                                composable(Screen.Login.route) { LoginScreen(navController = navController) }
                                composable(Screen.Expenses.route) { ExpensesView(navController = navController, trip = Mock.trip) }
                                composable(Screen.Home.route) { HomeScreen() }
                                composable(Screen.LanguageTranslation.route) { TranslationScreen() }
                                composable(Screen.Trips.route) { TripsView(navController) }
                                composable(Screen.TripAdd.route) { CreateTripAddView()}
                                composable(Screen.UnitConversion.route) { UnitConversionScreen() }
                                composable(Screen.AddEditExpense.route) { AddEditExpenseView(
                                    navController = navController,
                                    trip = Mock.trip) }
                            }
                        }
                    }
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