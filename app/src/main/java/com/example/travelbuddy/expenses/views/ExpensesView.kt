package com.example.travelbuddy.expenses.views

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
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
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.travelbuddy.R
import com.example.travelbuddy.data.model.ExpenseModel
import com.example.travelbuddy.expenses.ExpensesViewModel
import com.example.travelbuddy.expenses.model.ExpensesModel


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ExpenseList(
    expense: ExpenseModel.Expense,
    viewModel: ExpensesViewModel,
    state: ExpensesModel.ExpensesViewState
) {
    var expanded by remember { mutableStateOf(false) }
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .height(70.dp)
            .animateContentSize(),
        shape = RoundedCornerShape(15),
        onClick = { viewModel.navigateToAddEditExpense(expense.id) }
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
            ) {

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
        }

    }
}

//@SuppressLint("NewApi")
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun ExpensesView(
//    modifier: Modifier = Modifier,
//    onClick: () -> Unit = {},
//) {
//    val viewModel = hiltViewModel<ExpensesViewModel>()
//    val state by viewModel.state.collectAsState()
//
//    Scaffold { paddingValues ->
//        Text(
//            text = state.trip.name,
//            style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Medium),
//
//            )
//        Spacer(modifier = Modifier.height(20.dp))
//        Column(
//            modifier = Modifier
//                .padding(paddingValues)
//        ) {
//            LazyColumn(
//                modifier = Modifier
//                    .clip(RoundedCornerShape(16.dp))
//                    .border(
//                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.surfaceVariant),
//                        shape = RoundedCornerShape(16.dp),
//                    )
//                    .background(
//                        MaterialTheme.colorScheme.surfaceVariant
//                    )
////                        .clickable { onClick() }
//                    .padding(4.dp),
//                verticalArrangement = Arrangement.spacedBy(20.dp)
//            ) {
//                items(state.budgets.toList()) { (expenseType, amount) ->
//
//                    Row(
//                        modifier = Modifier.fillMaxWidth(),
//                    ) {
//                        Text(
//                            text = expenseType.stringValue,
//                            overflow = TextOverflow.Ellipsis,
//                            fontSize = 14.sp,
//                            textAlign = TextAlign.Left
//                        )
//                        Spacer(Modifier.weight(0.9f))
//                        Text(
//                            text = "\$${
//                                "%.2f".format(
//                                    amount.toString()
//                                )
//                            } / \$${"%.2f".format(amount.toString())}",
//                            overflow = TextOverflow.Ellipsis,
//                            fontSize = 14.sp,
//                            textAlign = TextAlign.Right
//                        )
//                    }
//                    var progress by remember { mutableFloatStateOf(0f) }
//                    val progressAnimDuration = 1500
//                    val progressAnimation by animateFloatAsState(
//                        targetValue = progress,
//                        animationSpec = tween(
//                            durationMillis = progressAnimDuration,
//                            easing = FastOutSlowInEasing
//                        ),
//                        label = ""
//                    )
//                    LinearProgressIndicator(
//                        progress = progressAnimation,
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .height(10.dp)
//                            .padding(end = 16.dp),
//                        color = MaterialTheme.colorScheme.secondary
//                    )
////                        LaunchedEffect(viewModel.getBudgetProgress(expenseType)) {
////                            progress = budgetProgress
////                        }
//                }
//            }
//            LazyColumn(
//                modifier = Modifier
//                    .padding(paddingValues)
//            ) {
//                viewModel.getData()
//                items(state.expensesList) { expense ->
//                    ExpenseList(expense = expense, viewModel = viewModel, state = state)
//                }
//            }
//        }
//        Box(
//            modifier = Modifier.fillMaxSize(),
//            contentAlignment = Alignment.BottomCenter
//        ) {
//            Button(
//                onClick = {
//                    viewModel.navigateToAddEditExpense()
//                },
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(horizontal = 10.dp, vertical = 10.dp),
//                colors = ButtonDefaults.buttonColors(
//                    containerColor = Color(92, 184, 92),
//                    contentColor = MaterialTheme.colorScheme.onTertiary
//                )
//            ) {
//                Text(text = "+")
//            }
//
//        }
//    }
//}

@SuppressLint("NewApi")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpensesView(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    val viewModel = hiltViewModel<ExpensesViewModel>()
    val state by viewModel.state.collectAsState()

    Scaffold { paddingValues ->
        Column(
            modifier = modifier.padding(paddingValues)
        ) {
            Text(
                text = state.trip.name,
                style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Medium),
                modifier = Modifier.padding(vertical = 16.dp)
            )
            LazyColumn(
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .border(
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.surfaceVariant),
                        shape = RoundedCornerShape(16.dp)
                    )
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .padding(8.dp)
            ) {
                items(state.budgets.toList()) { (expenseType, amount) ->
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
                            text = "\$${
                                "%.2f".format(
                                    viewModel.getTotalExpenses(expenseType)
                                )
                            } / \$${"%.2f".format(amount.toString().toDouble())}",
                            overflow = TextOverflow.Ellipsis,
                            fontSize = 16.sp,
                            modifier = Modifier.padding(start = 16.dp)
                        )
                    }
                    LinearProgressIndicator(
                        progress = 0.5f, // Dummy progress for demonstration
                        modifier = Modifier.fillMaxWidth(),
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn(
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .border(
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.surfaceVariant),
                        shape = RoundedCornerShape(16.dp)
                    )
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .padding(8.dp)
            ) {
                viewModel.getData()
                items(state.expensesList) { expense ->
                    ExpenseList(expense = expense, viewModel = viewModel, state = state)
                }
            }
            Box(
                modifier = Modifier.fillMaxSize(),
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
