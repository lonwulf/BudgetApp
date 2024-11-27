package com.lonwulf.budgetapp.objectbox.service

import com.lonwulf.budgetapp.objectbox.entity.ExpenseOB
import com.lonwulf.budgetapp.objectbox.entity.ExpenseOB_
import com.lonwulf.budgetapp.objectbox.store
import io.objectbox.kotlin.equal

import javax.inject.Inject

class ExpensesService @Inject constructor(private val boxService: BoxStoreService) {
    private val expensesBox = boxService.getBoxStoreModel(ExpenseOB::class.java)

    fun insert(entity: ExpenseOB) = boxService.store(entity)

    fun insertAll(entityList: List<ExpenseOB>) = boxService.store(entityList)

    fun findAll(): List<ExpenseOB> = expensesBox.all

    fun deleteAll() = expensesBox.removeAll()

    fun findExpensesByMonthDate(date: String): List<ExpenseOB>? =
        expensesBox.query(ExpenseOB_.date equal date).build().use { it.find() }

    fun findExpenseByMonthDate(date: String): ExpenseOB? =
        expensesBox.query(ExpenseOB_.date equal date).build().use { it.findUnique() }
}