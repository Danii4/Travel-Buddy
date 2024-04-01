package com.example.travelbuddy.expenses.add_edit_expense.views

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.AbsoluteAlignment
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.travelbuddy.CustomColors
import com.example.travelbuddy.data.model.Currency
import com.example.travelbuddy.data.model.ExpenseModel
import com.example.travelbuddy.expenses.add_edit_expense.AddEditExpenseViewModel
import com.github.nkuppan.countrycompose.presentation.currency.CountryCurrencySelectionDialog
import java.math.BigDecimal

@SuppressLint("NewApi")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun AddEditExpenseView(
) {
    val viewModel = hiltViewModel<AddEditExpenseViewModel>()
    val state by viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                navigationIcon = {
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        modifier = Modifier.clickable { viewModel.navigateBack() }
                    )
                },
                title = {
                    Text(
                        text = "Add Expense",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            )
        }
    ) { paddingValues ->
        var expanded by remember { mutableStateOf(false) }
        var currencySelection by remember { mutableStateOf(false) }

        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(15.dp),

            ) {
            Spacer(modifier = Modifier.height(45.dp))
            ElevatedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        width = 2.dp,
                        color = CustomColors.Indigo,
                        shape = RoundedCornerShape(16.dp)
                    )
            ) {
                TextField(
                    label = { Text(text = "Name") },
                    value = state.name,
                    onValueChange = { viewModel.setExpenseName(it) },
                    colors = TextFieldDefaults.textFieldColors(
                        cursorColor = MaterialTheme.colorScheme.primary,
                        focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                        focusedLabelColor = MaterialTheme.colorScheme.primary,
                        focusedSupportingTextColor = MaterialTheme.colorScheme.primary,
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
            }

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
                        imageVector = state.type.icon,
                        contentDescription = null,
                        tint = CustomColors.Indigo,
                        modifier = Modifier.size(27.dp)
                    )
                    // Expense type dropdown
                    Box(
                        modifier = Modifier.weight(1f), contentAlignment = Alignment.CenterStart
                    ) {
                        ExposedDropdownMenuBox(
                            expanded = expanded, onExpandedChange = {
                                expanded = !expanded
                            },
                            modifier = Modifier.height(50.dp)
                        ) {
                            TextField(
                                value = state.type.displayValue,
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
                                ExpenseModel.ExpenseType.values().forEach { item ->
                                    DropdownMenuItem(text = {
                                        Text(
                                            text = item.displayValue,
                                            overflow = TextOverflow.Ellipsis,
                                            maxLines = 1,
                                            textAlign = TextAlign.Start,
                                            modifier = Modifier.width(IntrinsicSize.Max)
                                        )
                                    }, onClick = {
                                        viewModel.setExpenseType(item)
                                        expanded = false
                                    })
                                }
                            }
                        }
                    }
                }
            }
            HorizontalDivider( thickness = 2.dp)
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
                        text = state.currency.code!!,
                        textAlign = TextAlign.Right
                    )
                    Text(
                        text = "${state.currency.symbol} ${state.currency.name}",
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
                    viewModel.setExpenseCurrency(country.currency!!)
                    currencySelection = !currencySelection
                }
            }


            ElevatedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        width = 2.dp,
                        color = CustomColors.Indigo,
                        shape = RoundedCornerShape(16.dp)
                    )
            ) {
                // Text field for entering expense amount
                TextField(
                    label = { Text(text = "Amount") },
                    value = state.amount,
                    onValueChange = { viewModel.setExpenseAmount(it) },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    colors = TextFieldDefaults.textFieldColors(
                        cursorColor = MaterialTheme.colorScheme.primary,
                        focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                        focusedLabelColor = MaterialTheme.colorScheme.primary,
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
            }
            Row(

            ) {
                if (!viewModel.getExpenseId().isNullOrBlank()) {
                    Button(
                        onClick = {
                            viewModel.deleteExpense()
                            viewModel.navigateBack()
                        },
                        modifier = Modifier.fillMaxWidth(0.5f)
                    ) {
                        Text(text = "Delete")
                    }
                }
                Button(
                    onClick = {
                        val id = viewModel.getExpenseId() ?: ""
                        val newExpense = ExpenseModel.Expense(
                            id = id,
                            name = state.name,
                            type = state.type,
                            amount = BigDecimal(state.amount),
                            date = state.date,
                            currency = Currency(
                                code = state.currency.code,
                                name = state.currency.name,
                                symbol = state.currency.symbol
                            )
                        )
                        viewModel.submitExpense(newExpense)
                    },
                    modifier = Modifier.fillMaxWidth(1f)
                ) {
                    Text(text = "Save")
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            Divider(modifier = Modifier.fillMaxWidth())
        }



        Spacer(modifier = Modifier.height(20.dp))
        Divider(modifier = Modifier.fillMaxWidth())
    }
}
