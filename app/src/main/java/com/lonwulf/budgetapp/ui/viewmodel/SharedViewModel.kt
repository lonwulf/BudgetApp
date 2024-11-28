package com.lonwulf.budgetapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lonwulf.budgetapp.domain.model.Expenses
import com.lonwulf.budgetapp.domain.usecase.ExpensesUseCase
import com.lonwulf.budgetapp.util.GenericResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(internal val expensesUseCase: ExpensesUseCase) :
    ViewModel() {

    private var _expensesStateFlow =
        MutableStateFlow<GenericResultState<List<Expenses>>>(GenericResultState.Loading)
    private var _monthlyExpensesStateFlow =
        MutableStateFlow<GenericResultState<Expenses>>(GenericResultState.Loading)
    private var _insertExpensesStateFlow =
        MutableStateFlow<GenericResultState<Boolean>>(GenericResultState.Loading)
    val expensesStateFlow
        get() = _expensesStateFlow.asStateFlow()
    val monthlyExpensesReport
        get() = _monthlyExpensesStateFlow.asStateFlow()
    val insertExpensesStateFlow
        get() = _insertExpensesStateFlow.asStateFlow()
    lateinit var expenseModel: Expenses

    fun fetchAllExpenses() = viewModelScope.launch(Dispatchers.IO) {
        expensesUseCase().onStart { setExpensesResult(GenericResultState.Loading) }
            .flowOn(Dispatchers.IO)
            .collect {
                if (it.isSuccessful) {
                    setMonthlyExpensesResult(GenericResultState.Success(it.result))
                } else {
                    setMonthlyExpensesResult(GenericResultState.Empty)
                }
            }

    }


    fun fetchMonthlyExpenses(date: String) = viewModelScope.launch(Dispatchers.IO) {
        expensesUseCase.fetchExpenseByMonthDate(date)
            .onStart { setExpensesResult(GenericResultState.Loading) }
            .flowOn(Dispatchers.IO)
            .collect {
                if (it.isSuccessful) {
                    setMonthlyExpensesResult(GenericResultState.Success(it.result))
                } else {
                    setMonthlyExpensesResult(GenericResultState.Empty)
                }
            }

    }

    fun insertExpense(model: Expenses) = viewModelScope.launch(Dispatchers.IO) {
        expensesUseCase.insertExpense(model)
            .onStart { }
            .flowOn(Dispatchers.IO)
            .collect {
                if (it.isSuccessful) {
                    setInsertExpensesResult(GenericResultState.Success(it.result))
                } else {
                    setInsertExpensesResult(
                        GenericResultState.Error(
                            msg = it.msg ?: "Something went wrong"
                        )
                    )
                }
            }
    }

    fun cacheExpenseModel(expenses: Expenses) {
        expenseModel = expenses
    }

    private fun setExpensesResult(data: GenericResultState<List<Expenses>>) {
        _expensesStateFlow.value = data
    }

    private fun setMonthlyExpensesResult(data: GenericResultState<Expenses>) {
        _monthlyExpensesStateFlow.value = data
    }

    private fun setInsertExpensesResult(data: GenericResultState<Boolean>) {
        _insertExpensesStateFlow.value = data
    }
}