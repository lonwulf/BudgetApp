package com.lonwulf.budgetapp.data

import com.lonwulf.budgetapp.domain.mapper.toDomainModel
import com.lonwulf.budgetapp.domain.mapper.toExpensesDomainModel
import com.lonwulf.budgetapp.domain.mapper.toObjectBox
import com.lonwulf.budgetapp.domain.mapper.toObjectBoxModel
import com.lonwulf.budgetapp.domain.model.Expenses
import com.lonwulf.budgetapp.domain.repository.Repository
import com.lonwulf.budgetapp.objectbox.service.ExpensesService
import java.util.Date
import javax.inject.Inject

class RepositoryImpl @Inject constructor(private val expensesService: ExpensesService) :
    Repository {
    override suspend fun insertAll(list: List<Expenses>) {
        expensesService.insertAll(list.toObjectBox())
    }

    override suspend fun insert(model: Expenses) {
        expensesService.insert(model.toObjectBoxModel())
    }

    override suspend fun deleteAllExpenses() {
        expensesService.deleteAll()
    }

    override suspend fun fetchAllExpensesRecords(): List<Expenses> {
        val result = expensesService.findAll()
        return result.toDomainModel()
    }

    override suspend fun fetchExpensesRecordsByMonthDate(date: String): Expenses? {
        val result = expensesService.findExpenseByMonthDate(date)
        return result?.toExpensesDomainModel()
    }
}