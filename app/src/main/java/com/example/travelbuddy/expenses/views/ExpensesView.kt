package com.example.travelbuddy.expenses.views

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.travelbuddy.data.model.ExpenseModel
import com.example.travelbuddy.expenses.ExpensesViewModel
import com.example.travelbuddy.expenses.model.ExpensesModel
import com.example.travelbuddy.languageTranslation.CustomColors
import java.math.BigDecimal

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BudgetItem(
    expenseType: ExpenseModel.ExpenseType,
    amount: BigDecimal,
    viewModel: ExpensesViewModel,
    state: ExpensesModel.ExpensesViewState
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = expenseType.displayValue,
            overflow = TextOverflow.Ellipsis,
            fontSize = 16.sp,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = "${state.trip.defaultCurrency.symbol}${
                "%.2f".format(
                    viewModel.getTotalExpenses(expenseType)
                )
            } / ${state.trip.defaultCurrency.symbol}${
                "%.2f".format(
                    amount.toString().toDouble()
                )
            } ${state.trip.defaultCurrency.code}",
            overflow = TextOverflow.Ellipsis,
            fontSize = 16.sp,
            modifier = Modifier.padding(start = 16.dp)
        )
    }
    Spacer(modifier = Modifier.height(2.dp))
    val progress = viewModel.getProgress(expenseType, amount)
    CustomProgressBar(
        Modifier
            .clip(shape = RoundedCornerShape(16.dp))
            .height(14.dp),
        330.dp,
        Color.White,
        Brush.horizontalGradient(listOf(CustomColors.LightIndigo, CustomColors.Indigo)),
        progress
    )
}

@Composable
fun CustomProgressBar(
    modifier: Modifier, width: Dp, backgroundColor: Color, foregroundColor: Brush, progress: Float,
) {
    Box(
        modifier = modifier
            .background(backgroundColor)
            .width(width)
    ) {
        Box(
            modifier = modifier
                .background(brush = foregroundColor)
                .width((width * progress))
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ExpenseList(
    expense: ExpenseModel.Expense,
    viewModel: ExpensesViewModel,
) {
    var expanded by remember { mutableStateOf(false) }
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize(),
        shape = RoundedCornerShape(15),
        onClick = { viewModel.navigateToAddEditExpense(expense.id) }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Row(
                modifier = Modifier
                    .weight(1f)
                    .padding(15.dp),
                horizontalArrangement = Arrangement.spacedBy(30.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Image(
                    modifier = Modifier
                        .width(36.dp)
                        .height(36.dp),
                    painter = rememberVectorPainter(expense.type.icon),
                    contentDescription = ""
                )
                Column(
                    modifier = Modifier
                        .width(140.dp)
                        .fillMaxHeight(),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = expense.name,
                        fontWeight = FontWeight.Bold,
                        style = TextStyle(color = MaterialTheme.colorScheme.primary)

                    )
                    Text(
                        text = expense.date.toString(),
                        style = TextStyle(color = MaterialTheme.colorScheme.primary)
                    )

                }
                Text(
                    text = "${expense.currency.symbol}${"%.2f".format(expense.amount)}\n${expense.currency.code}",
                    textAlign = TextAlign.Left,
                    fontWeight = FontWeight.Bold,
                    style = TextStyle(color = MaterialTheme.colorScheme.primary)
                )
            }
        }

    }
}

@SuppressLint("NewApi")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpensesView(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    val viewModel = hiltViewModel<ExpensesViewModel>()
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getData()
    }

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
                        text = state.trip.name,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                actions = {
                    // Add a switch to toggle default currency for expenses
                    var toggleState by remember { mutableStateOf(false) }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(10.dp)
                    ) {
                    }
                    Text(
                        text = state.trip.defaultCurrency.code!!,
                        color = if (toggleState) CustomColors.LightGreen else CustomColors.DarkGreen,
                        fontWeight = FontWeight.Bold
                    )
                    Switch(
                        checked = toggleState,
                        onCheckedChange = { isChecked ->
                            toggleState = isChecked
                            viewModel.setCurrencyToggle(toggleState)
                        },
                        modifier = Modifier.padding(10.dp)
                    )
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .padding(paddingValues)
                .padding(15.dp)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            LazyColumn(
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .border(
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.surfaceVariant),
                        shape = RoundedCornerShape(16.dp)
                    )
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .padding(15.dp)
            ) {
                items(state.budgets.toList()) { (expenseType, amount) ->
                    BudgetItem(expenseType, amount, viewModel, state)
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }

            Column(
                modifier = Modifier
                    .weight(1f, fill = true)
                    .fillMaxWidth()
            ) {
                if (state.expensesList.isNotEmpty()) {
                    LazyColumn(
                        modifier = Modifier
                            .clip(RoundedCornerShape(16.dp))
                            .border(
                                border = BorderStroke(
                                    1.dp,
                                    MaterialTheme.colorScheme.surfaceVariant
                                ),
                                shape = RoundedCornerShape(16.dp)
                            )
                            .background(MaterialTheme.colorScheme.surfaceVariant)
                            .padding(15.dp)
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(15.dp)
                    ) {
                        items(state.expensesList) { expense ->
                            ExpenseList(expense = expense, viewModel = viewModel)
                        }
                    }
                }
            }

            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.BottomCenter
            ) {
                Button(
                    onClick = {
                        viewModel.navigateToAddEditExpense()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp, vertical = 10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(92, 184, 92),
                        contentColor = MaterialTheme.colorScheme.onTertiary
                    )
                ) {
                    Text(text = "+")
                }

            }
        }
    }
}
