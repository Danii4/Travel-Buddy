@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.travelbuddy.trips.views

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Flight
import androidx.compose.material.icons.filled.Route
import androidx.compose.material.icons.outlined.AttachMoney
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.travelbuddy.create_trip.CreateTripAddViewModel
import com.example.travelbuddy.trips.TripsViewModel
import com.example.travelbuddy.expenses.add_edit_expense.AddEditExpenseViewModel
import com.example.travelbuddy.trips.model.TripAddPageModel
import com.example.travelbuddy.create_trip.views.CreateTripAddView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue

class TripsViewModel : ViewModel() {
    var trips = mutableStateListOf<String>()
}

@Composable
fun TripCard(
    trip: String,
//    title: String,
//    budget: String,
//    itinerary: String
    navController: NavController
) {
    return Card(modifier = Modifier
        .padding(4.dp),) {
        val expenseViewModel = AddEditExpenseViewModel()
        val addTripViewModel = hiltViewModel<CreateTripAddViewModel>()


        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(
                text = trip,
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
            ) {
                AssistChip(
                    onClick = { addTripViewModel.navigateToCreateTripAdd(navController) },
                    colors = AssistChipDefaults.assistChipColors(
                        leadingIconContentColor = MaterialTheme.colorScheme.onSurfaceVariant
                    ),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Flight,
                            contentDescription = null
                        )
                    },
                    label = {
                        Text(text = "Destinations")
                    }
                )
                Spacer(modifier = Modifier.width(100.dp))
                AssistChip(
                    onClick = { expenseViewModel.navigatetoExpenses(navController) },
                    colors = AssistChipDefaults.assistChipColors(
                        leadingIconContentColor = MaterialTheme.colorScheme.onSurfaceVariant
                    ),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Outlined.AttachMoney,
                            contentDescription = null
                        )
                    },
                    label = {
                        Text(text = "Budget")
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TripsView(
    navController: NavController
) {

    var tripTitle by remember {
        mutableStateOf("")
    }
    var tripTitleActive by remember {
        mutableStateOf(false)
    }

    val trip1 = "Grad Trip"
    val trip2 = "Month in Europe"

    val tripList = remember {
        mutableStateListOf(trip1, trip2)
    }

    fun deleteTrip(trip: String) {
        tripList.remove(trip)
    }


    Scaffold { paddingValues ->
        LazyColumn(
            modifier = Modifier.padding(paddingValues),
            userScrollEnabled = true
        ) {
            items(tripList) { trip: String ->
                TripCard(trip = trip, navController)
                Spacer(modifier = Modifier.height(5.dp))
            }
        }
        val sheetState = rememberModalBottomSheetState()
        var isSheetOpen by rememberSaveable {
            mutableStateOf(false)
        }
        var showPager by remember { mutableStateOf(false) }

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            Button(
                onClick = {
                    isSheetOpen = true
                    showPager = true
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xff5cb85c)
                )
            ) {
                Text(text = "New Trip")
            }
        }
        if (showPager) {
            val titlePage = TripAddPageModel(
                page = { innerPadding ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Box(modifier = Modifier.padding(vertical = 90.dp)){
                            Icon(
                                imageVector = Icons.Default.Route,
                                contentDescription = "Info Icon",
                                modifier = Modifier
                                    .size(100.dp)
                            )
                        }
                        Text(
                            text = "Enter Trip Name",
                            modifier = Modifier.padding(10.dp)
                        )
                        ElevatedCard(
                            modifier = Modifier.fillMaxWidth(0.75f)
                        ) {
                            TextField(
                                modifier = Modifier.fillMaxWidth(),
                                value = tripTitle,
                                onValueChange = { tripTitle = it },
                            )
                        }
                    }
                }
            )
            val addDestPage = TripAddPageModel(
                page = { innerPadding ->
                    CreateTripAddView(innerPadding)
                }
            )
            val tripsAddScreenList = listOf(titlePage, addDestPage)
            GenerateTripAddViews(tripsAddScreenList, navController)
        }
