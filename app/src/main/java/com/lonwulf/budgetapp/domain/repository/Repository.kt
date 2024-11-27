package com.lonwulf.budgetapp.domain.repository

import com.lonwulf.budgetapp.domain.model.Expenses
import java.util.Date

interface Repository {
    suspend fun insertAll(list:List<Expenses>)
    suspend fun insert(model:Expenses)
    suspend fun deleteAllExpenses()
    suspend fun fetchAllExpensesRecords():List<Expenses>
    suspend fun fetchExpensesRecordsByMonthDate(date: Date):List<Expenses>
}