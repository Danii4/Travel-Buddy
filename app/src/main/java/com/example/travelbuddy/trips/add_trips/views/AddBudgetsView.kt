package com.example.travelbuddy.trips.add_trips.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.AbsoluteAlignment
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.travelbuddy.trips.add_trips.AddTripsViewModel
import com.github.nkuppan.country.domain.model.Country
import com.github.nkuppan.country.domain.repository.CountryRepository
import com.github.nkuppan.countrycompose.presentation.country.CountrySelectionDialog
import com.github.nkuppan.countrycompose.presentation.currency.CountryCurrencySelectionDialog
import com.github.nkuppan.countrycompose.ui.components.CountryWithCurrencyItemViewPreview


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddBudgetsView() {
    val viewModel = hiltViewModel<AddTripsViewModel>()
    val state by viewModel.state.collectAsState()

    var currencySelection by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier.clickable {
                currencySelection = !currencySelection
            }
                .align(AbsoluteAlignment.Right)
        ) {
            Row(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .fillMaxWidth()
                ) {
                    Text(text = state.defaultCurrency.code!!)
                    Text(text = state.defaultCurrency.symbol!!)
                }
            }
        }
        if (currencySelection) {
            CountryCurrencySelectionDialog(
                onDismissRequest = {
                    currencySelection = !currencySelection
                }
            ) { country ->
                viewModel.setDefaultCurrency(country.currency!!)
                currencySelection = !currencySelection
            }
        }
        state.budgets.forEach { (expenseType, amount) ->
            var expanded by remember { mutableStateOf(false) }
            var selectedExpenseType by remember { mutableStateOf(expenseType) }
            var displayAmount by remember { mutableStateOf(TextFieldValue(amount.toString())) }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(vertical = 8.dp)
            ) {
                // Expense type dropdown
                Box(
                    modifier = Modifier.weight(1f), contentAlignment = Alignment.CenterStart
                ) {
                    ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = {
                        expanded = !expanded
                    }) {
                        TextField(
                            value = selectedExpenseType.stringValue,
                            onValueChange = {},
                            maxLines = 1,
                            readOnly = true,
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                            modifier = Modifier.menuAnchor()
                        )

                        ExposedDropdownMenu(expanded = expanded, onDismissRequest = {
                            expanded = false
                        }) {
                            viewModel.getExpenseTypes().forEach { item ->
                                DropdownMenuItem(text = {
                                    Text(
                                        text = item.stringValue,
                                        overflow = TextOverflow.Ellipsis,
                                        maxLines = 1,
                                        textAlign = TextAlign.Start,
                                        modifier = Modifier.width(IntrinsicSize.Max)
                                    )
                                }, onClick = {
                                    selectedExpenseType = item
                                    viewModel.updateBudget(
                                        expenseType, selectedExpenseType, displayAmount.text
                                    )
                                    expanded = false
                                })
                            }
                        }
                    }
                }
                val focusManager = LocalFocusManager.current
                // Amount input field
                OutlinedTextField(value = displayAmount, onValueChange = {
                    displayAmount = it
                }, label = { Text("Enter amount") }, keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number, imeAction = ImeAction.Done
                ), keyboardActions = KeyboardActions(
                    onDone = {
                        // Add input to budget
                        focusManager.clearFocus()
                        viewModel.updateBudget(
                            expenseType,
                            selectedExpenseType,
                            displayAmount.text
                        )
                    },
                ), modifier = Modifier.weight(0.65f)
                )
            }
        }
        if (viewModel.getExpenseTypes().isNotEmpty()) {
            // Add budget button
            Button(
                onClick = {
                    // Add a new row for expense type and number input
                    viewModel.initializeBudget()
                }, modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth(0.5f)
            ) {
                Text("Add Budget")
            }
        }
    }
}