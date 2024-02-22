package com.example.travelbuddy

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.ExitToApp
import androidx.compose.material.icons.outlined.Face
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material.icons.outlined.Settings
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.travelbuddy.ui.theme.TravelBuddyTheme
import com.example.travelbuddy.util.ImageType
import kotlinx.coroutines.launch

data class DrawerItem(
    val label: String,
    val iconSelected: ImageType,
    val iconUnselected: ImageType,
)

val DRAWER_ITEMS: List<DrawerItem> = listOf(
    DrawerItem(
        label = "Home",
        iconSelected = ImageType.Vector(Icons.Filled.Home),
        iconUnselected = ImageType.Vector(Icons.Outlined.Home),
    ),
    DrawerItem(
        label = "Trip planning",
        iconSelected = ImageType.Vector(Icons.Filled.LocationOn),
        iconUnselected = ImageType.Vector(Icons.Outlined.LocationOn),
    ),
    DrawerItem(
        label = "Budget Tracking",
        iconSelected = ImageType.Drawable(R.drawable.payment_filled_24),
        iconUnselected =  ImageType.Drawable(R.drawable.payment_outline_24),
    ),
    DrawerItem(
        label = "Translation",
        iconSelected = ImageType.Vector(Icons.Filled.Phone),
        iconUnselected = ImageType.Vector(Icons.Outlined.Phone),
    ),
    DrawerItem(
        label = "Unit Conversion",
        iconSelected = ImageType.Vector(Icons.Filled.ExitToApp),
        iconUnselected = ImageType.Vector(Icons.Outlined.ExitToApp),
    ),
)


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
                                                text = "Travel Buddy"
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
                        ) {}
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TravelBuddyTheme {
        Greeting("Android")
    }
}