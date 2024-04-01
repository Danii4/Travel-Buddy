package com.example.travelbuddy.expenses.add_edit_expense

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travelbuddy.NavWrapper
import com.example.travelbuddy.Screen
import com.example.travelbuddy.data.ExpenseRepository
import com.example.travelbuddy.data.model.ExpenseModel
import com.example.travelbuddy.data.model.ResponseModel
import com.example.travelbuddy.expenses.add_edit_expense.model.AddEditExpenseModel
import com.github.nkuppan.country.domain.model.Currency
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject


@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class AddEditExpenseViewModel @Inject constructor(
    private val expenseRepository: ExpenseRepository,
    private val navWrapper: NavWrapper,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableStateFlow(AddEditExpenseModel.AddEditExpenseViewState())
    val state: StateFlow<AddEditExpenseModel.AddEditExpenseViewState>
        get() = _state
    private val expenseId: String? = savedStateHandle["expenseId"]
    private val tripId: String? = savedStateHandle["tripId"]

    private val name: MutableStateFlow<String> = MutableStateFlow(_state.value.name)
    private val amount: MutableStateFlow<String> = MutableStateFlow(_state.value.amount)

    @RequiresApi(Build.VERSION_CODES.O)
    private val date: MutableStateFlow<Date> = MutableStateFlow(_state.value.date)
    private val currency: MutableStateFlow<Currency> = MutableStateFlow(_state.value.currency)
    private val type: MutableStateFlow<ExpenseModel.ExpenseType> =
        MutableStateFlow(_state.value.type)

    fun setExpenseName(newName: String) {
        name.value = newName
    }

    fun getExpenseId() : String? {
        return expenseId
    }

    // Cited from: https://github.com/Kotlin/kotlinx.coroutines/issues/3598
    inline fun <T1, T2, T3, T4, T5, R> combine(
        flow: Flow<T1>,
        flow2: Flow<T2>,
        flow3: Flow<T3>,
        flow4: Flow<T4>,
        flow5: Flow<T5>,
        crossinline transform: suspend (T1, T2, T3, T4, T5) -> R
    ): Flow<R> {
        return kotlinx.coroutines.flow.combine(flow, flow2, flow3, flow4, flow5) { args: Array<*> ->
            @Suppress("UNCHECKED_CAST")
            transform(
                args[0] as T1,
                args[1] as T2,
                args[2] as T3,
                args[3] as T4,
                args[4] as T5,
            )
        }
    }

    init {
        viewModelScope.launch {
            combine(name, amount, date, type, currency) { name: String,
                                                              amount: String,
                                                              date: Date,
                                                              type: ExpenseModel.ExpenseType,
                                                              currency: Currency ->
                AddEditExpenseModel.AddEditExpenseViewState(
                    name = name,
                    amount = amount,
                    date = date,
                    type = type,
                    currency = currency
                )
            }.collect {
                _state.value = it
            }
        }
    }

    init {
        getData()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getData() {
        viewModelScope.launch {
            if (expenseId?.isNotBlank() == true) {
                expenseRepository.getExpense(expenseId).collect { expense ->
                    expense.data?.let {
                        name.value = it.name
                        amount.value = it.amount.toString()
                        date.value = it.date
                        type.value = it.type
                        currency.value = Currency(code = it.currency.code, name = it.currency.name, symbol = it.currency.symbol)
                    }
                }
            }
        }
    }

    fun submitExpense(expense: ExpenseModel.Expense) {
        viewModelScope.launch {
            // Add type checking for expense
            when (expenseRepository.addUpdateExpense(expense, tripId)) {
                is ResponseModel.Response.Success -> {}
                is ResponseModel.Response.Failure -> {}
            }
            navigateBack()
        }
    }

    fun deleteExpense() {
        viewModelScope.launch {
            when (expenseRepository.deleteExpense(expenseId, tripId)) {
                is ResponseModel.Response.Success -> {
                    Log.d("Delete Expense Success", "Successfully delete expense id: $expenseId")
                }
                is ResponseModel.Response.Failure -> {
                    Log.d("Delete Expense Failure", "Error deleting expense id: $expenseId")
                }
            }
        }
    }

    fun navigateBack() {
        navWrapper.getNavController().navigateUp()
    }

    fun setExpenseType(newType: ExpenseModel.ExpenseType) {
        type.value = newType
    }

    fun setExpenseAmount(newAmount: String) {
        amount.value = newAmount
    }

    fun setExpenseCurrency(newCurrency: Currency) {
        currency.value = newCurrency

    }
}