package com.lonwulf.budgetapp.domain.mapper

import com.lonwulf.budgetapp.domain.model.Expenses
import com.lonwulf.budgetapp.objectbox.entity.ExpenseOB

fun List<ExpenseOB>.toDomainModel(): List<Expenses> = mutableListOf<Expenses>().apply {
    this@toDomainModel.map { entity ->
        add(
            Expenses(
                expense = entity.expense,
                spendingCategory = entity.spendingCategory,
                recentTransactions = entity.recentTransactions,
                date = entity.date
            )
        )
    }
}

fun List<Expenses>.toObjectBox(): List<ExpenseOB> = mutableListOf<ExpenseOB>().apply {
    this@toObjectBox.map { model ->
        add(
            ExpenseOB(
                expense = model.expense,
                date = model.date,
                recentTransactions = model.recentTransactions,
                spendingCategory = model.spendingCategory
            )
        )
    }
}

fun Expenses.toObjectBoxModel(): ExpenseOB = ExpenseOB(
    expense = this.expense,
    recentTransactions = this.recentTransactions,
    spendingCategory = this.spendingCategory,
    date = this.date
)