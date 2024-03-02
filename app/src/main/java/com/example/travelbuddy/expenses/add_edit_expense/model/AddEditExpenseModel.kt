package com.example.travelbuddy.expenses.add_edit_expense.model

import java.time.LocalDate
import java.time.LocalTime

data class AddExpenseViewState(
    val expenseName : String? = null,
    val expenseAmount : Int? = null,
    val expenseTime: LocalTime = LocalTime.now(),
    val expenseDate: LocalDate = LocalDate.now(),
//        val submitButtonUiState : UiComponentModel.ButtonUiState,
//        val isAddProduce : Boolean = true,
)
class AddExpenseModel {

}