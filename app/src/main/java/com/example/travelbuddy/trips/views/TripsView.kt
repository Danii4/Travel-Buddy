@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.travelbuddy.trips.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.travelbuddy.R

@Composable
fun TripCard(
    trip: String
//    title: String,
//    budget: String,
//    itinerary: String
) {
    return Card {
            Image(
                painter = painterResource(id = R.drawable.payment_filled_24),
                contentDescription = stringResource(id = R.string.app_name)
            )
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = trip,
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
            ) {
                AssistChip(
                    onClick = { },
                    colors = AssistChipDefaults.assistChipColors(
                        leadingIconContentColor = MaterialTheme.colorScheme.onSurfaceVariant
                    ),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Outlined.List,
                            contentDescription = null
                        )
                    },
                    label = {
                        Text(text = "Itinerary")
                    }
                )
                Spacer(modifier = Modifier.width(50.dp))
                AssistChip(
                    onClick = { },
                    colors = AssistChipDefaults.assistChipColors(
                        leadingIconContentColor = MaterialTheme.colorScheme.onSurfaceVariant
                    ),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Outlined.List,
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
@Composable
fun TripsView() {
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp)) {
            TripCard(trip = "Barcelona")
            Spacer(modifier = Modifier.height(8.dp))
            TripCard(trip = "Madrid")
        }
}
