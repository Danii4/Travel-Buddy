package com.example.travelbuddy.expenses.views

import android.annotation.SuppressLint
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import java.time.LocalDate


@Composable
fun ExpenseList(expense: ExpenseModel.Expense) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        // Modifier.clickable(onClick =
    ) {
        Row(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = expense.name)
            Spacer(Modifier.weight(1f))
            Text(text = "\$%.2f".format(expense.amount))
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
    val expense1 = ExpenseModel.Expense(
        id = "expense1",
        name = "Flight to Cancun",
        type = ExpenseModel.ExpenseType.FLIGHT,
        amount = 1500.23,
        date = LocalDate.now()
    )
    val expense2 = ExpenseModel.Expense(
        id = "expense2",
        name = "Hotel",
        type = ExpenseModel.ExpenseType.ACCOMMODATION,
        amount = 123.00,
        date = LocalDate.now()
    )
    val expense3 = ExpenseModel.Expense(
        id = "expense3",
        name = "Dinner",
        type = ExpenseModel.ExpenseType.FOOD,
        amount = 35.00,
        date = LocalDate.now()
    )
    val expense4 = ExpenseModel.Expense(
        id = "expense3",
        name = "Dinner",
        type = ExpenseModel.ExpenseType.FOOD,
        amount = 35.00,
        date = LocalDate.now()
    )
    val expense5 = ExpenseModel.Expense(
        id = "expense3",
        name = "Dinner",
        type = ExpenseModel.ExpenseType.FOOD,
        amount = 35.00,
        date = LocalDate.now()
    )
    val expense6 = ExpenseModel.Expense(
        id = "expense3",
        name = "Dinner",
        type = ExpenseModel.ExpenseType.FOOD,
        amount = 35.00,
        date = LocalDate.now()
    )
    val expense7 = ExpenseModel.Expense(
        id = "expense3",
        name = "Dinner",
        type = ExpenseModel.ExpenseType.FOOD,
        amount = 35.00,
        date = LocalDate.now()
    )
    val tripModel = object {
        val id = "test id"
        val name = "Test Trip"
        val budgets = mutableMapOf(
            ExpenseModel.ExpenseType.FLIGHT to 2000.00f,
            ExpenseModel.ExpenseType.ACCOMMODATION to 500.00f,
            ExpenseModel.ExpenseType.FOOD to 250.00f
        )
        val totalExpenses = mutableMapOf(
            ExpenseModel.ExpenseType.FLIGHT to 1500.23f,
            ExpenseModel.ExpenseType.ACCOMMODATION to 123.00f,
            ExpenseModel.ExpenseType.FOOD to 140.00f
        )
        val expensesList = remember { mutableStateListOf(expense1, expense2, expense3, expense4, expense5, expense6, expense7) }
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
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
        ) {
            item {
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
//                        .background(
//                            Color.White,
//                        )
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
                        var progress by remember { mutableFloatStateOf(0f) }
                        val progressAnimDuration = 1500
                        val progressAnimation by animateFloatAsState(
                            targetValue = progress,
                            animationSpec = tween(
                                durationMillis = progressAnimDuration,
                                easing = FastOutSlowInEasing
                            ),
                            label = ""
                        )
                        LinearProgressIndicator(
                            progress = progressAnimation,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(10.dp)
                                .padding(end = 16.dp),
                            color = budgetColor
                        )
                        LaunchedEffect(budgetProgress) {
                            progress = budgetProgress
                        }
                    }
                }
            }
            items(tripModel.expensesList) { expense ->
                ExpenseList(expense = expense)
            }
        }
    }
}
