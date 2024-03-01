package com.example.travelbuddy.expenses.views

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.travelbuddy.data.model.ExpenseModel
import com.example.travelbuddy.expenses.ExpensesViewModel


@Composable
fun ExpenseList(expense: ExpenseModel.Expense) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = expense.name)
            Text(text = "$${expense.amount}")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpensesView(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    val expense1 = ExpenseModel.Expense(
        expenseId = "expense1",
        name = "Flight to Cancun",
        expenseType = ExpenseModel.ExpenseType.FLIGHT,
        amount = 1500.23
    )
    val expense2 = ExpenseModel.Expense(
        expenseId = "expense2",
        name = "Hotel",
        expenseType = ExpenseModel.ExpenseType.ACCOMMODATION,
        amount = 123.00
    )
    val expense3 = ExpenseModel.Expense(
        expenseId = "expense3",
        name = "Dinner",
        expenseType = ExpenseModel.ExpenseType.FOOD,
        amount = 35.00
    )
    val tripModel = object {
        val id = "test id"
        val name = "Test Trip"
        val budgets = mutableMapOf<ExpenseModel.ExpenseType, Float>(
            ExpenseModel.ExpenseType.FLIGHT to 2000.00f,
            ExpenseModel.ExpenseType.ACCOMMODATION to 500.00f,
            ExpenseModel.ExpenseType.FOOD to 200.00f
        )
        val totalExpenses = mutableMapOf(
            ExpenseModel.ExpenseType.FLIGHT to 1500.23f,
            ExpenseModel.ExpenseType.ACCOMMODATION to 123.00f,
            ExpenseModel.ExpenseType.FOOD to 35.00f
        )
        val expensesList = remember { mutableStateListOf(expense1, expense2, expense3) }
    }
    val viewModel = ExpensesViewModel()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onClick() },
                shape = CircleShape
            ) {
                Icon(Icons.Filled.Add, "Floating action button.")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
        ) {
            Text(
                text = tripModel.name,
                style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Medium)
            )
            Spacer(modifier = Modifier.height(10.dp))
            Column(
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .border(
                        border = BorderStroke(1.dp, Color.Magenta),
                        shape = RoundedCornerShape(16.dp),
                    )
                    .background(
                        Color.White,
                    )
                    .clickable { onClick() }
                    .padding(20.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                tripModel.budgets.forEach { budget ->
                    val expenseType = budget.key
                    val budgetAmount = budget.value
                    val budgetTotalExpense = tripModel.totalExpenses[expenseType] ?: 0.0
                    val budgetProgress: Float =
                        (budgetTotalExpense.toFloat() / budgetAmount).coerceIn(0f, 1f)
                    val budgetColor: Color = when {
                        budgetProgress >= 0.75 -> Color.Red
                        budgetProgress >= 0.50 -> Color.Yellow
                        else -> Color.Green
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Text(
                            text = expenseType.toString(),
                            overflow = TextOverflow.Ellipsis,
                            fontSize = 14.sp,
                            textAlign = TextAlign.Left
                        )
                        Spacer(Modifier.weight(1f))
                        Text(
                            text = "\$${
                                "%.2f".format(
                                    budgetTotalExpense
                                )
                            } / \$${"%.2f".format(budgetAmount)}",
                            overflow = TextOverflow.Ellipsis,
                            fontSize = 14.sp,
                            textAlign = TextAlign.Right
                        )
                    }
                    LinearProgressIndicator(
                        progress = budgetProgress,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(10.dp)
                            .padding(end = 16.dp),
                        color = budgetColor
                    )
                }
            }
            LazyColumn {
                items(tripModel.expensesList) { expense ->
                    ExpenseList(expense = expense)
                }
            }
        }
    }
}
