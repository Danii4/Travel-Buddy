package com.example.travelbuddy.trips.add_trips.views

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.travelbuddy.data.model.ExpenseModel
import com.example.travelbuddy.languageTranslation.CustomColors
import com.example.travelbuddy.trips.add_trips.AddTripsViewModel
import com.github.nkuppan.countrycompose.presentation.currency.CountryCurrencySelectionDialog
import java.math.BigDecimal

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BudgetCard(expenseType: ExpenseModel.ExpenseType, amount: BigDecimal) {
    var expanded by remember { mutableStateOf(false) }
    var selectedExpenseType by remember { mutableStateOf(expenseType) }
    var displayAmount by remember { mutableStateOf(TextFieldValue(amount.toString())) }
    val viewModel = hiltViewModel<AddTripsViewModel>()

    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 2.dp,
                color = CustomColors.Indigo,
                shape = RoundedCornerShape(16.dp)
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                imageVector = expenseType.icon,
                contentDescription = null,
                tint = CustomColors.Indigo,
                modifier = Modifier.size(27.dp)
            )
            // Expense type dropdown
            Box(
                modifier = Modifier.weight(1f), contentAlignment = Alignment.CenterStart
            ) {
                ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = {
                    expanded = !expanded
                },
                    modifier = Modifier.height(50.dp)) {
                    TextField(
                        value = selectedExpenseType.displayValue,
                        onValueChange = {},
                        maxLines = 1,
                        readOnly = true,
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(
                                expanded = expanded
                            )
                        },
                        textStyle = TextStyle(fontSize = 15.sp),
                        modifier = Modifier
                            .menuAnchor()
                            .padding(0.dp)
                    )

                    ExposedDropdownMenu(expanded = expanded, onDismissRequest = {
                        expanded = false
                    }) {
                        viewModel.getExpenseTypes().forEach { item ->
                            DropdownMenuItem(text = {
                                Text(
                                    text = item.displayValue,
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
            }, keyboardOptions = KeyboardOptions(
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
            ), modifier = Modifier.width(100.dp)
            )
        }

    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddBudgetsView(
    innerPadding: PaddingValues = PaddingValues(10.dp),
) {
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
            modifier = Modifier
                .clickable {
                    currencySelection = !currencySelection
                }

                .border(
                    width = 2.dp,
                    color = CustomColors.LightIndigo,
                    shape = RoundedCornerShape(16.dp)
                )
        ) {
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp),
                horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(
                        text = state.defaultCurrency.code!!,
                        textAlign = TextAlign.Right
                    )
                    Text(
                        text = "${state.defaultCurrency.symbol} ${state.defaultCurrency.name}",
                        textAlign = TextAlign.Right
                    )
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
        Spacer(modifier = Modifier.height(12.5.dp))
        HorizontalDivider( thickness = 2.dp)
        Spacer(modifier = Modifier.height(12.5.dp))

        LazyColumn(
            userScrollEnabled = true,
            verticalArrangement = Arrangement.spacedBy(15.dp),
            modifier = Modifier
                .weight(1f, fill = false)
                .fillMaxWidth(),
        ) {
            items(state.budgets) { (expenseType, amount) ->
                BudgetCard(expenseType, amount)
            }
        }

        Spacer(modifier = Modifier.height(15.dp))
        if (viewModel.getExpenseTypes().isNotEmpty()) {
            Button(
                onClick = {
                    // Add a new row for expense type and number input
                    viewModel.initializeBudget()
                },
                modifier = Modifier
                    .height(50.dp)
                    .fillMaxWidth(),

                ) {
                Text("Add Budget")
            }
        }
        Spacer(modifier = Modifier.height(25.dp))
    }
}