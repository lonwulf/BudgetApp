package com.lonwulf.budgetapp.domain.usecase

import com.lonwulf.budgetapp.domain.model.Expenses
import com.lonwulf.budgetapp.domain.repository.Repository
import com.lonwulf.budgetapp.util.GenericUseCaseResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.Date
import javax.inject.Inject

class ExpensesUseCase @Inject constructor(private val repository: Repository) {
    operator fun invoke(): Flow<GenericUseCaseResult<List<Expenses>>> =
        flow {
            val list = repository.fetchAllExpensesRecords()
            emit(GenericUseCaseResult(result = list, isSuccessful = true))
        }

    fun insertAllItems(): Flow<GenericUseCaseResult<Boolean>> = flow {
        try {
            val list = listOf(Expenses(), Expenses(), Expenses(), Expenses(), Expenses())
            repository.insertAll(list)
            emit(GenericUseCaseResult(result = true, isSuccessful = true))
        } catch (ex: Exception) {
            emit(GenericUseCaseResult(result = false, isSuccessful = false))
        }
    }

    suspend fun deleteCache() = repository.deleteAllExpenses()

    fun fetchExpenseByMonthDate(date: Date): Flow<GenericUseCaseResult<List<Expenses>>> =
        flow {
            val list = repository.fetchExpensesRecordsByMonthDate(date)
            emit(GenericUseCaseResult(result = list, isSuccessful = true))
        }
}