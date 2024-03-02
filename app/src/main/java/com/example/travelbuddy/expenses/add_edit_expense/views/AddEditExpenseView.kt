package com.example.travelbuddy.expenses.add_edit_expense.views

import android.annotation.SuppressLint
import android.widget.DatePicker
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.travelbuddy.data.model.ExpenseModel
import com.example.travelbuddy.data.model.TripModel
import com.example.travelbuddy.expenses.ExpensesViewModel
import com.example.travelbuddy.expenses.add_edit_expense.AddEditExpenseViewModel
import java.time.LocalDate

@SuppressLint("NewApi")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun AddEditExpenseView( navController: NavController, trip: TripModel.Trip) {
    val viewModel = AddEditExpenseViewModel()
//    val viewModel = hiltViewModel<AddEditExpenseViewModel>()
//    val state by viewModel.state.collectAsState()
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        // text = if (state.selectedMarket == null) "Add Market" else "Edit - ${state.selectedMarket!!.name}",
                        text = "Add Expense",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            )
        }
    ) { paddingValues ->
        var expenseName by remember { mutableStateOf("") }
        var expenseType by remember { mutableStateOf(ExpenseModel.ExpenseType.MISCELLANEOUS) }
        var expenseAmount by remember { mutableStateOf(0.0f) }
        var expenseDate by remember { mutableStateOf(LocalDate.now()) }
        var expanded by remember { mutableStateOf(false) }

        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(20.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
        ) {

            expenseName.let {
                TextField(
                    label = { Text(text = "Name") },
                    value = it,
                    onValueChange = { expenseName = it },
                    colors = TextFieldDefaults.textFieldColors(
                        cursorColor = MaterialTheme.colorScheme.primary,
                        focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                        focusedLabelColor = MaterialTheme.colorScheme.primary,
                        focusedSupportingTextColor = MaterialTheme.colorScheme.primary,
                    ),
                    modifier = Modifier.fillMaxWidth(),
                )
            }

            Spacer(modifier = Modifier.height(20.dp))
            // Dropdown menu for selecting expense type
            ExposedDropdownMenuBox(
                expanded = true,
                onExpandedChange = {
                    expanded = !expanded
                },
                modifier = Modifier.fillMaxWidth()) {
                TextField(
                    value = expenseType.stringValue,
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    modifier = Modifier.menuAnchor()
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    ExpenseModel.ExpenseType.values().forEach { type ->
                        DropdownMenuItem(
                            text = { Text(text = type.stringValue) },
                            onClick = {
                                expenseType = type
                                expanded = false
                            })
                    }
                }
            }

            // Display the selected expense type
            Text(
                text = "Type: ${expenseType.name}",
                modifier = Modifier.padding(vertical = 8.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Text field for entering expense amount
            TextField(
                label = { Text(text = "Amount ($)") },
                value = expenseAmount.toString(),
                onValueChange = { expenseAmount = it.toFloatOrNull() ?: 0.0f },
                colors = TextFieldDefaults.textFieldColors(
                    cursorColor = MaterialTheme.colorScheme.primary,
                    focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                    focusedLabelColor = MaterialTheme.colorScheme.primary,
                ),
                modifier = Modifier.fillMaxWidth(),
            )

            Spacer(modifier = Modifier.height(20.dp))

            val datePickerState = rememberDatePickerState(selectableDates = object :
                SelectableDates {
                override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                    return utcTimeMillis <= System.currentTimeMillis()
                }
            })

//            val selectedDate = datePickerState.selectedDateMillis?.let {
//                convertMillisToDate(it)
//            } ?: ""
//            DatePickerDialog(
//                onDismissRequest = { onDismiss() },
//                confirmButton = {
//                    Button(onClick = {
//                        onDateSelected(selectedDate)
//                        onDismiss()
//                    }
//
//                    ) {
//                        Text(text = "OK")
//                    }
//                },
//                dismissButton = {
//                    Button(onClick = {
//                        onDismiss()
//                    }) {
//                        Text(text = "Cancel")
//                    }
//                }
//            ) {
//                DatePicker(
//                    state = datePickerState
//                )
//            }

            val state = rememberDatePickerState()
            val openDialog = remember { mutableStateOf(false) }

            if (openDialog.value) {
                DatePickerDialog(
                    onDismissRequest = {
                        openDialog.value = false
                    },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                openDialog.value = false
                            }
                        ) {
                            Text("OK")
                        }
                    },
                    dismissButton = {
                        TextButton(
                            onClick = {
                                openDialog.value = false
                            }
                        ) {
                            Text("CANCEL")
                        }
                    }
                ) {
                    DatePicker(
                        state = state
                    )
                }
            }

            Row(

            ) {
                Button(
                    onClick = {
                        viewModel.navigatetoExpenses(navController)
                    },
//                    modifier = Modifier.
                ) {
                    Text(text = "Cancel")
                }

                Button(
                    onClick = {
                        val newExpense = ExpenseModel.Expense(
                            name = expenseName,
                            type = expenseType,
                            amount = expenseAmount,
                            date = expenseDate
                        )
                        trip.addExpense(newExpense)
                        viewModel.navigatetoExpenses(navController)
                    },
//                    modifier = Modifier.
                ) {
                    Text(text = "Save")
                }
            }
            // Button to save the expense


            Spacer(modifier = Modifier.height(20.dp))
            Divider(modifier = Modifier.fillMaxWidth())
        }



        Spacer(modifier = Modifier.height(20.dp))
        Divider(modifier = Modifier.fillMaxWidth())
    }
}