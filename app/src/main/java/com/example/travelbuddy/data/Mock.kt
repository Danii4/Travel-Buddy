//package com.example.travelbuddy.data
//
//import android.annotation.SuppressLint
//import androidx.compose.runtime.mutableStateListOf
//import com.example.travelbuddy.data.model.ExpenseModel
//import com.example.travelbuddy.data.model.TripModel
//import java.time.LocalDate
//
//@SuppressLint("NewApi")
//object Mock {
//    val expense1: ExpenseModel.Expense = ExpenseModel.Expense(
//        id = "expense1",
//        name = "Flight to Cancun",
//        type = ExpenseModel.ExpenseType.FLIGHT,
//        amount = 1500.23f,
//        date = LocalDate.now()
//    )
//    val expense2: ExpenseModel.Expense = ExpenseModel.Expense(
//        id = "expense2",
//        name = "Hotel",
//        type = ExpenseModel.ExpenseType.ACCOMMODATION,
//        amount = 123.00f,
//        date = LocalDate.now()
//    )
//    val expense3 = ExpenseModel.Expense(
//        id = "expense3",
//        name = "Dinner",
//        type = ExpenseModel.ExpenseType.FOOD,
//        amount = 35.00f,
//        date = LocalDate.now()
//    )
//    val expense4 = ExpenseModel.Expense(
//        id = "expense3",
//        name = "Dinner",
//        type = ExpenseModel.ExpenseType.FOOD,
//        amount = 35.00f,
//        date = LocalDate.now()
//    )
//    val expense5 = ExpenseModel.Expense(
//        id = "expense3",
//        name = "Dinner",
//        type = ExpenseModel.ExpenseType.FOOD,
//        amount = 35.00f,
//        date = LocalDate.now()
//    )
//    val expense6 = ExpenseModel.Expense(
//        id = "expense3",
//        name = "Dinner",
//        type = ExpenseModel.ExpenseType.FOOD,
//        amount = 35.00f,
//        date = LocalDate.now()
//    )
//    val expense7 = ExpenseModel.Expense(
//        id = "expense3",
//        name = "Dinner",
//        type = ExpenseModel.ExpenseType.FOOD,
//        amount = 35.00f,
//        date = LocalDate.now()
//    )
//    val trip = TripModel.Trip(
//        id = "test id",
//        name = "Test Trip",
//        budgets = mutableMapOf(
//            ExpenseModel.ExpenseType.FLIGHT to 2000.00f,
//            ExpenseModel.ExpenseType.ACCOMMODATION to 500.00f,
//            ExpenseModel.ExpenseType.FOOD to 250.00f
//        ),
//        totalExpenses = mutableMapOf(
//            ExpenseModel.ExpenseType.FLIGHT to 1500.23f,
//            ExpenseModel.ExpenseType.ACCOMMODATION to 123.00f,
//            ExpenseModel.ExpenseType.FOOD to 140.00f
//        ),
//        expensesList =
//            mutableStateListOf(
//                expense1,
//                expense2,
//                expense3,
//                expense4,
//                expense5,
//                expense6,
//                expense7
//            )
//    )
//}
