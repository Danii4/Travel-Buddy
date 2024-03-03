package com.example.travelbuddy

import HomeScreen
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.outlined.ExitToApp
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.travelbuddy.data.Mock
import com.example.travelbuddy.expenses.add_edit_expense.views.AddEditExpenseView
import com.example.travelbuddy.expenses.views.ExpensesView
import com.example.travelbuddy.screens.TranslationScreen
import com.example.travelbuddy.screens.TripPlanningScreen
import com.example.travelbuddy.create_trip.views.CreateTripAddView
import com.example.travelbuddy.screens.UnitConversionScreen
import com.example.travelbuddy.ui.theme.TravelBuddyTheme
import com.example.travelbuddy.util.ImageType
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

data class DrawerItem(
    val label: String,
    val iconSelected: ImageType,
    val iconUnselected: ImageType,
    val screen: Screen
)

val DRAWER_ITEMS: List<DrawerItem> = listOf(
    DrawerItem(
        label = "Home",
        iconSelected = ImageType.Vector(Icons.Filled.Home),
        iconUnselected = ImageType.Vector(Icons.Outlined.Home),
        screen = Screen.Home,
    ),
    DrawerItem(
        label = "Trip planning",
        iconSelected = ImageType.Vector(Icons.Filled.LocationOn),
        iconUnselected = ImageType.Vector(Icons.Outlined.LocationOn),
        screen = Screen.TripPlanning,
    ),
    DrawerItem(
        label = "Trip Add Screen",
        iconSelected = ImageType.Vector(Icons.Filled.LocationOn),
        iconUnselected = ImageType.Vector(Icons.Outlined.LocationOn),
        screen = Screen.TripAdd,
    ),
    DrawerItem(
        label = "Expenses",
        iconSelected = ImageType.Drawable(R.drawable.payment_filled_24),
        iconUnselected =  ImageType.Drawable(R.drawable.payment_outline_24),
        screen = Screen.Expenses,
    ),
    DrawerItem(
        label = "Language Translation",
        iconSelected = ImageType.Vector(Icons.Filled.Phone),
        iconUnselected = ImageType.Vector(Icons.Outlined.Phone),
        screen = Screen.LanguageTranslation,
    ),
    DrawerItem(
        label = "Unit Conversion",
        iconSelected = ImageType.Vector(Icons.Filled.ExitToApp),
        iconUnselected = ImageType.Vector(Icons.Outlined.ExitToApp),
        screen = Screen.UnitConversion,
    ),
)

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TravelBuddyTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
                    val scope = rememberCoroutineScope()
                    var selectedItemIndex by rememberSaveable { mutableStateOf(0) }
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
                                startDestination = Screen.Home.route,
                                modifier = Modifier.padding(paddingValues)
                            ) {
                                composable(Screen.Expenses.route) { ExpensesView(
                                    navController = navController,
                                    trip = Mock.trip
                                ) }
                                composable(Screen.Home.route) { HomeScreen() }
                                composable(Screen.LanguageTranslation.route) { TranslationScreen() }
                                composable(Screen.TripPlanning.route) { TripPlanningScreen() }
                                composable(Screen.TripAdd.route) { CreateTripAddView()}
                                composable(Screen.UnitConversion.route) { UnitConversionScreen() }
                                composable(Screen.AddEditExpense.route) { AddEditExpenseView(
                                    navController = navController,
                                    trip = Mock.trip) }
                            }
                        }
                    }
                }
            }
        }
    }
}