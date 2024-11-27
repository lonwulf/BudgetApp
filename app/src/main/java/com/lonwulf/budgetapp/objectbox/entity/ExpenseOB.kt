package com.lonwulf.budgetapp.objectbox.entity

import com.lonwulf.budgetapp.domain.model.Expenses
import com.lonwulf.budgetapp.domain.model.Transactions
import com.lonwulf.budgetapp.objectbox.BoxStoreObject
import com.lonwulf.budgetapp.objectbox.util.CategoryConverter
import com.lonwulf.budgetapp.objectbox.util.TransactionsConverter
import io.objectbox.annotation.ConflictStrategy
import io.objectbox.annotation.Convert
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.annotation.Unique

@Entity
data class ExpenseOB(
    @Id(assignable = true)
    override var objectBoxId: Long = 0,

    @Unique(onConflict = ConflictStrategy.REPLACE)
    var id: String = String(),

    var expense: Double = 0.0,

    @Convert(converter = CategoryConverter::class, dbType = String::class)
    var spendingCategory: List<Expenses.ExpenseCategory> = emptyList(),

    @Convert(converter = TransactionsConverter::class, dbType = String::class)
    var recentTransactions: List<Transactions>? = emptyList(),

    var date: String = String()
) : BoxStoreObject