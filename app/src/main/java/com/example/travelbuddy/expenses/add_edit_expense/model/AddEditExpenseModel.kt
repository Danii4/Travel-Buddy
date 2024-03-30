package com.example.travelbuddy.expenses.add_edit_expense.model

import android.annotation.SuppressLint
import com.example.travelbuddy.data.model.ExpenseModel
import java.time.Instant
import java.time.LocalDate
import java.time.LocalTime
import java.util.Date


@SuppressLint("NewApi")
class AddEditExpenseModel {
    data class AddEditExpenseViewState(
        val name : String = "",
        val amount : Double = 0.00,
        val date : Date = Date.from(Instant.now()),
        val selectedExpense : ExpenseModel.Expense? = null,
        val expenses : List<ExpenseModel.Expense> = listOf()
    )
}