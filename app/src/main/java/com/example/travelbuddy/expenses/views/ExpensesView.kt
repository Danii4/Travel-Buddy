package com.example.travelbuddy.expenses.views

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.travelbuddy.data.model.ExpenseModel
import com.example.travelbuddy.expenses.ExpensesViewModel


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
        price = 1500.23
    )
    val expense2 = ExpenseModel.Expense(
        expenseId = "expense2",
        name = "Hotel",
        expenseType = ExpenseModel.ExpenseType.ACCOMMODATION,
        price = 123.00
    )
    val expense3 = ExpenseModel.Expense(
        expenseId = "expense3",
        name = "Dinner",
        expenseType = ExpenseModel.ExpenseType.FOOD,
        price = 35.00
    )
    val tripModel = object {
        val id = "test id"
        val name = "Test Trip"
        val budgets = mutableMapOf(
            ExpenseModel.ExpenseType.FLIGHT to 2000.00,
            ExpenseModel.ExpenseType.ACCOMMODATION to 500.00,
            ExpenseModel.ExpenseType.FOOD to 200.00
        )
        val totalExpenses = mutableMapOf(
            ExpenseModel.ExpenseType.FLIGHT to 1500.23,
            ExpenseModel.ExpenseType.ACCOMMODATION to 123.00,
            ExpenseModel.ExpenseType.FOOD to 35.00
        )
        val expensesList = mutableListOf(expense1, expense2, expense3)
    }
    val viewModel = ExpensesViewModel()

    Column(
        modifier = modifier
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
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = budget.key.toString(),
                        modifier = Modifier
                            .width(125.dp),
                        overflow = TextOverflow.Ellipsis,
                        fontSize = 14.sp
                    )
                    val budgetProgress: Float =
                        (tripModel.totalExpenses[budget.key]!!.toFloat() / budget.value).toFloat()
                    val budgetColor: Color = when {
                        budgetProgress >= 0.75 -> Color.Red
                        budgetProgress >= 0.50 -> Color.Yellow
                        else -> Color.Green
                    }
                    LinearProgressIndicator(
                        progress = budgetProgress,
                        modifier = Modifier.fillMaxWidth(),
                        color = budgetColor
                    )
                }
            }
        }
    }
}