//        if (isSheetOpen) {
//            ModalBottomSheet(
//                sheetState = sheetState,
//                onDismissRequest = {
//                    isSheetOpen = false
//                }
//            )
//            {
//                Column(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(paddingValues),
//                ) {
//                    Text(
////            text = stringResource(id = R.string.medication_name),
//                        text = "Title",
//                        style = MaterialTheme.typography.bodyLarge
//                    )
//                    Spacer(modifier = Modifier.height(10.dp))
//
//                    TextField(
//                        modifier = Modifier.fillMaxWidth(),
//                        value = tripTitle,
//                        onValueChange = { tripTitle = it },
////            placeholder = { Text(text = "e.g. Hexamine") },
//                    )
//                    Spacer(modifier = Modifier.height(20.dp))
//
//
//                    Row(
//                        horizontalArrangement = Arrangement.SpaceBetween,
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(horizontal = 10.dp)
//                    ) {
//
//                        Box(
//                            Modifier
//                                .weight(1f)
//                                .padding(start = 8.dp),
//                        ) {
//                            Button(
//                                onClick = {
//                                    tripTitleActive = false
//                                    if (tripTitle.isNotBlank()) {
//                                        tripList.add(tripTitle)
//                                        tripTitle = ""
//                                        isSheetOpen=false
//                                    }
//                                },
//                                modifier = Modifier.fillMaxWidth(),
//                                shape = RoundedCornerShape(10.dp),
//                                colors = ButtonDefaults.buttonColors(
//                                    containerColor = Color(0xff5cb85c)
//                                )
//                            ) {
//                                Text("Confirm")
//                            }
//                        }
//                    }
//                }
//            }
//        }
    }
}

// Cited from: https://medium.com/@domen.lanisnik/exploring-the-official-pager-in-compose-8c2698c49a98
@Composable
private fun HorizontalPagerIndicator(
    pageCount: Int,
    currentPage: Int,
    targetPage: Int,
    currentPageOffsetFraction: Float,
    modifier: Modifier = Modifier,
    indicatorColor: Color = Color.DarkGray,
    unselectedIndicatorSize: Dp = 8.dp,
    selectedIndicatorSize: Dp = 10.dp,
    indicatorCornerRadius: Dp = 2.dp,
    indicatorPadding: Dp = 2.dp
) {
    Row(
        modifier = modifier
            .padding(15.dp)
    ) {

        // draw an indicator for each page
        repeat(pageCount) { page ->
            // calculate color and size of the indicator
            val (color, size) =
                if (currentPage == page || targetPage == page) {
                    // calculate page offset
                    val pageOffset =
                        ((currentPage - page) + currentPageOffsetFraction).absoluteValue
                    // calculate offset percentage between 0.0 and 1.0
                    val offsetPercentage = 1f - pageOffset.coerceIn(0f, 1f)

                    val size =
                        unselectedIndicatorSize + ((selectedIndicatorSize - unselectedIndicatorSize) * offsetPercentage)

                    indicatorColor.copy(
                        alpha = offsetPercentage
                    ) to size
                } else {
                    indicatorColor.copy(alpha = 0.1f) to unselectedIndicatorSize
                }

            // draw indicator
            Box(
                modifier = Modifier
                    .padding(
                        // apply horizontal padding, so that each indicator is same width
                        horizontal = ((selectedIndicatorSize + indicatorPadding * 2) - size) / 2,
                        vertical = size / 4
                    )
                    .clip(RoundedCornerShape(indicatorCornerRadius))
                    .background(color)
                    .width(2 * size)
                    .height((4 * size) / 7)
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ModifiedRow(
    pagerState: PagerState,
    coroutineScope: CoroutineScope,
    navController: NavController
) {
    Row(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .padding(bottom = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // Backward Nav
        if (pagerState.canScrollBackward) {
            Box(
                modifier = Modifier
                    .padding(horizontal = 15.dp, vertical = 10.dp)
                    .clickable {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage - 1)
                        }
                    }
            ) {
                Text("Back")
            }
        } else {
            Box(
                modifier = Modifier
                    .padding(horizontal = 15.dp, vertical = 10.dp)
                    .clickable {
                        TripsViewModel().navigateToTrips(navController)
                    }
            ) {
                Text("Cancel")
            }
        }

        // Indicators
        HorizontalPagerIndicator(
            pageCount = pagerState.pageCount,
            currentPage = pagerState.currentPage,
            targetPage = pagerState.targetPage,
            currentPageOffsetFraction = pagerState.currentPageOffsetFraction
        )

        // Forward Nav
        if (pagerState.canScrollForward){
            Box(
                modifier = Modifier
                    .padding(horizontal = 15.dp, vertical = 10.dp)
                    .clickable {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        }
                    }
            ) {
                Text("Next")
            }
        } else {
            Box(
                modifier = Modifier
                    .padding(horizontal = 15.dp, vertical = 10.dp)
                    .clickable {
                        TripsViewModel().navigateToTrips(navController)
                    }
            ) {
                Text("Done")
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun GenerateTripAddViews(pages: List<TripAddPageModel>, navController: NavController) {
    val pagerState = rememberPagerState(pageCount = { pages.size })
    val coroutineScope = rememberCoroutineScope()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        HorizontalPager(state = pagerState) { index ->
            Scaffold(
                bottomBar = {
                    ModifiedRow(pagerState, coroutineScope, navController)
                },
                content = {innerPadding ->
                    pages[index].page(innerPadding)
                }
            )
        }
    }
}