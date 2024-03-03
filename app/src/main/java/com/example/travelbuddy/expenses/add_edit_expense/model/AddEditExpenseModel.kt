package com.example.travelbuddy.expenses.add_edit_expense.model

import android.annotation.SuppressLint
import com.example.travelbuddy.data.model.ExpenseModel
import java.time.LocalDate
import java.time.LocalTime


@SuppressLint("NewApi")
class AddEditExpenseModel {
    data class AddEditExpenseViewState(
        val expenseName : String = "",
        val expenseAmount : Double = 0.00,
        val expenseDate : LocalDate = LocalDate.now(),
        val selectedExpense : ExpenseModel.Expense? = null,
        val expenses : List<ExpenseModel.Expense> = listOf()
    )
}