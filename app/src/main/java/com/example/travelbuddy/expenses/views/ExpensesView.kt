package com.example.travelbuddy.expenses.views

import android.annotation.SuppressLint
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.travelbuddy.data.model.ExpenseModel
import com.example.travelbuddy.data.model.TripModel
import com.example.travelbuddy.expenses.ExpensesViewModel


@Composable
fun ExpenseList(expense: ExpenseModel.Expense) {
    var expanded by remember { mutableStateOf(false) }
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .height(70.dp)
            .animateContentSize(),
//            .clickable {
//                onTransactionNavigate(transaction.transactionId)
//            },
        shape = RoundedCornerShape(15),
        onClick = { expanded = !expanded }
    ) {
        Row(
            modifier = Modifier
//                .background(color = MaterialTheme.colorScheme.onBackground)
                .fillMaxWidth()
                .fillMaxHeight(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Row(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
            ){

                Spacer(modifier = Modifier.width(11.dp))
                Image(
                    modifier = Modifier
                        .width(36.dp)
                        .height(36.dp),
                    painter = rememberVectorPainter(expense.type.icon),
                    contentDescription = ""
                )
                Spacer(modifier = Modifier.width(40.dp))
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
                Spacer(modifier = Modifier.width(40.dp))
                Text(
                    text = "\$${"%.2f".format(expense.amount)}",
                    textAlign = TextAlign.Left,
                    fontWeight = FontWeight.Bold,
                    style = TextStyle(color = MaterialTheme.colorScheme.primary)
                )
            }
//            IconButton(onClick = {
//                onTransactionNavigate(transaction.transactionId)
//            }) {
//                Icon(
//                    imageVector = Icons.Outlined.ChevronRight,
//                    tint = Color.Black,
//                    contentDescription = "More "
//                )
//
//            }
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

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { viewModel.navigateToAddEditExpense() },
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
//                Text(
//                    text = trip.name,
//                    style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Medium),
//
//                )
                Spacer(modifier = Modifier.height(10.dp))
                Column(
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .border(
                            border = BorderStroke(1.dp, MaterialTheme.colorScheme.surfaceVariant),
                            shape = RoundedCornerShape(16.dp),
                        )
                        .background(
                            MaterialTheme.colorScheme.surfaceVariant
                        )
                        .clickable { onClick() }
                        .padding(4.dp),
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
//                    trip.budgets.forEach { budget ->
//                        val expenseType = budget.key
//                        val budgetAmount = budget.value
//                        val budgetTotalExpense = trip.totalExpenses[expenseType] ?: 0.0
//                        val budgetProgress: Float =
//                            (budgetTotalExpense.toFloat() / budgetAmount).coerceIn(0f, 1f)
////                        val budgetColor: Color = when {
////                            budgetProgress >= 0.75 -> Color.Red
////                            budgetProgress >= 0.50 -> Color.Yellow
////                            else -> Color.Green
////                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                        ) {
//                            Text(
//                                text = expenseType.toString(),
//                                overflow = TextOverflow.Ellipsis,
//                                fontSize = 14.sp,
//                                textAlign = TextAlign.Left
//                            )
//                            Spacer(Modifier.weight(0.9f))
//                            Text(
//                                text = "\$${
//                                    "%.2f".format(
//                                        budgetTotalExpense
//                                    )
//                                } / \$${"%.2f".format(budgetAmount)}",
//                                overflow = TextOverflow.Ellipsis,
//                                fontSize = 14.sp,
//                                textAlign = TextAlign.Right
//                            )
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
                            color = MaterialTheme.colorScheme.secondary
                        )
//                        LaunchedEffect(budgetProgress) {
//                            progress = budgetProgress
//                        }
                    }
                }
            }
//            items(trip.expensesList) { expense ->
//                ExpenseList(expense = expense)
//            }
        }
    }
//}
