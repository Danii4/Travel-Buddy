package com.example.travelbuddy.trips.add_trips.views

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import androidx.navigation.NavController
import com.example.travelbuddy.trips.TripsViewModel
import com.example.travelbuddy.trips.model.TripAddPageModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue

@Composable
fun AddTripsPagerView(navController: NavController) {
    val titlePage = TripAddPageModel(
        page = {
            AddTripNameView()
        }
    )
    val addDestPage = TripAddPageModel(
        page = { innerPadding ->
            // AddDestinationsView
            AddEditDestinationView(innerPadding)
        }
    )
    val tripsAddScreenList = listOf(titlePage, addDestPage)
    GenerateTripAddViews(tripsAddScreenList, navController)
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