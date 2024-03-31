package com.example.travelbuddy.expenses.add_edit_expense.model

import android.annotation.SuppressLint
import com.example.travelbuddy.data.model.ExpenseModel
import com.github.nkuppan.country.domain.model.Currency
import java.math.BigDecimal
import java.time.Instant
import java.time.LocalDate
import java.time.LocalTime
import java.util.Date


@SuppressLint("NewApi")
class AddEditExpenseModel {
    data class AddEditExpenseViewState(
        val name : String = "",
        val amount : String = "",
        val date : Date = Date.from(Instant.now()),
        val type : ExpenseModel.ExpenseType = ExpenseModel.ExpenseType.FLIGHT,
        val currency : Currency = Currency(code = "CAD", symbol = "$")
    )
}