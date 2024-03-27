package com.example.travelbuddy.data.model

class TripModel {
    data class Trip(
        val id: String = "",
        val name: String = "",
//        var budgets: MutableMap<ExpenseModel.ExpenseType, Float>,
//        var totalExpenses: MutableMap<ExpenseModel.ExpenseType, Float>,
//        var expensesList: List<String>,
//        var destinationList: List<String>
    ) {
//        fun addExpense(newExpense: ExpenseModel.Expense) {
//            this.expensesList.add(newExpense)
//            if (totalExpenses.containsKey(newExpense.type)) {
//                totalExpenses[newExpense.type] = totalExpenses[newExpense.type]!! + newExpense.amount
//            }
//        }
//
//        fun deleteExpense(expense: ExpenseModel.Expense) {
//            this.expensesList.remove(expense)
//            if (totalExpenses.containsKey(expense.type)) {
//                totalExpenses[expense.type] = totalExpenses[expense.type]!! - expense.amount
//            }
//        }
    }
}