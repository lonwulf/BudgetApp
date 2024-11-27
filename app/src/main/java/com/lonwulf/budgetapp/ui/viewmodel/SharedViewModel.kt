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
class SharedViewModel @Inject constructor(internal val expensesUseCase: ExpensesUseCase):ViewModel() {

    private var _expensesStateFlow = MutableStateFlow<GenericResultState<List<Expenses>>>(GenericResultState.Loading)
    val expensesStateFlow
        get() = _expensesStateFlow.asStateFlow()

    fun fetchAllExpenses() = viewModelScope.launch(Dispatchers.IO) {
        expensesUseCase().onStart { setExpensesResult(GenericResultState.Loading) }
            .flowOn(Dispatchers.IO)
            .collect{
                if (it.isSuccessful){
                    setExpensesResult(GenericResultState.Success(it.result))
                }else{
                    setExpensesResult(GenericResultState.Empty)
                }
            }

    }

    private fun setExpensesResult(data:GenericResultState<List<Expenses>>){
        _expensesStateFlow.value = data
    }
}